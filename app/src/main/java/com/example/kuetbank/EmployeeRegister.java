package com.example.kuetbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class EmployeeRegister extends AppCompatActivity {

    DatabaseReference dataBaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kuet-bank-default-rtdb.firebaseio.com");
    private EditText Pass, Pass2, Name, DateOfBirth, MobileNo, Email, Address;
    private Button Next;
    private TextView AccID;
    private RadioGroup Gender, AccType;
    private RadioButton rbutton;
    private Double Balance = 0D;
    FirebaseAuth mauth;
    String AccountID,pass,pass2,email,name,dateofbirth,mobileno,address,gender,acctype,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_register);

        Calendar calender=Calendar.getInstance();
        final int year=calender.get(Calendar.YEAR);
        final int month=calender.get(Calendar.MONTH);
        final int day=calender.get(Calendar.DAY_OF_MONTH);
        AccID = findViewById(R.id.accountno);
        Pass = findViewById(R.id.password);
        Pass2= findViewById(R.id.password2);
        Email = findViewById(R.id.email);
        Name = findViewById(R.id.name);
        DateOfBirth = findViewById(R.id.dob);
        MobileNo = findViewById(R.id.mobile);
        Address = findViewById(R.id.address);
        Gender = findViewById(R.id.gender);
        AccType = findViewById(R.id.accounttype);
        mauth = FirebaseAuth.getInstance();

        Double random = 10000000 * Math.random();
        Double random2 = 10000 * Math.random();
        Integer ran = random.intValue();
        Integer ran2 = random2.intValue();
        String AccountNo = String.valueOf(ran);
        String MicrNo = String.valueOf(ran2);
        AccID.setText(AccountNo);

        DateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(EmployeeRegister.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;
                        String date=day+"/"+month+"/"+year;
                        DateOfBirth.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


        Next = (Button) findViewById(R.id.next);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersignup();
            }
        });
    }

    private void usersignup() {
        AccountID = AccID.getText().toString();
        pass = Pass.getText().toString();
        pass2=Pass2.getText().toString();
        name = Name.getText().toString().trim();
        email = Email.getText().toString().trim();
        dateofbirth = DateOfBirth.getText().toString();
        mobileno = MobileNo.getText().toString().trim();
        address = Address.getText().toString().trim();

        int genderid = Gender.getCheckedRadioButtonId();
        rbutton = findViewById(genderid);
        gender = (String) rbutton.getText();
        int accid2 = AccType.getCheckedRadioButtonId();
        rbutton = findViewById(accid2);
        acctype = (String) rbutton.getText();

        if(email.isEmpty()){
            //Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            Email.setError("Enter Email");
            Email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
            Email.setError("Please Enter Valid Email");
            Email.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            //Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            Pass.setError("Please Enter Password");
            Pass.requestFocus();
            return;
        }
        if(pass.length()<6){
            //Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            Pass.setError("Minimum password length must be 6 character");
            Pass.requestFocus();
            return;
        }
        if(!pass2.equals(pass)){
            //Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            Pass2.setError("Password doesn't match");
            Pass2.requestFocus();
            return;
        }
        mauth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    dataBaseReference.child("Extra2").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(AccountID)){
                                MobileNo.setError("Already Registered With this Mobile No");
                                MobileNo.requestFocus();
                                return;
                            }
                            else{
                                boolean verified=false;
                                Employee employee=new Employee(AccountID,acctype,name,mobileno,email,pass,gender,dateofbirth,address,verified);
                                uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                FirebaseDatabase.getInstance().getReference("Employee").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(employee).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            accountnotoid();
                                            Toast.makeText(EmployeeRegister.this, "Registered", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(EmployeeRegister.this,LoginEmployee.class);
                                            startActivity(intent);
                                        }
                                        else {
                                            Toast.makeText(EmployeeRegister.this, "Failed to register"+task.getException(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else {
                    Toast.makeText(EmployeeRegister.this, "Failed to register"+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void accountnotoid() {
        dataBaseReference.child("Extra2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataBaseReference.child("Extra2").child(AccountID).child("uid").setValue(uid);
                Toast.makeText(EmployeeRegister.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void onBackPressed(){
        startActivity(new Intent(EmployeeRegister.this,LoginEmployee.class));
    }
}