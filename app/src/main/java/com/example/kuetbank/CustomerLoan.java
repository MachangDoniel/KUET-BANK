package com.example.kuetbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerLoan extends AppCompatActivity {

    DatabaseReference dataBaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://kuet-bank-default-rtdb.firebaseio.com");
    private TextView t1,t2;
    Button balance,profile,transfer,payment,loan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_loan);

        t1=findViewById(R.id.uname);
        t2=findViewById(R.id.showbalance);
        Bundle b1=getIntent().getExtras();
        int Balance=0,count=0;
        String ACC=getIntent().getStringExtra("Account_No");
        dataBaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(ACC)){
                    String getName=snapshot.child(ACC).child("Name").getValue(String.class);
                    t1.setText(getName);
                }
                else{
                    Toast.makeText(CustomerLoan.this,"Account No doesn't exists",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        balance=findViewById(R.id.balance);
        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(ACC)){
                            String getBalance=snapshot.child(ACC).child("Balance").getValue(String.class);
                            String s=getBalance+" ৳";
                            t2.setText(s);
                        }
                        else{
                            Toast.makeText(CustomerLoan.this,"Account No doesn't exists",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        profile=findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerLoan.this,CustomerProfile.class);
                intent.putExtra("Account_No",ACC);
                startActivity(intent);
            }
        });
        transfer=findViewById(R.id.transfer);
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerLoan.this,CustomerTransfer.class);
                intent.putExtra("Account_No",ACC);
                startActivity(intent);
            }
        });
        payment=findViewById(R.id.payment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerLoan.this,CustomerPayment.class);
                intent.putExtra("Account_No",ACC);
                startActivity(intent);
            }
        });
        loan=findViewById(R.id.loan);
        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerLoan.this,CustomerLoan.class);
                intent.putExtra("Account_No",ACC);
                startActivity(intent);
            }
        });
    }
}