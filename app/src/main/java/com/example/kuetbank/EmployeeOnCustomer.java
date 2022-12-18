package com.example.kuetbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

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

public class EmployeeOnCustomer extends AppCompatActivity {

    DatabaseReference ref,dataBaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://kuet-bank-default-rtdb.firebaseio.com");
    TextView Accountno,accounttype,name,mobile,gender,dob,address,balance;
    Customer customer;
    String userId;
    Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_on_customer);

        String ACC=getIntent().getStringExtra("accountno");
        Accountno=findViewById(R.id.accountno);
        accounttype=findViewById(R.id.accounttype);
        name=findViewById(R.id.name);
        mobile=findViewById(R.id.mobileno);
        gender=findViewById(R.id.gender);
        dob=findViewById(R.id.dob);
        address=findViewById(R.id.address);
        balance=findViewById(R.id.balance);
        edit=findViewById(R.id.edit);

        ref= FirebaseDatabase.getInstance().getReference().child("Customer");

        String Name,Mobile,Email,Gender,Dob,Address,Acctype,Balance;
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(ACC)){
                    //String getName=snapshot.child(ACC).child("name").getValue(String.class);
                    String getName=snapshot.child(ACC).child("name").getValue().toString();
                    name.setText(getName);
                    //String getdob=snapshot.child(ACC).child("dob").getValue(String.class);
                    String getdob=snapshot.child(ACC).child("dob").getValue().toString();
                    dob.setText(getdob);
                    //String getgen=snapshot.child(ACC).child("gender").getValue(String.class);
                    String getgen=snapshot.child(ACC).child("gender").getValue().toString();
                    gender.setText(getgen);
                    //String getaccno=snapshot.child(ACC).child("accountno").getValue(String.class);
                    String getaccno=snapshot.child(ACC).child("accountno").getValue().toString();
                    Accountno.setText(getaccno);
                    String getemp=snapshot.child(ACC).child("accounttype").getValue().toString();
                    accounttype.setText(getemp);
                    //String getmob=snapshot.child(ACC).child("mobile").getValue(String.class);
                    String getmob=snapshot.child(ACC).child("mobile").getValue().toString();
                    mobile.setText(getmob);
                    //String getadd=snapshot.child(ACC).child("address").getValue(String.class);
                    String getadd=snapshot.child(ACC).child("address").getValue().toString();
                    address.setText(getadd);
                    String getbal=snapshot.child(ACC).child("balance").getValue().toString();
                    getbal+=" ৳";
                    balance.setText(getbal);
                }
                else{
                    Toast.makeText(EmployeeOnCustomer.this,"Error",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        /*
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmployeeOnCustomer.this,EmployeeProfileEdit.class));
            }
        });

         */


    }
    public void onBackPressed(){
        Intent intent=new Intent(EmployeeOnCustomer.this,CustomerList.class);
        startActivity(intent);
    }
}