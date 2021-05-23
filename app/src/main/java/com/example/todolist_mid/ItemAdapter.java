package com.example.todolist_mid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.itemViewHolder>{
    private ArrayList<String> items;
    ItemDel activity;
    public ItemAdapter(Context context,ArrayList<String> items){
        activity= (ItemDel) context;
        this.items=items;
    }
    public interface ItemDel{
        void DelItemClick(int index);
    }
    @NonNull

    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.listview,parent,false);
        return new itemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        holder.itemView.setTag(items.get(position));
        holder.tvItem.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder{
        TextView tvItem;
        ImageView ivDel;
        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem= itemView.findViewById(R.id.tvItem);
            ivDel=itemView.findViewById(R.id.ivDel);
            ivDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i=items.indexOf(itemView.getTag());
                    activity.DelItemClick(i);
                }
            });
        }
    }

}
