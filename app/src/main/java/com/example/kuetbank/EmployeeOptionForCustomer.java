package com.example.kuetbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class EmployeeOptionForCustomer extends AppCompatActivity {

    TextView profile,add,transfer,withdraw,history;
    String ACC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_option_for_customer);

        ACC=getIntent().getStringExtra("accountno");
        profile=findViewById(R.id.profile);
        add=findViewById(R.id.add);
        transfer=findViewById(R.id.transfer);
        withdraw=findViewById(R.id.withdraw);
        history=findViewById(R.id.history);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EmployeeOptionForCustomer.this,EmployeeOnCustomer.class);
                intent.putExtra("accountno",ACC);
                startActivity(intent);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EmployeeOptionForCustomer.this,EmployeeAddMoney.class);
                intent.putExtra("accountno",ACC);
                startActivity(intent);
            }
        });
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EmployeeOptionForCustomer.this,EmployeeTransfer.class);
                intent.putExtra("accountno",ACC);
                startActivity(intent);
            }
        });
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EmployeeOptionForCustomer.this,EmployeeWithdraw.class);
                intent.putExtra("accountno",ACC);
                startActivity(intent);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EmployeeOptionForCustomer.this,TransactionList.class);
                intent.putExtra("accountno",ACC);
                startActivity(intent);
            }
        });
    }
    public void onBackPressed(){
        Intent intent=new Intent(EmployeeOptionForCustomer.this,CustomerList.class);
        startActivity(intent);
    }
}