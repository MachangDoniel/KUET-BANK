package com.example.kuetbank;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Customer> list;
    SelectListener listener;


    public MyAdapter(Context context, ArrayList<Customer> list, SelectListener listener) {
        this.context = context;
        this.list = list;
        this.listener=listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {

        Customer user = list.get(position);
        holder.Name.setText(user.getName());
        holder.Balance.setText(user.getBalance()+" ৳");
        holder.AccountNo.setText(user.getAccountno());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Name,Balance,AccountNo;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            AccountNo = itemView.findViewById(R.id.accountno);
            Name = itemView.findViewById(R.id.name);
            Balance = itemView.findViewById(R.id.balance);
            cardView = itemView.findViewById(R.id.main_container);
        }
    }

}
