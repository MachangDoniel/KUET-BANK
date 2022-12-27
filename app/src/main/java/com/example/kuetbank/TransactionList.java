package com.example.kuetbank;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TransactionList extends AppCompatActivity implements SelectListener4 {

    RecyclerView recyclerView;
    DatabaseReference database;
    TransAdapter myAdapter;
    String accountId="",userId="12345";
    //ArrayList<Transaction>list;
    String ACC;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);

        recyclerView=findViewById(R.id.recyclerview);
        ACC=getIntent().getStringExtra("accountno");
        database= FirebaseDatabase.getInstance().getReference("Transaction");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //ArrayList<Transaction>list = new ArrayList<>();
        ArrayList<String>list = new ArrayList<>();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(ACC)){
                    for(DataSnapshot dataSnapshot: snapshot.child(ACC).getChildren()){
                        //Transaction user=dataSnapshot.getValue(Transaction.class);
                        String user=dataSnapshot.getValue(String.class);
                        list.add(user);

                    }
                    myAdapter = new TransAdapter(TransactionList.this,list,TransactionList.this);
                    recyclerView.setAdapter(myAdapter);
                }
                else{
                    Toast.makeText(TransactionList.this, "NO Transaction", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onBackPressed(){
        Intent intent=new Intent(TransactionList.this,EmployeeOptionForCustomer.class);
        intent.putExtra("accountno",ACC);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onItemClicked(Transaction transaction) {

    }
}