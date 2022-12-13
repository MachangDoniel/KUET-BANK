package com.example.kuetbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerRegister extends AppCompatActivity {

    DatabaseReference ref,dataBaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://kuet-bank-default-rtdb.firebaseio.com");
    private EditText Pin,Name,DateOfBirth,Nationality,MobileNo,SecurityQ,Answer,Email,Address;
    private Button Next;
    private TextView AccNo,Micr;
    private RadioGroup Gender,AccType;
    private RadioButton rbutton;
    private Double Balance=0D;
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        AccNo=findViewById(R.id.accountno);
        Pin=findViewById(R.id.pin);
        Micr=findViewById(R.id.micr);
        Email=findViewById(R.id.email);
        Name=findViewById(R.id.name);
        DateOfBirth=findViewById(R.id.dateofbirth);
        Nationality=findViewById(R.id.nationality);
        MobileNo=findViewById(R.id.mobile);
        Address=findViewById(R.id.address);
        SecurityQ=findViewById(R.id.security);
        Answer=findViewById(R.id.answer);
        Gender=findViewById(R.id.gender);
        AccType=findViewById(R.id.accounttype);
        ref= FirebaseDatabase.getInstance().getReference().child("Customer");


        Double random=10000000*Math.random();
        Double random2=10000*Math.random();
        Integer ran=random.intValue();
        Integer ran2=random2.intValue();
        String AccountNo=String.valueOf(ran);
        String MicrNo=String.valueOf(ran2);
        AccNo.setText(AccountNo);
        Micr.setText(MicrNo);


        Next=(Button)findViewById(R.id.next);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String AccountNo=AccNo.getText().toString();
                String pin=Pin.getText().toString();
                String micr=Micr.getText().toString();
                String name=Name.getText().toString();
                String email=Email.getText().toString();
                String dateofbirth=DateOfBirth.getText().toString();
                String nationality=Nationality.getText().toString();
                String mobileno=MobileNo.getText().toString();
                String address=Address.getText().toString();

                String securityq=SecurityQ.getText().toString();
                String answer=Answer.getText().toString();

                int genderid=Gender.getCheckedRadioButtonId();
                rbutton=findViewById(genderid);
                String gender=(String)rbutton.getText();
                int accid2=AccType.getCheckedRadioButtonId();
                rbutton=findViewById(accid2);
                String acctype=(String)rbutton.getText();

                String balance=String.valueOf(Balance);
                //String balance=bal+" ৳";


                if(AccountNo.isEmpty() || MicrNo.isEmpty() || pin.isEmpty() || name.isEmpty() || email.isEmpty() || dateofbirth.isEmpty() || nationality.isEmpty() || mobileno.isEmpty() || address.isEmpty() || acctype.isEmpty() || gender.isEmpty()){
                    Toast.makeText(CustomerRegister.this,"Enter data",Toast.LENGTH_SHORT).show();
                }
                else{
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(AccountNo)) {
                                Toast.makeText(CustomerRegister.this, "Account No is already registered", Toast.LENGTH_SHORT).show();
                            } else {
                                customer = new Customer(AccountNo, acctype, name, mobileno, email, pin, gender, dateofbirth, address, balance);
                            /*}
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

                             */

                                ref.child(AccountNo).setValue(customer);
                                Toast.makeText(CustomerRegister.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
    public void onBackPressed(){
        startActivity(new Intent(CustomerRegister.this,MainActivity.class));
    }
}