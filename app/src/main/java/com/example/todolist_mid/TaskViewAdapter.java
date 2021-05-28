package com.example.todolist_mid;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class TaskViewAdapter extends RecyclerView.Adapter <TaskViewAdapter.TaskViewHolder> {
    ArrayList<String> itemsView;
    UpdatedList activity;
    public TaskViewAdapter(Context context, ArrayList<String>list){

        activity=(UpdatedList) context;
        itemsView=list;
    }
    public interface UpdatedList{
        void onClickRemove(int index);
    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.itemsview,parent,false);
        return new TaskViewAdapter.TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.itemView.setTag(itemsView.get(position));
        holder.tvitemView.setText(itemsView.get(position));
    }


    @Override
    public int getItemCount() {
        return itemsView.size();
    }
    public class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView tvitemView;
        CheckBox checkitem;
        ImageView ivRemove;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvitemView=itemView.findViewById(R.id.tvItemshow);
            checkitem=itemView.findViewById(R.id.checkitem);
            ivRemove=itemView.findViewById(R.id.icRemove);

            checkitem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(checkitem.isChecked()) {
                        tvitemView.setPaintFlags(tvitemView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    }
                    else if(!checkitem.isChecked()) {
                        tvitemView.setPaintFlags(tvitemView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    }
                }
            });
            ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onClickRemove(itemsView.indexOf(itemView.getTag()));
                }
            });
        }
    }
}
