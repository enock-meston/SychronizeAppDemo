package com.nigoote.synchronizedemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
        private ArrayList<Contact> arrayList = new ArrayList<>();

        public  RecyclerAdapter( ArrayList<Contact> arrayList){
            this.arrayList = arrayList;
        }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
// set the name to textView
        holder.Name.setText(arrayList.get(position).getName());
        holder.Price.setText(arrayList.get(position).getPrice());
        holder.Quantity.setText(arrayList.get(position).getQuantity());
        int sync_status = arrayList.get(position).getSync_status();

        if (sync_status==DbContract.SYNC_STATUS_OK){
            holder.Sync_Status.setImageResource(R.drawable.ic_baseline_ok_24);
        }else {
            holder.Sync_Status.setImageResource(R.drawable.ic_baseline_sync_24);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView Sync_Status;
        TextView Name;
        TextView Price;
        TextView Quantity;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Sync_Status = (ImageView)itemView.findViewById(R.id.imgSync);
            Name = (TextView)itemView.findViewById(R.id.txtname);
            Quantity = (TextView)itemView.findViewById(R.id.txtquantity);
            Price = (TextView)itemView.findViewById(R.id.txtprice);

        }
    }
}
