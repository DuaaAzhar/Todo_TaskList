package com.example.todolist_mid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TaskAdapter.TaskSelected {
    final static int ENTER_RECORD=1;
    FloatingActionButton btnAdd;
    ArrayList<Tasks> tasks;
    View layEmpty;
    RecyclerView RvTask;
    RecyclerView.LayoutManager layoutManager;
    TaskAdapter taskAdapter;
    ImageView ivBin, ivEditTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        if(tasks.size()>=0)
        {
            layEmpty.setVisibility(View.GONE);
        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,com.example.todolist_mid.addList.class);
                startActivityForResult(intent,ENTER_RECORD);
            }
        });
        ivEditTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void init() {
        btnAdd=findViewById(R.id.btnAdd);
        tasks=new ArrayList<>();
        layEmpty=findViewById(R.id.layEmpty);
        RvTask=findViewById(R.id.RvTask);
        RvTask.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        RvTask.setLayoutManager(layoutManager);
        taskAdapter=new TaskAdapter(this, TaskArray.tasks);
        RvTask.setAdapter(taskAdapter);
        ivBin=findViewById(R.id.ivBin);
        ivEditTask=findViewById(R.id.ivEditTask);
    }

    @Override
    public void onTaskClick(int index) {
        Toast.makeText(this, TaskArray.tasks.get(index).getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickBin(int index) {
        Toast.makeText(this, TaskArray.tasks.get(index).getName(), Toast.LENGTH_SHORT).show();
        TaskArray.tasks.remove(TaskArray.tasks.get(index));
        taskAdapter.notifyDataSetChanged();
    }
}