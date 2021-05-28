package com.example.todolist_mid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class ViewTask extends AppCompatActivity implements TaskViewAdapter.UpdatedList {
    final Context context = this;
    ImageView ivSelectDate, ivTime, ivAddItem, ivRepeat, ivBack;
    TextView tvDate, tvTime, tvNameView;
    private Calendar calendar;
    private int year, month, day, hour, min, index;
    RecyclerView RvViewTask;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> list=new ArrayList<>();
    TaskViewAdapter adapter;
    Button btnSave,btnCancel;
    RadioButton high,medium,low;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        init();
        index=getIntent().getIntExtra("index",-1);
        list=TaskArray.tasks.get(index).getItems();
        tvNameView.setText(TaskArray.tasks.get(index).getName());
        tvDate.setText(TaskArray.tasks.get(index).getDate());
        tvTime.setText(TaskArray.tasks.get(index).getTime());
        int p=TaskArray.tasks.get(index).getPriority();
        if(p==R.drawable.red)
        {
            high.setChecked(true);
        }
        else if(p==R.drawable.green)
        {
            medium.setChecked(true);
        }
        else if(p==R.drawable.yellow)
        {
            low.setChecked(true);
        }
        Boolean repeat=TaskArray.tasks.get(index).getRepeat();
        if(repeat)
        {
            ivRepeat.setImageResource(R.drawable.repeat_);
        }
        else
        {
            ivRepeat.setImageResource(R.drawable.repeat);
        }
        adapter=  new TaskViewAdapter(this,list);
        RvViewTask.setAdapter(adapter);

        ivSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });
        ivTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                min = calendar.get(Calendar.MINUTE);
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ViewTask.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                tvTime.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, min, false);
                timePickerDialog.show();
            }
        });
        ivRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((Integer) ivRepeat.getTag() == R.drawable.repeat)
                {
                    ivRepeat.setImageResource(R.drawable.repeat_);
                    ivRepeat.setTag(R.drawable.repeat_);
                }
                else {
                    ivRepeat.setImageResource(R.drawable.repeat);
                    ivRepeat.setTag(R.drawable.repeat);
                }
            }
        });
        ivAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.additem, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView.findViewById(R.id.etDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("SAVE",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        String s=userInput.getText().toString();
                                        if(!s.isEmpty())
                                            list.add(s);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name=tvNameView.getText().toString().trim();
                int Priority=TaskArray.tasks.get(index).getPriority();
                if(high.isChecked())
                {
                    Priority=R.drawable.red;
                }
                else if(medium.isChecked())
                {
                    Priority=R.drawable.green;
                }
                else if(low.isChecked())
                {
                    Priority=R.drawable.yellow;
                }
                String date=tvDate.getText().toString();
                String time=tvTime.getText().toString();
                Boolean repeat=false;
                if(ivRepeat.getDrawable().equals(R.drawable.repeat_))
                {
                    repeat=true;
                }

                if(checkValidation())
                {
                    Intent intent=new Intent();
                    intent.putExtra("Name",Name );
                    intent.putExtra("priority",Priority );
                    intent.putExtra("date",date);
                    intent.putExtra("time",time);
                    intent.putExtra("repeat",repeat);
                    intent.putExtra("items", list);
                    setResult(RESULT_OK,intent);
                    Toast.makeText(context, "Data Send", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ViewTask.this,com.example.todolist_mid.MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean checkValidation() {
        Boolean valid=true;

        if(!(high.isChecked() || medium.isChecked() || low.isChecked()))
        {
            Toast.makeText(context, "Enter Missing Entity", Toast.LENGTH_SHORT).show();
            valid=false;
        }
        if(tvDate.getText().equals("Date Not Selected"))
        {
            Toast.makeText(context, "Enter Missing Entity", Toast.LENGTH_SHORT).show();
            valid=false;
        }
        if(tvTime.getText().equals("Time Not Selected"))
        {
            Toast.makeText(context, "Enter Missing Entity", Toast.LENGTH_SHORT).show();
            valid=false;
        }
        return valid;


    }
    private void init() {
        ivSelectDate=findViewById(R.id.ivSelectDate);
        tvDate=findViewById(R.id.tvDate);
        ivTime=findViewById(R.id.ivTime);
        tvTime=findViewById(R.id.tvTime);
        ivRepeat=findViewById(R.id.ivRepeat);
        ivRepeat.setTag(R.drawable.repeat);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        ivAddItem=findViewById(R.id.ivAddItem);
        RvViewTask=findViewById(R.id.RvViewTask);
        RvViewTask.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        RvViewTask.setLayoutManager(layoutManager);
        btnSave=findViewById(R.id.btnSave);
        high=findViewById(R.id.radioHigh);
        medium=findViewById(R.id.radioMedium);
        low=findViewById(R.id.radioLow);
        btnCancel=findViewById(R.id.btnCancel);
        tvNameView=findViewById(R.id.tvNameView);
        ivBack=findViewById(R.id.ivBack);
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        tvDate.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }


    @Override
    public void onClickRemove(int index) {
        list.remove(index);
        adapter.notifyDataSetChanged();
    }
}