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

public class CustomerClearLoan extends AppCompatActivity {

    TextView Installment,date,amount,Pay;
    EditText pin;
    Button send;
    String ACC;
    Double Balance,Emi;
    int PAY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_clear_loan);

        ACC=getIntent().getStringExtra("accountno");

        Installment=findViewById(R.id.installment);
        Pay=findViewById(R.id.pay);
        date=findViewById(R.id.date);
        amount=findViewById(R.id.amount);
        pin=findViewById(R.id.pin);
        send=findViewById(R.id.send);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("LOAN"),ref2=FirebaseDatabase.getInstance().getReference("Customer");

        ref.child(ACC).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String emi = snapshot.child("emi").getValue().toString();
                String installment = snapshot.child("installment").getValue().toString();
                String pay = snapshot.child("pay").getValue().toString();
                Emi = Double.valueOf(emi);
                PAY = Integer.parseInt(pay);
                PAY++;
                amount.setText(emi);
                Installment.setText(installment);
                if (PAY == 1) {
                    Pay.setText("1st");
                } else if (PAY == 2) {
                    Pay.setText("2nd");
                } else if (PAY == 3) {
                    Pay.setText("3rd");
                } else {
                    Pay.setText(PAY + "th");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref2.child(ACC).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String PIN=snapshot.child("pass").getValue().toString();
                        String Pin=pin.getText().toString().trim();
                        if(Pin.isEmpty()){
                            pin.setError("Enter Pin");
                            pin.requestFocus();
                            return;
                        }
                        else if(PIN.equals(Pin)){
                            Balance=Double.valueOf(snapshot.child("balance").getValue().toString());
                            if(Balance>Emi){
                                Balance-=Emi;
                                ref2.child(ACC).child("balance").setValue(String.valueOf(Balance));
                                ref.child(ACC).child("pay").setValue(String.valueOf(PAY));
                                Toast.makeText(CustomerClearLoan.this, "EMI payment Successfull", Toast.LENGTH_SHORT).show();;

                                if(String.valueOf(PAY).equals(Installment.getText().toString().trim())){
                                    Toast.makeText(CustomerClearLoan.this, "Congratulation!, Your EMI is Cleared", Toast.LENGTH_SHORT).show();
                                    ref.child(ACC).removeValue();
                                }
                                Intent intent=new Intent(CustomerClearLoan.this,CustomerHome.class);
                                intent.putExtra("accountno",ACC);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(CustomerClearLoan.this, "Balance Shortage", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
    public void onBackPressed(){
        Intent intent=new Intent(CustomerClearLoan.this,CustomerHome.class);
        intent.putExtra("accountno",ACC);
        startActivity(intent);
    }
}