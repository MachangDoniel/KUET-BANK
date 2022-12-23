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

public class ManagerProfile extends AppCompatActivity {

    DatabaseReference dataBaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://kuet-bank-default-rtdb.firebaseio.com"),ref;
    TextView accountid,accounttype,name,mobile,email,gender,dob,address;
    Employee employee;
    String userId;
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_profile);

        //String email=getIntent().getStringExtra("email");
        name=findViewById(R.id.name);
        mobile=findViewById(R.id.mobileno);
        email=findViewById(R.id.email);
        gender=findViewById(R.id.gender);
        dob=findViewById(R.id.dob);
        address=findViewById(R.id.address);

        FirebaseUser emp = FirebaseAuth.getInstance().getCurrentUser();
        ref=FirebaseDatabase.getInstance().getReference("Manager");
        userId=emp.getUid();
        ref.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Manager profile=snapshot.getValue(Manager.class);

                if(profile!=null)
                {
                    String Name,Accountid,Mobile,Email,Gender,Dob,Address,Emptype;
                    Name=profile.name;
                    Mobile=profile.mobileno;
                    Email=profile.email;
                    Gender=profile.gender;
                    Dob=profile.dateofbirthh;
                    Address=profile.address;

                    name.setText(Name);
                    mobile.setText(Mobile);
                    email.setText(Email);
                    gender.setText(Gender);
                    dob.setText(Dob);
                    address.setText(Address);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void onBackPressed(){
        startActivity(new Intent(ManagerProfile.this,ManagerHome.class));
    }
}