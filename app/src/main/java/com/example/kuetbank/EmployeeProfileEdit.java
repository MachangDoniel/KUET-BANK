package com.example.kuetbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeProfileEdit extends AppCompatActivity {

    DatabaseReference dataBaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kuet-bank-default-rtdb.firebaseio.com"), ref;
    TextView accountid, accounttype;
    EditText name, mobile, email, gender, dob, address;
    Employee employee;
    String userId,Pass;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile_edit);


        //String email=getIntent().getStringExtra("email");
        accountid = findViewById(R.id.accountid);
        accounttype = findViewById(R.id.employeetype);
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobileno);
        email = findViewById(R.id.email);
        gender = findViewById(R.id.gender);
        dob = findViewById(R.id.dob);
        address = findViewById(R.id.address);
        save = findViewById(R.id.save);

        FirebaseUser emp = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference("Employee");
        userId = emp.getUid();
        ref.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Employee profile = snapshot.getValue(Employee.class);

                if (profile != null) {
                    String Name, Accountid, Mobile, Email, Gender, Dob, Address, Emptype;
                    Name = profile.name;
                    Accountid = profile.accountid;
                    Mobile = profile.mobileno;
                    Email = profile.email;
                    Gender = profile.gender;
                    Dob = profile.dateofbirthh;
                    Address = profile.address;
                    Emptype = profile.employeetype;
                    Pass=profile.pass;


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
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String AccountId=accountid.getText().toString();
                String Emptype=accounttype.getText().toString();
                String Name = name.getText().toString();
                String Email = email.getText().toString();
                String Dob = dob.getText().toString();
                String Mobile = mobile.getText().toString();
                String Address = address.getText().toString();
                String Gender = gender.getText().toString();

                if (Email.isEmpty()) {
                    //Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
                    email.setError("Enter Email");
                    email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    //Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
                    email.setError("Please Enter Valid Email");
                    email.requestFocus();
                    return;
                }
                else
                {
                    boolean verified=true;
                    Employee employee = new Employee(AccountId,Emptype,Name, Mobile, Email,Pass,Gender,Dob, Address,verified);
                    FirebaseDatabase.getInstance().getReference("Employee").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(employee).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(EmployeeProfileEdit.this, "Edited Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EmployeeProfileEdit.this, EmployeeProfile.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(EmployeeProfileEdit.this, "Failed to Edit" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
    public void onBackPressed(){
        startActivity(new Intent(EmployeeProfileEdit.this,EmployeeProfile.class));
    }
}