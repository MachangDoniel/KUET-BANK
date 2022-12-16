package com.example.kuetbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuetbank.databinding.ActivityCustomerOtpsendBinding;
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
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class CustomerOTPsend extends AppCompatActivity {

    private ActivityCustomerOtpsendBinding binding;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    TextView Mobile;
    Button Send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_otpsend);
        binding=ActivityCustomerOtpsendBinding.inflate(getLayoutInflater());
        binding.progressBar.setVisibility(View.GONE);
        Mobile=findViewById(R.id.mobile);
        Send=findViewById(R.id.send);
        String phone=getIntent().getStringExtra("phone");
        Mobile.setText(phone);

        mAuth = FirebaseAuth.getInstance();

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.mobile.getText().toString().trim().isEmpty()) {
                    Toast.makeText(CustomerOTPsend.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                } else if (binding.mobile.getText().toString().trim().length() != 10) {
                    Toast.makeText(CustomerOTPsend.this, "Type valid Phone Number", Toast.LENGTH_SHORT).show();
                } else {
                    otpSend();
                }
            }
        });
    }
    private void otpSend() {
        //binding.progressBar.setVisibility(View.VISIBLE);
        //binding.send.setVisibility(View.INVISIBLE);

        Toast.makeText(this, "OTP sending...", Toast.LENGTH_SHORT).show();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                binding.progressBar.setVisibility(View.GONE);
                binding.send.setVisibility(View.VISIBLE);
                Toast.makeText(CustomerOTPsend.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                binding.progressBar.setVisibility(View.GONE);
                binding.send.setVisibility(View.VISIBLE);
                Toast.makeText(CustomerOTPsend.this, "OTP is successfully send.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CustomerOTPsend.this, CustomerOTPverify.class);
                intent.putExtra("phone", Mobile.getText().toString().trim());
                intent.putExtra("verificationId", verificationId);
                startActivity(intent);
            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+880" + binding.mobile.getText().toString().trim())
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}