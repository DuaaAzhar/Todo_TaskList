package com.example.todolist_mid;

import java.util.ArrayList;

public class Tasks {
    String name,Date, Time;
    int Priority;
    Boolean Repeat;

    public Tasks(String name, int priority, String date, String time, Boolean repeat, ArrayList<String> items) {
        this.name = name;
        Priority = priority;
        Date = date;
        Time = time;
        Repeat = repeat;
        this.items = items;
    }

    ArrayList<String> items;

    public String getName() {
        return name;
    }

    public int getPriority() {
        return Priority;
    }

    public String getDate() {
        return Date;
    }

    public String getTime() {
        return Time;
    }

    public Boolean getRepeat() {
        return Repeat;
    }

    public ArrayList<String> getItems() {
        return items;
    }
}
