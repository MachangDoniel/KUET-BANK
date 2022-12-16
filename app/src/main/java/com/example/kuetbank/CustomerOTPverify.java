package com.example.kuetbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuetbank.databinding.ActivityCustomerOtpsendBinding;
import com.example.kuetbank.databinding.ActivityCustomerOtpverifyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class CustomerOTPverify extends AppCompatActivity {

    private String verificationId;
    private ProgressBar progressBar;
    private TextView Mobile,otp1,otp2,otp3,otp4,otp5,otp6;
    private Button Verify,Resend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_otpverify);

        editTextInput();

        otp1.findViewById(R.id.otp1);
        otp2.findViewById(R.id.otp2);
        otp3.findViewById(R.id.otp3);
        otp4.findViewById(R.id.otp4);
        otp5.findViewById(R.id.otp5);
        otp6.findViewById(R.id.otp6);
        progressBar.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        Mobile.findViewById(R.id.mobile);
        Resend.findViewById(R.id.resend);
        Verify.findViewById(R.id.verify);
        Mobile.setText(String.format(
                "+990-%s",getIntent().getStringExtra("phone")
        ));
        verificationId=getIntent().getStringExtra("verificationId");
        Resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CustomerOTPverify.this, "OTP Resend Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Verify.setVisibility(View.INVISIBLE);
                if(otp1.getText().toString().trim().isEmpty() ||
                        otp2.getText().toString().trim().isEmpty() ||
                        otp3.getText().toString().trim().isEmpty() ||
                        otp4.getText().toString().trim().isEmpty() ||
                        otp5.getText().toString().trim().isEmpty() ||
                        otp6.getText().toString().trim().isEmpty()){
                    Toast.makeText(CustomerOTPverify.this, "OTP is Empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(verificationId!=null){
                        String code=otp1.getText().toString().trim()+
                                otp2.getText().toString().trim()+
                                otp3.getText().toString().trim()+
                                otp4.getText().toString().trim()+
                                otp5.getText().toString().trim()+
                                otp6.getText().toString().trim();
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
                        FirebaseAuth
                                .getInstance()
                                .signInWithCredential(credential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    progressBar.setVisibility(View.VISIBLE);
                                    Verify.setVisibility(View.INVISIBLE);
                                    Toast.makeText(CustomerOTPverify.this, "Welcome...", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(CustomerOTPverify.this,MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                                else{
                                    progressBar.setVisibility(View.GONE);
                                    Verify.setVisibility(View.VISIBLE);
                                    Toast.makeText(CustomerOTPverify.this, "OTP is not Valid!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });

    }

    private void editTextInput() {
        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                otp2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                otp3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                otp4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                otp5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                otp6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}