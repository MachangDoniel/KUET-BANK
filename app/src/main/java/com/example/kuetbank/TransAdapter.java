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

public class TransAdapter extends RecyclerView.Adapter<TransAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> list;
    SelectListener4 listener;


    public TransAdapter(Context context, ArrayList<String> list, SelectListener4 listener) {
        this.context = context;
        this.list = list;
        this.listener=listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item4,parent,false);
        return  new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {


        //Transaction user = list.get(position);
        String user=list.get(position).trim();
        //holder.textView.setText(user.getMessage());
        holder.textView.setText(user);
/*        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(list.get(position));
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnCreateContextMenuListener{

        TextView textView;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textview);
            cardView = itemView.findViewById(R.id.main_container4);
            cardView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

        }
    }
    public void removeItem(int position){
        list.remove(position);
        notifyDataSetChanged();
    }
}
