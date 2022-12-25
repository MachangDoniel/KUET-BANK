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

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmployeeApplicants extends AppCompatActivity implements SelectListener2 {

    DatabaseReference ref,dataBaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kuet-bank-default-rtdb.firebaseio.com");
    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter2 myAdapter;
    String accountId="",userId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_applicants);

        recyclerView=findViewById(R.id.employeelist);
        database= FirebaseDatabase.getInstance().getReference("Employee");
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

        ArrayList<Employee>list = new ArrayList<>();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Employee user=dataSnapshot.getValue(Employee.class);
                    if((key.isEmpty() || (user != null && user.name.toLowerCase().contains(key))) && user.isverified()!=true){
                        list.add(user);
                    }
                }
                myAdapter = new MyAdapter2(EmployeeApplicants.this,list,EmployeeApplicants.this);
                recyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClicked(Employee employee) {
        accountId=employee.getAccountid();
        dataBaseReference.child("Extra2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(accountId)){
                    userId=snapshot.child(accountId).child("uid").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Toast.makeText(this, employee.getName()+" Clicked.  Account Id is: "+accountId, Toast.LENGTH_SHORT).show();
    }
    public void onBackPressed(){
        Intent intent=new Intent(EmployeeApplicants.this,ManagerHome.class);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case 121:
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Employee");
                ref.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Employee profile=snapshot.getValue(Employee.class);

                        if(profile!=null)
                        {
                            boolean verified=true;
                            ref.child(userId).child("verified").setValue(verified);
                            myAdapter.removeItem(item.getGroupId());
                            Toast.makeText(EmployeeApplicants.this, "Applicants Added Successfully as Employee", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(EmployeeApplicants.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                return true;
            case 122:
                //myAdapter.removeitem(item.getGroupId());
                ref= FirebaseDatabase.getInstance().getReference("Employee");
                ref.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Employee profile=snapshot.getValue(Employee.class);

                        if(profile!=null)
                        {
                            boolean verified=false;
                            ref.child(userId).child("verified").setValue(verified);
                            //myAdapter.removeItem(item.getGroupId());
                            Toast.makeText(EmployeeApplicants.this, "Applicants Deleted from Employee List", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(EmployeeApplicants.this, "Error", Toast.LENGTH_SHORT).show();
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
}