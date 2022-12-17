package com.example.kuetbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerProfile extends AppCompatActivity {

    DatabaseReference ref,dataBaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://kuet-bank-default-rtdb.firebaseio.com");
    private TextView name,dob,gender,accountno,mobileno,address,Accounttype;
    Button balance,profile,transfer,payment,loan,edit;
    String ACC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        ref= FirebaseDatabase.getInstance().getReference("Customer");
        name=findViewById(R.id.name);
        dob=findViewById(R.id.dob);
        gender=findViewById(R.id.gender);
        accountno=findViewById(R.id.accountno);
        mobileno=findViewById(R.id.mobileno);
        address=findViewById(R.id.address);
        Accounttype=findViewById(R.id.accounttype);
        edit=findViewById(R.id.edit);

        ACC=getIntent().getStringExtra("accountno");
        mobileno.setText(ACC);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(ACC)){

                    //String getName=snapshot.child(ACC).child("name").getValue(String.class);
                    String getName=snapshot.child(ACC).child("name").getValue().toString();
                    name.setText(getName);
                    //String getName=snapshot.child(ACC).child("name").getValue(String.class);
                    String getacctype=snapshot.child(ACC).child("accounttype").getValue().toString();
                    Accounttype.setText(getacctype);
                    //String getdob=snapshot.child(ACC).child("dob").getValue(String.class);
                    String getdob=snapshot.child(ACC).child("dob").getValue().toString();
                    dob.setText(getdob);
                    //String getgen=snapshot.child(ACC).child("gender").getValue(String.class);
                    String getgen=snapshot.child(ACC).child("gender").getValue().toString();
                    gender.setText(getgen);
                    //String getaccno=snapshot.child(ACC).child("accountno").getValue(String.class);
                    String getaccno=snapshot.child(ACC).child("accountno").getValue().toString();
                    accountno.setText(getaccno);
                    //String getmob=snapshot.child(ACC).child("mobile").getValue(String.class);
                    String getmob=snapshot.child(ACC).child("mobile").getValue().toString();
                    mobileno.setText(getmob);
                    //String getadd=snapshot.child(ACC).child("address").getValue(String.class);
                    String getadd=snapshot.child(ACC).child("address").getValue().toString();
                    address.setText(getadd);


                }
                else{
                    Toast.makeText(CustomerProfile.this,"Account No doesn't exists",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerProfile.this,EditCustomerProfile.class);
                intent.putExtra("accountno",ACC);
                startActivity(intent);
            }
        });

    }
    public void onBackPressed(){
        Intent intent=new Intent(CustomerProfile.this,CustomerHome.class);
        intent.putExtra("accountno",ACC);
        startActivity(intent);
    }
}