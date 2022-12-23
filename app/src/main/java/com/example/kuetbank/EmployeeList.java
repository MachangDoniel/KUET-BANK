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

public class EmployeeList extends AppCompatActivity implements SelectListener2 {

    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter2 myAdapter;
    //ArrayList<Customer>list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        recyclerView=findViewById(R.id.employeelist);
        database= FirebaseDatabase.getInstance().getReference("Employee");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        list=new ArrayList<>();
//        myAdapter= new MyAdapter(this,list,this);
//        recyclerView.setAdapter(myAdapter);

        EditText editText = findViewById(R.id.text);

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

        ArrayList<Employee>list = new ArrayList<>();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Employee user=dataSnapshot.getValue(Employee.class);
                    if((key.isEmpty() || (user != null && user.name.toLowerCase().trim().contains(key))) && user.isverified()==true) {
                        list.add(user);
                    }
                }
                myAdapter = new MyAdapter2(EmployeeList.this,list,EmployeeList.this);
                recyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClicked(Employee employee) {
        Toast.makeText(this, employee.getName()+" Clicked.  Account Id is: "+employee.getAccountid(), Toast.LENGTH_SHORT).show();
    }
    public void onBackPressed(){
        Intent intent=new Intent(EmployeeList.this,ManagerHome.class);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}