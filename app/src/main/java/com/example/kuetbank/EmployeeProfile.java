package com.example.kuetbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeProfile extends AppCompatActivity {

    DatabaseReference dataBaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://kuet-bank-default-rtdb.firebaseio.com"),ref;
    TextView accountid,accounttype,name,mobile,email,gender,dob,address;
    Employee employee;
    String userId;
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        //String email=getIntent().getStringExtra("email");
        accountid=findViewById(R.id.accountid);
        accounttype=findViewById(R.id.employeetype);
        name=findViewById(R.id.name);
        mobile=findViewById(R.id.mobileno);
        email=findViewById(R.id.email);
        gender=findViewById(R.id.gender);
        dob=findViewById(R.id.dob);
        address=findViewById(R.id.address);
        edit=findViewById(R.id.edit);

        FirebaseUser emp = FirebaseAuth.getInstance().getCurrentUser();
        ref=FirebaseDatabase.getInstance().getReference("Employee");
        userId=emp.getUid();
        ref.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Employee profile=snapshot.getValue(Employee.class);

                if(profile!=null)
                {
                    String Name,Accountid,Mobile,Email,Gender,Dob,Address,Emptype;
                    Name=profile.name;
                    Accountid=profile.accountid;
                    Mobile=profile.mobileno;
                    Email=profile.email;
                    Gender=profile.gender;
                    Dob=profile.dateofbirthh;
                    Address=profile.address;
                    Emptype=profile.employeetype;

                    name.setText(Name);
                    accountid.setText(Accountid);
                    mobile.setText(Mobile);
                    email.setText(Email);
                    gender.setText(Gender);
                    dob.setText(Dob);
                    address.setText(Address);
                    accounttype.setText(Emptype);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmployeeProfile.this,EmployeeProfileEdit.class));
            }
        });

    }
    public void onBackPressed(){
        startActivity(new Intent(EmployeeProfile.this,EmployeeHome.class));
    }
}