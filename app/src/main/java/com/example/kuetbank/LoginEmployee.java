package com.example.kuetbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class LoginEmployee extends AppCompatActivity {

    Button register, login, Forgotpassword;
    EditText Email, password;
    FirebaseAuth mauth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_employee);

        Email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);
        mauth = FirebaseAuth.getInstance();
        Forgotpassword=findViewById(R.id.forgotpassword);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);


        //String id = "1907121", pass = "121";
        //1907121 & 121

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userlogin();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(LoginEmployee.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginEmployee.this, EmployeeRegister.class);
                //intent.putExtra("s", id);
                startActivity(intent);
            }
        });
        Forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginEmployee.this, EmployeeForgotPassword.class);
                startActivity(intent);
            }
        });
    }

    private void userlogin() {
        progressBar.setVisibility(View.VISIBLE);
        String email = Email.getText().toString().trim();
        String pass = password.getText().toString().trim();
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
            password.setError("Please Enter Password");
            password.requestFocus();
            return;
        }
        mauth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (firebaseUser.isEmailVerified()) {
                        Intent intent = new Intent(LoginEmployee.this, EmployeeHome.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginEmployee.this, "Log in Succesfull", Toast.LENGTH_SHORT).show();
                        intent.putExtra("email", email);
                        startActivity(intent);
                        finish();
                    } else {
                        firebaseUser.sendEmailVerification();
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(LoginEmployee.this, "check your email to verify", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginEmployee.this, "log in Unsuccessful", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    public void onBackPressed(){
        startActivity(new Intent(LoginEmployee.this,MainActivity.class));
    }
}