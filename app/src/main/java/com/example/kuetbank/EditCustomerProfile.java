package com.example.kuetbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditCustomerProfile extends AppCompatActivity {

    DatabaseReference ref;
    private EditText name,dob,gender,mobileno,address;
    TextView accountno,Accounttype;
    Button save;
    String ACC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer_profile);

        ref= FirebaseDatabase.getInstance().getReference("Customer");
        name=findViewById(R.id.name);
        dob=findViewById(R.id.dob);
        gender=findViewById(R.id.gender);
        accountno=findViewById(R.id.accountno);
        mobileno=findViewById(R.id.mobileno);
        address=findViewById(R.id.address);
        Accounttype=findViewById(R.id.accounttype);
        save=findViewById(R.id.save);
        ACC=getIntent().getStringExtra("accountno");
        mobileno.setText(ACC);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(ACC)){
                    //String getName=snapshot.child(ACC).child("name").getValue(String.class);
                    String getName=snapshot.child(ACC).child("name").getValue().toString();
                    name.setText(getName);
                    //String getName=snapshot.child(ACC).child("name").getValue(String.class);
                    String getacctype=snapshot.child(ACC).child("accounttype").getValue().toString();
                    Accounttype.setText(getacctype);
                    //String getdob=snapshot.child(ACC).child("dob").getValue(String.class);
                    String getdob=snapshot.child(ACC).child("dob").getValue().toString();
                    dob.setText(getdob);
                    //String getgen=snapshot.child(ACC).child("gender").getValue(String.class);
                    String getgen=snapshot.child(ACC).child("gender").getValue().toString();
                    gender.setText(getgen);
                    //String getadd=snapshot.child(ACC).child("address").getValue(String.class);
                    String getadd=snapshot.child(ACC).child("address").getValue().toString();
                    address.setText(getadd);
                }
                else{
                    Toast.makeText(EditCustomerProfile.this,"Account No doesn't exists",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name=name.getText().toString();
                ref.child(ACC).child("name").setValue(Name);
                String Dob=dob.getText().toString();
                ref.child(ACC).child("dob").setValue(Dob);
                String Gender=gender.getText().toString();
                ref.child(ACC).child("gender").setValue(Gender);
                String MobileNo=mobileno.getText().toString();
                ref.child(ACC).child("mobile").setValue(MobileNo);
                String Address=address.getText().toString();
                ref.child(ACC).child("address").setValue(Address);

                Intent intent=new Intent(EditCustomerProfile.this,CustomerProfile.class);
                intent.putExtra("accountno",ACC);
                startActivity(intent);
                finish();
            }
        });
    }
    public void onBackPressed(){
        Intent intent=new Intent(EditCustomerProfile.this,CustomerProfile.class);
        intent.putExtra("accountno",ACC);
        startActivity(intent);
    }
}