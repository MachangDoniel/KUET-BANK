package com.example.kuetbank;


import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> implements View.OnCreateContextMenuListener {

    Context context;
    ArrayList<Employee> list;
    SelectListener2 listener;


    public MyAdapter2(Context context, ArrayList<Employee> list, SelectListener2 listener) {
        this.context = context;
        this.list = list;
        this.listener=listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item2,parent,false);
        return  new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {

        Employee user = list.get(position);
        holder.Name.setText(user.getName());
        holder.Email.setText(user.getEmail());
        holder.AccountNo.setText(user.getAccountid());
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

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnCreateContextMenuListener{

        TextView Name,Email,AccountNo;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            AccountNo = itemView.findViewById(R.id.accountno);
            Name = itemView.findViewById(R.id.name);
            Email = itemView.findViewById(R.id.email);
            cardView = itemView.findViewById(R.id.main_container2);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select Any One");
            contextMenu.add(getAdapterPosition(),121,0,"Add");
            contextMenu.add(getAdapterPosition(),122,1,"Delete");
        }
    }

}
