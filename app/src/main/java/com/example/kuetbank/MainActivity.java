package com.example.kuetbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    DatabaseReference ref,dataBaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kuet-bank-default-rtdb.firebaseio.com");

    EditText Phone,Pin;
    Button login,register,forgot;
    String ACC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ref= FirebaseDatabase.getInstance().getReference().child("Customer");
        Phone =  findViewById(R.id.phone);
        Pin =  findViewById(R.id.pin);
        login = findViewById(R.id.login);
        //String ACC = "1907121", PIN = "121";
        //1907121 & 121
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = Phone.getText().toString();
                String PIN = Pin.getText().toString();
                if (mobile.isEmpty()) {
                    //Toast.makeText(MainActivity.this, "Enter Account No and Pin", Toast.LENGTH_SHORT).show();
                    Phone.setError("Enter Phone No");
                    Phone.requestFocus();
                    return;
                }
                /*
                if (mobile.length()!=10) {
                    //Toast.makeText(MainActivity.this, "Enter Account No and Pin", Toast.LENGTH_SHORT).show();
                    Phone.setError("Enter Valid Phone Number");
                    Phone.requestFocus();
                    return;
                }
                 */
                else if (PIN.isEmpty()) {
                    //Toast.makeText(MainActivity.this, "Enter Account No and Pin", Toast.LENGTH_SHORT).show();
                    Pin.setError("Enter Pin No");
                    Pin.requestFocus();
                    return;
                }else {
                    dataBaseReference.child("Extra").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(mobile)){
                                ACC=snapshot.child(mobile).child("Account No").getValue().toString();
                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.hasChild(ACC)) {
                                            //String getPin = snapshot.child(ACC).child("Pin").getValue(String.class);
                                            String getPin = snapshot.child(ACC).child("pass").getValue().toString();
                                            if (getPin.equals(PIN)) {
                                                Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(MainActivity.this, CustomerHome.class);
                                                intent.putExtra("accountno", ACC);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(MainActivity.this, "Account No doesn't exists", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                            else{
                                Toast.makeText(MainActivity.this,"Account No doesn't exists",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CustomerRegister.class);
                startActivity(intent);
            }
        });
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.manager:
                //startActivity(new Intent(MainActivity.this, MainActivity4.class));
                return true;
            case R.id.employee:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // User is signed in
                    startActivity(new Intent(MainActivity.this, EmployeeHome.class));
                    return true;
                } else {
                    // No user is signed in
                    startActivity(new Intent(MainActivity.this, LoginEmployee.class));
                    return true;
                }

            case R.id.customer:
                user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //Intent intent = new Intent(MainActivity.this, CustomerHome.class);
                    //String ACC="1893097217";
                    //intent.putExtra("mobileno", ACC);
                    //startActivity(intent);
                    return true;
                } else {
                    // No user is signed in
                    //startActivity(new Intent(MainActivity.this, MainActivity.class));
                    //return true;
                }

                return true;
            default:
                return false;
        }
    }
    public void onBackPressed(){
        startActivity(new Intent(MainActivity.this,MainActivity.class));
    }
}