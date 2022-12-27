package com.example.kuetbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeTransfer extends AppCompatActivity {

    DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Customer");
    Button search,transfer;
    TextView name;
    EditText accountno,amount;
    String ACC,RACC;
    long max=2147483647;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_transfer);

        search=findViewById(R.id.search);
        transfer=findViewById(R.id.transfer);
        name=findViewById(R.id.name);
        accountno=findViewById(R.id.accountno);
        amount=findViewById(R.id.amount);
        ACC=getIntent().getStringExtra("accountno");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RACC=accountno.getText().toString().trim();
                if(RACC.isEmpty()){
                    accountno.setError("Enter Account No");
                    accountno.requestFocus();
                    return;
                }
                else{
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(RACC)){
                                if(RACC.equals(ACC)){
                                    accountno.setError("Error");
                                    accountno.requestFocus();
                                    Toast.makeText(EmployeeTransfer.this, "Sender and Reciever Account No can't be same", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                else{
                                    String Name=snapshot.child(RACC).child("name").getValue().toString();
                                    name.setText(Name);
                                    Toast.makeText(EmployeeTransfer.this, "Enter Amount to go forward", Toast.LENGTH_SHORT).show();
                                    transfer.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Double Amount=Double.parseDouble(amount.getText().toString().trim());
                                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    Double balance=Double.parseDouble(snapshot.child(ACC).child("balance").getValue().toString());
                                                    if(balance>Amount){
                                                        balance-=Amount;
                                                        ref.child(ACC).child("balance").setValue(String.valueOf(balance));
                                                        Double balance2=Double.parseDouble(snapshot.child(RACC).child("balance").getValue().toString());
                                                        balance2+=Amount;
                                                        ref.child(RACC).child("balance").setValue(String.valueOf(balance2));
                                                        enterhistory(String.valueOf(Amount));
                                                        Toast.makeText(EmployeeTransfer.this, "Money Transfered Successfully", Toast.LENGTH_SHORT).show();
                                                        Intent intent=new Intent(EmployeeTransfer.this,EmployeeOptionForCustomer.class);
                                                        intent.putExtra("accountno",ACC);
                                                        startActivity(intent);
                                                    }
                                                    else{
                                                        amount.setError("Balance Shortage");
                                                        amount.requestFocus();
                                                        return;
                                                    }

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }
                                    });
                                }
                            }
                            else{
                                accountno.setError("Account No doesn't exist");
                                accountno.requestFocus();
                                return;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
    private void enterhistory(String amount) {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Transaction");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long maxid=snapshot.child(ACC).getChildrenCount();
                reference.child(ACC).child(String.valueOf(max-maxid)).setValue("Transferred "+amount+"৳ to "+RACC+" by agent");
                maxid=snapshot.child(RACC).getChildrenCount();
                reference.child(RACC).child(String.valueOf(max-maxid)).setValue("Transferred "+amount+"৳ from "+ACC+" by agent");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void onBackPressed(){
        Intent intent=new Intent(EmployeeTransfer.this,EmployeeOptionForCustomer.class);
        intent.putExtra("accountno",ACC);
        startActivity(intent);
    }
}