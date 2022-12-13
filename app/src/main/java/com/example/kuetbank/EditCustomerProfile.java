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

public class EditCustomerProfile extends AppCompatActivity {

    DatabaseReference ref,dataBaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://kuet-bank-default-rtdb.firebaseio.com");
    private TextView t1,t2,name,dob,gender,nationality,accountno,email,mobileno,address;
    Button balance,profile,transfer,payment,loan,save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer_profile);

        ref=FirebaseDatabase.getInstance().getReference("Customer");
        t1=findViewById(R.id.uname);
        t2=findViewById(R.id.showbalance);
        name=findViewById(R.id.name);
        dob=findViewById(R.id.dob);
        gender=findViewById(R.id.gender);
        nationality=findViewById(R.id.nationality);
        accountno=findViewById(R.id.accountno);
        email=findViewById(R.id.email);
        mobileno=findViewById(R.id.mobile);
        address=findViewById(R.id.address);

        String ACC=getIntent().getStringExtra("Account_No");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(ACC)){
                    //String getName=snapshot.child(ACC).child("name").getValue(String.class);
                    String getName=snapshot.child(ACC).child("name").getValue().toString();
                    t1.setText(getName);
                    name.setText(getName);
                    //String getdob=snapshot.child(ACC).child("dob").getValue(String.class);
                    String getdob=snapshot.child(ACC).child("dob").getValue().toString();
                    dob.setText(getdob);
                    //String getgen=snapshot.child(ACC).child("gender").getValue(String.class);
                    String getgen=snapshot.child(ACC).child("gender").getValue().toString();
                    gender.setText(getgen);
                    //String getaccno=snapshot.child(ACC).child("accountno").getValue(String.class);
                    String getaccno=snapshot.child(ACC).child("accountno").getValue().toString();
                    accountno.setText(getaccno);
                    //String getemail=snapshot.child(ACC).child("email").getValue(String.class);
                    String getemail=snapshot.child(ACC).child("email").getValue().toString();
                    email.setText(getemail);
                    //String getmob=snapshot.child(ACC).child("mobile").getValue(String.class);
                    String getmob=snapshot.child(ACC).child("mobile").getValue().toString();
                    mobileno.setText(getmob);
                    //String getadd=snapshot.child(ACC).child("address").getValue(String.class);
                    String getadd=snapshot.child(ACC).child("address").getValue().toString();
                    address.setText(getadd);
                }
                else{
                    Toast.makeText(EditCustomerProfile.this,"Account No doesn't exists",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        balance=findViewById(R.id.bal);
        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(ACC)){
                            //String getBalance=snapshot.child(ACC).child("balance").getValue(String.class);
                            String getBalance=snapshot.child(ACC).child("balance").getValue().toString();
                            t2.setText(getBalance);
                        }
                        else{
                            Toast.makeText(EditCustomerProfile.this,"Account No doesn't exists",Toast.LENGTH_SHORT).show();
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
                Intent intent=new Intent(EditCustomerProfile.this,CustomerProfile.class);
                intent.putExtra("Account_No",ACC);
                startActivity(intent);
            }
        });
        transfer=findViewById(R.id.transfer);
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EditCustomerProfile.this,CustomerTransfer.class);
                intent.putExtra("Account_No",ACC);
                startActivity(intent);
            }
        });
        payment=findViewById(R.id.payment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EditCustomerProfile.this,CustomerPayment.class);
                intent.putExtra("Account_No",ACC);
                startActivity(intent);
            }
        });
        loan=findViewById(R.id.loan);
        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EditCustomerProfile.this,CustomerLoan.class);
                intent.putExtra("Account_No",ACC);
                startActivity(intent);
            }
        });
        save=findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name=name.getText().toString();
                ref.child(ACC).child("name").setValue(Name);
                String Dob=dob.getText().toString();
                ref.child(ACC).child("dob").setValue(Dob);
                String Gender=gender.getText().toString();
                ref.child(ACC).child("gender").setValue(Gender);
                String AccountNo=accountno.getText().toString();
                ref.child(ACC).child("accountno").setValue(AccountNo);
                String Email=email.getText().toString();
                ref.child(ACC).child("email").setValue(Email);
                String MobileNo=mobileno.getText().toString();
                ref.child(ACC).child("mobile").setValue(MobileNo);
                String Address=address.getText().toString();
                ref.child(ACC).child("address").setValue(Address);

                Intent intent=new Intent(EditCustomerProfile.this,CustomerProfile.class);
                intent.putExtra("Account_No",ACC);
                startActivity(intent);
            }
        });
    }
    public void onBackPressed(){
        startActivity(new Intent(EditCustomerProfile.this,CustomerProfile.class));
    }
}