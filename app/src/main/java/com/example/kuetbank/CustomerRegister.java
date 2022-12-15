package com.example.kuetbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuetbank.databinding.ActivityBlankBinding;
import com.example.kuetbank.databinding.ActivityCustomerOtpsendBinding;
import com.example.kuetbank.databinding.ActivityCustomerRegisterBinding;
import com.google.firebase.FirebaseException;
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
    private EditText Pin,Name,DateOfBirth,MobileNo,Address;
    private Button Next;
    private TextView AccNo;
    private RadioGroup Gender,AccType;
    private RadioButton rbutton;
    private Double Balance=0D;
    private Customer customer;
    private ActivityCustomerRegisterBinding binding;
    FirebaseAuth auth;
    ProgressBar progressBar;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);
        binding= ActivityCustomerRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //progressBar=findViewById(R.id.progressBar);
        auth=FirebaseAuth.getInstance();

        AccNo=findViewById(R.id.accountno);
        Pin=findViewById(R.id.pin);
        Name=findViewById(R.id.name);
        DateOfBirth=findViewById(R.id.dob);
        MobileNo=findViewById(R.id.mobile);
        Address=findViewById(R.id.address);
        Gender=findViewById(R.id.gender);
        AccType=findViewById(R.id.accounttype);
        ref= FirebaseDatabase.getInstance().getReference().child("Customer");

        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.mobile.getText().toString().trim().isEmpty()){
                    Toast.makeText(CustomerRegister.this, "Number can't be empty", Toast.LENGTH_SHORT).show();
                }
                else if(binding.mobile.getText().toString().trim().length()!=10){
                    Toast.makeText(CustomerRegister.this, "Invalid Number", Toast.LENGTH_SHORT).show();
                }
                else{
                    otpsend();
                }
            }
        });


        /*
        AccNo=findViewById(R.id.accountno);
        Pin=findViewById(R.id.pin);
        Name=findViewById(R.id.name);
        DateOfBirth=findViewById(R.id.dob);
        MobileNo=findViewById(R.id.mobile);
        Address=findViewById(R.id.address);
        Gender=findViewById(R.id.gender);
        AccType=findViewById(R.id.accounttype);
        ref= FirebaseDatabase.getInstance().getReference().child("Customer");


        Double random=10000000*Math.random();
        Integer ran=random.intValue();
        String AccountNo=String.valueOf(ran);
        AccNo.setText(AccountNo);



        Next=(Button)findViewById(R.id.next);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String AccountNo=AccNo.getText().toString();
                String pin=Pin.getText().toString();
                String name=Name.getText().toString();
                String dateofbirth=DateOfBirth.getText().toString();
                String mobileno=MobileNo.getText().toString();
                String address=Address.getText().toString();

                int genderid=Gender.getCheckedRadioButtonId();
                rbutton=findViewById(genderid);
                String gender=(String)rbutton.getText();
                int accid2=AccType.getCheckedRadioButtonId();
                rbutton=findViewById(accid2);
                String acctype=(String)rbutton.getText();

                String balance=String.valueOf(Balance);
                //String balance=bal+" ৳";


                if(AccountNo.isEmpty() || pin.isEmpty() || name.isEmpty() || dateofbirth.isEmpty() || mobileno.isEmpty() || acctype.isEmpty() || gender.isEmpty()){
                    Toast.makeText(CustomerRegister.this,"Enter Required data",Toast.LENGTH_SHORT).show();
                }
                else{
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(AccountNo)) {
                                Toast.makeText(CustomerRegister.this, "Account No is already registered", Toast.LENGTH_SHORT).show();
                            } else {
                                customer = new Customer(AccountNo, acctype, name, mobileno, pin, gender, dateofbirth, address, balance);

                            }
                            dataBaseReference.child("users").child(AccountNo).child("Account No").setValue(AccountNo);
                            dataBaseReference.child("users").child(AccountNo).child("MICR").setValue(micr);
                            dataBaseReference.child("users").child(AccountNo).child("Pin").setValue(pin);
                            dataBaseReference.child("users").child(AccountNo).child("Name").setValue(name);
                            dataBaseReference.child("users").child(AccountNo).child("Email").setValue(email);
                            dataBaseReference.child("users").child(AccountNo).child("Date of Birth").setValue(dateofbirth);
                            dataBaseReference.child("users").child(AccountNo).child("Nationality").setValue(nationality);
                            dataBaseReference.child("users").child(AccountNo).child("Mobile No").setValue(mobileno);
                            dataBaseReference.child("users").child(AccountNo).child("Address").setValue(address);

                            dataBaseReference.child("users").child(AccountNo).child("Gender").setValue(gender);
                            dataBaseReference.child("users").child(AccountNo).child("Account Type").setValue(acctype);

                            dataBaseReference.child("users").child(AccountNo).child("Balance").setValue(balance);

                                ref.child(mobileno).setValue(customer);
                                Toast.makeText(CustomerRegister.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        */

    }
    public void onBackPressed(){
        startActivity(new Intent(CustomerRegister.this,MainActivity.class));
    }
    private void otpsend(){
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(CustomerRegister.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {



                Double random=10000000*Math.random();
                Integer ran=random.intValue();
                String AccountNo=String.valueOf(ran);
                AccNo.setText(AccountNo);

                String pin=Pin.getText().toString();
                String name=Name.getText().toString();
                String dateofbirth=DateOfBirth.getText().toString();
                String mobileno=MobileNo.getText().toString();
                String address=Address.getText().toString();

                int genderid=Gender.getCheckedRadioButtonId();
                rbutton=findViewById(genderid);
                String gender=(String)rbutton.getText();
                int accid2=AccType.getCheckedRadioButtonId();
                rbutton=findViewById(accid2);
                String acctype=(String)rbutton.getText();

                String balance=String.valueOf(Balance);
                //String balance=bal+" ৳";


                if(AccountNo.isEmpty() || pin.isEmpty() || name.isEmpty() || dateofbirth.isEmpty() || mobileno.isEmpty() || acctype.isEmpty() || gender.isEmpty()){
                    Toast.makeText(CustomerRegister.this,"Enter Required data",Toast.LENGTH_SHORT).show();
                }
                else{
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(AccountNo)) {
                                Toast.makeText(CustomerRegister.this, "Account No is already registered", Toast.LENGTH_SHORT).show();
                            } else {
                                customer = new Customer(AccountNo, acctype, name, mobileno, pin, gender, dateofbirth, address, balance);
                                ref.child(mobileno).setValue(customer);
                                Toast.makeText(CustomerRegister.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


                Intent intent=new Intent(CustomerRegister.this,CustomerOTPsend.class);
                intent.putExtra("phone",binding.mobile.getText().toString().trim());
                intent.putExtra("verificationId",verificationId);
                startActivity(intent);
            }
        };
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber("+880"+binding.mobile.getText().toString().trim())       // Phone number to verify
                        .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}