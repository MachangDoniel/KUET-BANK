package com.example.kuetbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerWithdraw extends AppCompatActivity {

    DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Customer"),ref2=FirebaseDatabase.getInstance().getReference("Employee"),central=FirebaseDatabase.getInstance().getReference("Manager");
    EditText amount,agent;
    Button withdraw;
    String ACC,uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_withdraw);

        ACC=getIntent().getStringExtra("accountno");
        amount=findViewById(R.id.amount);
        agent=findViewById(R.id.agentaccountno);
        withdraw=findViewById(R.id.withdraw);
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Agent=agent.getText().toString().trim();
                String Amount=agent.getText().toString().trim();
                if(Agent.isEmpty()){
                    agent.setError("Enter Agents Account No");
                    agent.requestFocus();
                    return;
                }
                if(Amount.isEmpty()){
                    amount.setError("Enter Agents Account No");
                    amount.requestFocus();
                    return;
                }
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(ACC)){
                            Double balance=Double.valueOf(snapshot.child(ACC).child("balance").getValue().toString());
                            ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.hasChild(Agent)){
                                        DatabaseReference reff=FirebaseDatabase.getInstance().getReference("Extra2");
                                        reff.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                uid=snapshot.child(Agent).child("uid").getValue().toString();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    }
                                    else{
                                        agent.setError("Enter Valid Account No");
                                        agent.requestFocus();
                                        return;
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        else{
                            Toast.makeText(CustomerWithdraw.this, "Error", Toast.LENGTH_SHORT).show();
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