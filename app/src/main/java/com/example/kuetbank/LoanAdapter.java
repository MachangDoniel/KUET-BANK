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

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.MyViewHolder> {

    Context context;
    ArrayList<Loan> list;
    SelectListener3 listener;


    public LoanAdapter(Context context, ArrayList<Loan> list, SelectListener3 listener) {
        this.context = context;
        this.list = list;
        this.listener=listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item3,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {

        Loan user = list.get(position);
        holder.AccountNo.setText(user.getAccountno());
        holder.Emi.setText(user.getEmi()+" ৳");
        holder.Installment.setText(user.getInstallment());
        holder.Name.setText(user.getName());
        holder.Loan.setText(user.getLoan()+" ৳");
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

    public static class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnCreateContextMenuListener{

        TextView Name,Loan,AccountNo,Emi,Installment;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Emi=itemView.findViewById(R.id.emi);
            Installment=itemView.findViewById(R.id.installment);
            AccountNo = itemView.findViewById(R.id.accountno);
            Name = itemView.findViewById(R.id.name);
            Loan = itemView.findViewById(R.id.loan);
            cardView = itemView.findViewById(R.id.main_container3);
            cardView.setOnCreateContextMenuListener(this);
        }

        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select Any One");
            contextMenu.add(this.getAdapterPosition(),121,0,"Accept");
            contextMenu.add(this.getAdapterPosition(),122,1,"Decline");
        }
    }
    public void removeItem(int position){
        list.remove(position);
        notifyDataSetChanged();
    }
}