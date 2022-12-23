package com.example.kuetbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManagerLogin extends AppCompatActivity {

    Button register, login, Forgotpassword;
    EditText Email, password;
    FirebaseAuth mauth;
    ProgressBar progressBar;
    boolean verified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_login);

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

        /*
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(LoginEmployee.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ManagerLogin.this, ManagerRegister.class);
                //intent.putExtra("s", id);
                startActivity(intent);
            }
        });
         */
    }

    private void userlogin() {
        progressBar.setVisibility(View.VISIBLE);
        String email = Email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        if(email.isEmpty()){
            //Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            Email.setError("Enter Email");
            Email.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
            Email.setError("Please Enter Valid Email");
            Email.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if(pass.isEmpty()){
            //Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            password.setError("Please Enter Password");
            password.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }
        mauth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser emp = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Manager");
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (firebaseUser.isEmailVerified()) {
                        Intent intent = new Intent(ManagerLogin.this, ManagerHome.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ManagerLogin.this, "Log in Succesfull", Toast.LENGTH_SHORT).show();
                        intent.putExtra("email", email);
                        startActivity(intent);
                        finish();
                    } else {
                        firebaseUser.sendEmailVerification();
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ManagerLogin.this, "check your email to verify", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ManagerLogin.this, "log in Unsuccessful", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void onBackPressed(){
        startActivity(new Intent(ManagerLogin.this,MainActivity.class));
    }
}