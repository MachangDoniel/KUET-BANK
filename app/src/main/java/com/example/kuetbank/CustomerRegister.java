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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuetbank.databinding.ActivityCustomerRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class CustomerRegister extends AppCompatActivity {

    DatabaseReference ref;
    EditText Pin, Name, DateOfBirth, MobileNo, Address;
    Button Next;
    TextView AccNo;
    RadioGroup Gender, AccType;
    RadioButton rbutton;
    FirebaseAuth mauth;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String mobileno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        AccNo = findViewById(R.id.accountno);
        Pin = findViewById(R.id.pin);
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
        AccNo.setText(AccountNo);


        Next = (Button) findViewById(R.id.next);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(CustomerRegister.this, "Next Clicked", Toast.LENGTH_SHORT).show();
                employeesignup();
                //startActivity(new Intent(CustomerRegister.this,EmployeeRegister.class));
            }
        });
    }

    private void employeesignup() {
        //ref=FirebaseDatabase.getInstance().getReference("Customer");
        String AccountNo = AccNo.getText().toString();
        String pin = Pin.getText().toString();
        String name = Name.getText().toString();
        String dateofbirth = DateOfBirth.getText().toString();
        mobileno = MobileNo.getText().toString();
        String address = Address.getText().toString();

        int genderid = Gender.getCheckedRadioButtonId();
        rbutton = findViewById(genderid);
        String gender = (String) rbutton.getText();
        int accid2 = AccType.getCheckedRadioButtonId();
        rbutton = findViewById(accid2);
        String acctype = (String) rbutton.getText();
        Double Balance=0D;
        String balance = String.valueOf(Balance);

        if(name.isEmpty()){
            //Toast.makeText(CustomerRegister.this, "Enter Name", Toast.LENGTH_SHORT).show();
            Name.setError("Please Enter Name");
            Name.requestFocus();
            return;
        }
        if(mobileno.isEmpty()){
            //Toast.makeText(CustomerRegister.this, "Enter Name", Toast.LENGTH_SHORT).show();
            MobileNo.setError("Please Enter Name");
            MobileNo.requestFocus();
            return;
        }
        if(mobileno.length()!=10){
            //Toast.makeText(CustomerRegister.this, "Enter Name", Toast.LENGTH_SHORT).show();
            MobileNo.setError("Please Enter Valid Number");
            MobileNo.requestFocus();
            return;
        }
        if(pin.isEmpty()){
            //Toast.makeText(CustomerRegister.this, "Enter Name", Toast.LENGTH_SHORT).show();
            Pin.setError("Please Enter Pin");
            Pin.requestFocus();
            return;
        }
        if(pin.length()<3){
            //Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            Pin.setError("Minimum password length must be 6 character");
            Pin.requestFocus();
            return;
        }
        if(dateofbirth.isEmpty()){
            //Toast.makeText(CustomerRegister.this, "Enter Name", Toast.LENGTH_SHORT).show();
            DateOfBirth.setError("Please Enter date of birth");
            DateOfBirth.requestFocus();
            return;
        }

        if(AccountNo.isEmpty() || acctype.isEmpty() || name.isEmpty() || mobileno.isEmpty() || pin.isEmpty() || gender.isEmpty())
        {
            Toast.makeText(this, "Enter Required Data", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            Customer customer=new Customer(AccountNo,acctype,name,mobileno,pin,gender,dateofbirth,address,balance);
            FirebaseDatabase.getInstance().getReference("Customer").child(mobileno).setValue(customer).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(CustomerRegister.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(CustomerRegister.this,CustomerOTPsend.class);
                        intent.putExtra("phone",mobileno);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(CustomerRegister.this, "Failed to register"+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public void onBackPressed(){
        startActivity(new Intent(CustomerRegister.this,MainActivity.class));
    }
}