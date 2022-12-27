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

public class CustomerTransfer extends AppCompatActivity {

    DatabaseReference ref,dataBaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://kuet-bank-default-rtdb.firebaseio.com");
    private TextView t1,t2,name;
    private EditText accountno,amount,pin;
    Button balance,profile,transfer,payment,loan,search,send;
    String ACC,AccountNo;
    long max=2147483647;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_transfer);

        ref=FirebaseDatabase.getInstance().getReference("Customer");

        int Balance=0,count=0;
        ACC=getIntent().getStringExtra("accountno");

        accountno=findViewById(R.id.accno);
        amount=findViewById(R.id.amount);
        pin=findViewById(R.id.pin);
        name=findViewById(R.id.name);
        search=findViewById(R.id.search);
        send=findViewById(R.id.send);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountNo=accountno.getText().toString();
                if(AccountNo.isEmpty()){
                    //Toast.makeText(CustomerTransfer.this,"Enter Receiver's Account No",Toast.LENGTH_SHORT).show();
                    accountno.setError("Enter Receiver's Account No");
                    accountno.requestFocus();
                    return;
                }
                else if(AccountNo.equals(ACC)){
                    //Toast.makeText(CustomerTransfer.this,"You can't transfer money to your Own Account",Toast.LENGTH_SHORT).show();
                    accountno.setError("You can't transfer money to your Own Account");
                    accountno.requestFocus();
                    return;
                }
                else{
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(AccountNo)){
                                //String getName=snapshot.child(AccountNo).child("name").getValue(String.class);
                                String getName=snapshot.child(AccountNo).child("name").getValue().toString();
                                name.setText(getName);
                                Toast.makeText(CustomerTransfer.this,"Enter Amount and PIN",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                //Toast.makeText(CustomerTransfer.this,"Account No doesn't exists",Toast.LENGTH_SHORT).show();
                                accountno.setError("Account No doesn't exists");
                                accountno.requestFocus();
                                return;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.hasChild(AccountNo)){
                                        double Amount=Double.valueOf(amount.getText().toString());
                                        String Pin=pin.getText().toString();
                                        if(String.valueOf(Amount).isEmpty()){
                                            amount.setError("Enter Amount");
                                            amount.requestFocus();
                                            return;
                                        }
                                        else if(Pin.isEmpty()){
                                            pin.setError("Enter PIN");
                                            pin.requestFocus();
                                            return;
                                        }
                                        //if(String.valueOf(Amount).isEmpty() || Pin.isEmpty()){
                                        //    Toast.makeText(CustomerTransfer.this,"Enter Amount and PIN",Toast.LENGTH_SHORT).show();
                                        //}
                                        //else if(Pin.equals(snapshot.child(ACC).child("Pin").getValue(String.class))){
                                        else if(Pin.equals(snapshot.child(ACC).child("pass").getValue().toString())){
                                            //Toast.makeText(CustomerTransfer.this,"Right Pin",Toast.LENGTH_SHORT).show();
                                            //String bal=snapshot.child(ACC).child("Balance").getValue(String.class);
                                            String bal=snapshot.child(ACC).child("balance").getValue().toString();
                                            //double getBalance=Double.valueOf(bal);
                                            double getBalance=Double.parseDouble(bal);
                                            if(Amount<=getBalance){
                                                getBalance-=Amount;
                                                String Balance=String.valueOf(getBalance);
                                                ref.child(ACC).child("balance").setValue(Balance);
                                                //double getBalance2=Double.valueOf(snapshot.child(AccountNo).child("balance").getValue(String.class));
                                                double getBalance2=Double.valueOf(snapshot.child(AccountNo).child("balance").getValue().toString());
                                                getBalance2+=Amount;
                                                String Balance2=String.valueOf(getBalance2);
                                                ref.child(AccountNo).child("balance").setValue(Balance2);

                                                enterhistory(String.valueOf(Amount));

                                                Toast.makeText(CustomerTransfer.this,"Transaction Successfull",Toast.LENGTH_SHORT).show();
                                                Intent intent=new Intent(CustomerTransfer.this,CustomerHome.class);
                                                intent.putExtra("accountno",ACC);
                                                startActivity(intent);
                                            }
                                            else{
                                                Toast.makeText(CustomerTransfer.this,"Balance Shortage",Toast.LENGTH_SHORT).show();
                                                    amount.setError("Reduce Ammount");
                                                    amount.requestFocus();
                                                    return;
                                            }
                                        }
                                        else {
                                            Toast.makeText(CustomerTransfer.this,"Wrong PIN",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else{
                                        //Toast.makeText(CustomerTransfer.this,"Account No doesn't exists",Toast.LENGTH_SHORT).show();
                                        accountno.setError("Account No doesn't exists");
                                        accountno.requestFocus();
                                        return;
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    });
                }
            }
        });

    }

    private void enterhistory(String amount) {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Transaction");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long maxid=snapshot.child(ACC).getChildrenCount();
                reference.child(ACC).child(String.valueOf(max-maxid)).setValue("Transferred "+amount+"৳ to "+AccountNo);
                maxid=snapshot.child(AccountNo).getChildrenCount();
                reference.child(AccountNo).child(String.valueOf(max-maxid)).setValue("Transferred "+amount+"৳ from "+ACC);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onBackPressed(){
        Intent intent=new Intent(CustomerTransfer.this,CustomerHome.class);
        intent.putExtra("accountno",ACC);
        startActivity(intent);
    }
}