package com.example.kuetbank;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class CustomerList extends AppCompatActivity implements SelectListener {

    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    //ArrayList<Customer>list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        recyclerView=findViewById(R.id.customerlist);
        database= FirebaseDatabase.getInstance().getReference("Customer");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        list=new ArrayList<>();
//        myAdapter= new MyAdapter(this,list,this);
//        recyclerView.setAdapter(myAdapter);

        EditText editText = findViewById(R.id.text);
//        Button button = findViewById(R.id.search);


        showData("");

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String key = editText.getText().toString();
//                showData(key);
//            }
//        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = String.valueOf(editable).toLowerCase();
                showData(text);
            }
        });

    }

    private void showData(String key){

        ArrayList<Customer>list = new ArrayList<>();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Customer user=dataSnapshot.getValue(Customer.class);
                    if(key.isEmpty() || (user != null && user.name.toLowerCase().contains(key))) {
                        list.add(user);
                    }
                }
                myAdapter = new MyAdapter(CustomerList.this,list,CustomerList.this);
                recyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClicked(Customer customer) {
        Toast.makeText(this, customer.getName()+" Clicked.  Account No is: "+customer.getAccountno(), Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(CustomerList.this,EmployeeOptionForCustomer.class);
        intent.putExtra("accountno",customer.getAccountno());
        startActivity(intent);
    }
    public void onBackPressed(){
        Intent intent=new Intent(CustomerList.this,EmployeeHome.class);
        startActivity(intent);
    }
}