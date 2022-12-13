package com.example.kuetbank;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
    ArrayList<Customer>list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        recyclerView=findViewById(R.id.customerlist);
        database= FirebaseDatabase.getInstance().getReference("Customer");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<>();
        myAdapter= new MyAdapter(this,list,this);
        recyclerView.setAdapter(myAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Customer user=dataSnapshot.getValue(Customer.class);
                    list.add(user);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onItemClicked(Customer customer) {
        Toast.makeText(this, customer.getName()+" Clicked.  Account No is: "+customer.getAccountno(), Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(CustomerList.this,EmployeeOnCustomer.class);
        intent.putExtra("accountno",customer.getAccountno());
        startActivity(intent);
    }
    public void onBackPressed(){
        startActivity(new Intent(CustomerList.this,EmployeeHome.class));
    }
}