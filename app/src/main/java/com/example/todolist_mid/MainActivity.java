package com.example.todolist_mid;


import androidx.annotation.Nullable;

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

    final static int ENTER_RECORD = 1, VIEW_RECORD = 2;
    FloatingActionButton btnAdd;
    ArrayList<Tasks> tasks;
    View layEmpty;
    RecyclerView RvTask;
    RecyclerView.LayoutManager layoutManager;
    TaskAdapter taskAdapter;
    ImageView ivBin, ivEditTask;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        if (tasks.size() >= 0) {
            layEmpty.setVisibility(View.GONE);
        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.todolist_mid.addList.class);
                startActivityForResult(intent, ENTER_RECORD);
            }
        });

    }

    private void init() {
        btnAdd = findViewById(R.id.btnAdd);
        tasks = new ArrayList<>();
        layEmpty = findViewById(R.id.layEmpty);
        RvTask = findViewById(R.id.RvTask);
        RvTask.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        RvTask.setLayoutManager(layoutManager);
        taskAdapter = new TaskAdapter(this, TaskArray.tasks);
        RvTask.setAdapter(taskAdapter);
        ivBin = findViewById(R.id.ivBin);
        ivEditTask = findViewById(R.id.ivEditTask);
    }

    @Override
    public void onTaskClick(int index) {

        Toast.makeText(this, TaskArray.tasks.get(index).getName(), Toast.LENGTH_SHORT).show();
        this.index = index;
        Intent intent1 = new Intent(MainActivity.this, com.example.todolist_mid.ViewTask.class);
        intent1.putExtra("index", index);
        startActivityForResult(intent1, VIEW_RECORD);

    }

    @Override
    public void onClickBin(int index) {
        Toast.makeText(this, TaskArray.tasks.get(index).getName(), Toast.LENGTH_SHORT).show();
        TaskArray.tasks.remove(TaskArray.tasks.get(index));
        taskAdapter.notifyDataSetChanged();
        if (TaskArray.tasks.size() <= 0) {
            layEmpty.setVisibility(View.VISIBLE);
        }
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ENTER_RECORD)
        {
            if(resultCode==RESULT_OK)
            {
                TaskArray.tasks.add(new Tasks(
                        data.getStringExtra("Name"),
                        data.getIntExtra("priority",-1),
                        data.getStringExtra("date"),
                        data.getStringExtra("time"),
                        data.getBooleanExtra("repeat", false),
                        data.getStringArrayListExtra("items")));
                taskAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Record Successfully Entered", Toast.LENGTH_SHORT).show();


            }
            else if(resultCode==RESULT_CANCELED)
            {
                Toast.makeText(this, "Record Not Entered", Toast.LENGTH_SHORT).show();
            }
        }
       else if(requestCode==VIEW_RECORD)
        {
            if(resultCode==RESULT_OK)
            {
                TaskArray.tasks.get(index).setName(data.getStringExtra("Name"));
                TaskArray.tasks.get(index).setPriority(data.getIntExtra("priority",-1));
                TaskArray.tasks.get(index).setDate(data.getStringExtra("date"));
                TaskArray.tasks.get(index).setTime(data.getStringExtra("time"));
                TaskArray.tasks.get(index).setRepeat(data.getBooleanExtra("repeat", false));
                TaskArray.tasks.get(index).setItems(data.getStringArrayListExtra("items"));
                taskAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Record Successfully Updated", Toast.LENGTH_SHORT).show();


            }
            else if(resultCode==RESULT_CANCELED)
            {
                Toast.makeText(this, "Record Not Updated", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
