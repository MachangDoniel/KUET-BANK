package com.example.kuetbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.kuetbank.databinding.ActivityCustomerOtpsendBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class CustomerOTPsend extends AppCompatActivity {

    ActivityCustomerOtpsendBinding binding;
    String verificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_otpsend);

        binding=ActivityCustomerOtpsendBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.mobile.setText(String.format("+990-%s",getIntent().getStringExtra("phone")));
        verificationId=getIntent().getStringExtra("verificationId");
        binding.resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CustomerOTPsend.this, "OTP Resend Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verificationId!=null){
                    String code=binding.otp.getText().toString().trim();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent=new Intent(CustomerOTPsend.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(CustomerOTPsend.this, "OTP is not valid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}