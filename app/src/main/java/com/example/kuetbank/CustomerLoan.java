package com.example.kuetbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerLoan extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Customer"),ref2=FirebaseDatabase.getInstance().getReference("LOAN");
    String Loan,Installment,ACC;
    Double emi,Amount;
    TextView EMI,show;
    Spinner spinner,spinner2,spinner3;
    CheckBox checkBox;
    Button send;
    EditText LOAN,pin;
    String name,accountno;
    int i=0;
    //CharSequence[] loan={"1000","5000","10000","20000","50000","100000","200000","500000","1000000","2000000"};
    //String[] installment={"1","2","3","6","12","24","48"};
    boolean spin=false,spin2=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_loan);

        checkBox=findViewById(R.id.checkbox);
        EMI=findViewById(R.id.emi);
        LOAN=findViewById(R.id.loan);
        pin=findViewById(R.id.pin);
        show=findViewById(R.id.show);
        spinner=findViewById(R.id.spinner2);
        send=findViewById(R.id.send);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.loan, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        ACC=getIntent().getStringExtra("accountno");
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LOAN.getText().toString().trim().isEmpty()){
                    LOAN.setError("Enter Loan Amount");
                    LOAN.requestFocus();
                    return;
                }
                Amount=Double.valueOf(LOAN.getText().toString().trim());
                EMI.setText(String.valueOf(emi+"৳"));
                emi=(Amount+Amount*11/100/12)/Double.valueOf(Installment);
                if(i%2==1){
                    show.setText("EMI: ");
                    EMI.setVisibility(View.VISIBLE);
                    i++;
                }
                else{
                    show.setText("Show EMI");
                    EMI.setVisibility(View.INVISIBLE);
                    i++;
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()) {
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(ACC)) {
                                String Pin = pin.getText().toString();
                                if(LOAN.getText().toString().trim().isEmpty()){
                                    LOAN.setError("Enter Loan Amount");
                                    LOAN.requestFocus();
                                    return;
                                }
                                else if (Pin.isEmpty()) {
                                    pin.setError("Enter PIN");
                                    pin.requestFocus();
                                    return;
                                }
                                /*
                                else if (Pin.equals(snapshot.child(ACC).child("pass").getValue().toString())) {
                                    String Loan=LOAN.getText().toString().trim();
                                    ref2.child(ACC).child("Loan Amount").setValue(Loan);
                                    ref2.child(ACC).child("Installment").setValue(Installment);
                                    String approve="false";
                                    ref2.child(ACC).child("Approve").setValue(approve);
                                    Toast.makeText(CustomerLoan.this, "Loan Request Successfully Send", Toast.LENGTH_SHORT).show();
                                }
                                 */
                                else if (Pin.equals(snapshot.child(ACC).child("pass").getValue().toString())) {
                                    String Loan=LOAN.getText().toString().trim();
                                    String approve="false";
                                    accountno=snapshot.child(ACC).child("accountno").getValue().toString();
                                    name=snapshot.child(ACC).child("name").getValue().toString();
                                    String Emi=String.valueOf(emi);
                                    String Pay="0";
                                    Loan loan=new Loan(name,accountno,approve,Installment,Loan,Emi,Pay);
                                    FirebaseDatabase.getInstance().getReference("LOAN").child(ACC).setValue(loan).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(CustomerLoan.this, "Loan Request Successfully Send", Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                Toast.makeText(CustomerLoan.this, "Failed to send Loan Request"+task.getException(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                                else{
                                    pin.setError("Wrong PIN");
                                    pin.requestFocus();
                                    return;
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    Toast.makeText(CustomerLoan.this, "Tik at checkbox to go forward", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Installment=adapterView.getItemAtPosition(i).toString();
        //Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void onBackPressed(){
        Intent intent=new Intent(CustomerLoan.this,CustomerHome.class);
        intent.putExtra("accountno",ACC);
        startActivity(intent);
    }
}