package com.example.kuetbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
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

public class EmployeeRegister extends AppCompatActivity {

    DatabaseReference dataBaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kuet-bank-default-rtdb.firebaseio.com");
    private EditText Pass, Name, DateOfBirth, Nationality, MobileNo, SecurityQ, Answer, Email, Address;
    private Button Next;
    private TextView AccID, Micr;
    private RadioGroup Gender, AccType;
    private RadioButton rbutton;
    private Double Balance = 0D;
    FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_register);

        AccID = findViewById(R.id.accountno);
        Pass = findViewById(R.id.pass);
        Micr = findViewById(R.id.micr);
        Email = findViewById(R.id.email);
        Name = findViewById(R.id.name);
        DateOfBirth = findViewById(R.id.dateofbirth);
        Nationality = findViewById(R.id.nationality);
        MobileNo = findViewById(R.id.mobile);
        Address = findViewById(R.id.address);
        SecurityQ = findViewById(R.id.security);
        Answer = findViewById(R.id.answer);
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
        Micr.setText(MicrNo);


        Next = (Button) findViewById(R.id.next);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersignup();
            }
        });
    }

    private void usersignup() {
        String AccountID = AccID.getText().toString();
        String pass = Pass.getText().toString();
        String name = Name.getText().toString();
        String email = Email.getText().toString();
        String dateofbirth = DateOfBirth.getText().toString();
        String mobileno = MobileNo.getText().toString();
        String address = Address.getText().toString();

        int genderid = Gender.getCheckedRadioButtonId();
        rbutton = findViewById(genderid);
        String gender = (String) rbutton.getText();
        int accid2 = AccType.getCheckedRadioButtonId();
        rbutton = findViewById(accid2);
        String acctype = (String) rbutton.getText();

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
        mauth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Employee employee=new Employee(AccountID,acctype,name,mobileno,email,pass,gender,dateofbirth,address);
                    FirebaseDatabase.getInstance().getReference("Employee").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(employee).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
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
                else {
                    Toast.makeText(EmployeeRegister.this, "Failed to register"+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void onBackPressed(){
        startActivity(new Intent(EmployeeRegister.this,LoginEmployee.class));
    }
}