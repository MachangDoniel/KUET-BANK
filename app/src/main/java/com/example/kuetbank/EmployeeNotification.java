package com.example.kuetbank;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmployeeNotification extends AppCompatActivity implements SelectListener3 {

    DatabaseReference ref,dataBaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kuet-bank-default-rtdb.firebaseio.com");
    RecyclerView recyclerView;
    DatabaseReference database;
    LoanAdapter myAdapter;
    //ArrayList<Customer>list;
    String userId="",accountId="";
    Double Loan=0d;
    String ACC;
    long max=2147483647;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_notification);

        recyclerView=findViewById(R.id.list);
        database= FirebaseDatabase.getInstance().getReference("LOAN");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        EditText editText = findViewById(R.id.text);

        showData("");

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = String.valueOf(editable).toLowerCase();
                showData(text);
            }
        });

    }

    private void showData(String key){
        ArrayList<Loan>list = new ArrayList<>();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Loan user=dataSnapshot.getValue(Loan.class);
                    if((key.isEmpty() || (user != null && user.Name.toLowerCase().contains(key))) && user.Approve.equals("false")) {
                        list.add(user);
                    }
                }
                myAdapter = new LoanAdapter(EmployeeNotification.this,list,EmployeeNotification.this);
                recyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case 121:
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("LOAN"),ref2=FirebaseDatabase.getInstance().getReference("Customer");

                ref.child(accountId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String verified = "true";
                        ref.child(accountId).child("approve").setValue(verified);


                        String loan=snapshot.child("loan").getValue().toString();
                        Loan=Double.parseDouble(loan);
                        myAdapter.removeItem(item.getGroupId());
                        Toast.makeText(EmployeeNotification.this, "LOAN Request Accepted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                ref2.child(accountId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Loan profile = snapshot.getValue(Loan.class);

                        if (profile != null) {
                            Double balance=Double.valueOf(snapshot.child("balance").getValue().toString());
                            balance+=Loan;
                            String Balance=String.valueOf(balance);
                            ref2.child(accountId).child("balance").setValue(Balance);
                            enterhistory(String.valueOf(Loan));
                            Toast.makeText(EmployeeNotification.this, "Loan Sent To Customer", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EmployeeNotification.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                return true;
            case 122:
                //myAdapter.removeitem(item.getGroupId());
                ref = FirebaseDatabase.getInstance().getReference("LOAN");
                ref.child(accountId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Loan profile = snapshot.getValue(Loan.class);

                        if (profile != null) {
                            //String verified = "false";
                            //ref.child(accountId).child("approve").setValue(verified);
                            myAdapter.removeItem(item.getGroupId());
                            ref.child(accountId).removeValue();
                            Toast.makeText(EmployeeNotification.this, "LOAN Request Declined", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EmployeeNotification.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                return true;
        }
        return super.onContextItemSelected(item);
    }
    private void enterhistory(String amount) {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Transaction");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long maxid=snapshot.child(accountId).getChildrenCount();
                reference.child(accountId).child(String.valueOf(max-maxid)).setValue("Accepted loan request of "+amount+"৳ by agent");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onBackPressed(){
        Intent intent=new Intent(EmployeeNotification.this,EmployeeHome.class);
        startActivity(intent);
    }

    @Override
    public void onItemClicked(Loan loan) {
        accountId=loan.getAccountno();
        Toast.makeText(this, loan.getName()+" Clicked.  Account Id is: "+accountId, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}