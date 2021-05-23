package com.example.todolist_mid;

import android.app.Application;

import java.util.ArrayList;

public class TaskArray extends Application {
    public static ArrayList<Tasks> tasks;

    @Override
    public void onCreate() {
        super.onCreate();
        tasks=new ArrayList<>();
        ArrayList<String>items1,items2,items3,items4;
        items1=new ArrayList<String>();
        items1.add("Bread");
        items1.add("Pan");
        items1.add("Eggs");
        tasks.add(new Tasks("Shopping", R.drawable.red,"5-May-2021", "24-7", true,items1 ));
        items2=new ArrayList<String>();
        items2.add("Chemistry");
        items2.add("Bio");
        items2.add("Physics");
        items2.add("English");
        tasks.add(new Tasks("Homework", R.drawable.red,"5-May-2021", "24-7", true, items2));
        items3=new ArrayList<String>();
        items3.add("Fruit Salad");
        items3.add("Karahi");
        items3.add("Kabab");
        items3.add("Pulao");
        tasks.add(new Tasks("Cooking", R.drawable.yellow,"5-May-2021", "24-7", true, items3));
        items4=new ArrayList<String>();
        items4.add("Walk");
        items4.add("Breakfast");
        items4.add("Dish Wash");
        items4.add("Lunch");
        items4.add("Cloth Washing");
        tasks.add(new Tasks("Daily Task", R.drawable.green,"5-May-2021", "24-7", true, items4));
    }
}
