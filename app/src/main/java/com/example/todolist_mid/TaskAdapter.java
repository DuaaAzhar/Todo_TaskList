package com.example.todolist_mid;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.taskViewHolder> {
    ArrayList<Tasks> tasks;
    TaskSelected activity;
    public TaskAdapter(Context context, ArrayList<Tasks> list){
        activity=(TaskSelected)context;
        tasks=list;
    }
    public interface TaskSelected{
        void onTaskClick(int index);
        void onClickBin(int index);
    }
    @NonNull
    @Override
    public taskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.taskview,parent,false);
        return new taskViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull taskViewHolder holder, int position) {
        holder.itemView.setTag(tasks.get(position));
        holder.tvName.setText(tasks.get(position).getName());
        ArrayList<String> items = new ArrayList<String>(tasks.get(position).getItems());
        if(tasks.get(position).getItems().size()>=3)
            items = new ArrayList<String>(tasks.get(position).getItems().subList(0, 3));
        String str = String.join(" ,", items);
        holder.tvItemshow.setText(str);
        holder.ivPriority.setImageResource(tasks.get(position).getPriority());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class taskViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvItemshow;
        ImageView ivPriority, ivBin;
        public taskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            tvItemshow=itemView.findViewById(R.id.tvItemshow);
            ivPriority=itemView.findViewById(R.id.ivPriority);
            ivBin=itemView.findViewById(R.id.ivBin);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onTaskClick(tasks.indexOf(itemView.getTag()));
                }
            });
            ivBin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onClickBin(tasks.indexOf(itemView.getTag()));
                }
            });
        }
    }
}
