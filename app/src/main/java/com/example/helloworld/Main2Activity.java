package com.example.helloworld;
import android.widget.Toast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


//Susana Cuellar edited this on 11/16/17
//this is the navigation screen that will take you to events, find events, etc.
//almost positive it is right
//waiting on james to check login screen


public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


    }


    //Find Study Event Button
    //Create Study Event Button
    //My Study Events button

    public void StudyEventButton(View v) {

        tToast("study event button clicked");

    }

    private void tToast(String s) {

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, s, duration);
        toast.show();

    }

    public void createEvent(View v) {

        tToast("create event button clicked");

    }


    private void fToast(String d) {

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, d, duration);
        toast.show();

    }


    public void myEvents(View v) {

        jToast(" my study event button clicked");

    }


    private void jToast(String a) {

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, a, duration);
        toast.show();

    }





}
