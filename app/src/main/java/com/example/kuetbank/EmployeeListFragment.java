package com.example.kuetbank;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmployeeListFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference database;
    AdminAdapter myAdapter;
    ArrayList<Customer> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee_list, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //@Override
    //public void onItemClicked(Customer customer) {
        //Toast.makeText(this, customer.getName()+" Clicked.  Account No is: "+customer.getAccountno(), Toast.LENGTH_SHORT).show();
        //Intent intent=new Intent(CustomerList.this,EmployeeOnCustomer.class);
        //intent.putExtra("accountno",customer.getAccountno());
        //startActivity(intent);
    //}
    //public void onBackPressed(){
        //Intent intent=new Intent(CustomerList.this,EmployeeHome.class);
        //startActivity(intent);
    //}
}