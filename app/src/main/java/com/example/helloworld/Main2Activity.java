package com.example.helloworld;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
//modified on 11/27/17


public class Main2Activity extends AppCompatActivity {
    private EditText editLocation;
    private EditText editTime;
    private TextView textViewPersons;
    private Button create_room;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
//        buttonSave = (Button) findViewById(R.id.buttonSave);
//        editTextName = (EditText) findViewById(R.id.editTextName);
//        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
//
//        textViewPersons = (TextView) findViewById(R.id.textViewPersons);

    }


    //Find Study Event Button
    //Create Study Event Button
    //My Study Events button


    //Connect button with search screen
    public void StudyEventButton(View view) {

        tToast("study event button clicked");
        Intent intent = new Intent(Main2Activity.this, SearchScreen.class);
        startActivity(intent);

    }

    private void tToast(String s) {

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, s, duration);
        toast.show();

    }

    //Switch Activity Method to create group/event page
    public void createEvent(View view) {

        Intent intent = new Intent(Main2Activity.this, CreateGroup.class);
        startActivity(intent);

    }


    private void fToast(String d) {

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, d, duration);
        toast.show();

    }


    public void myEvents(View view) {

        jToast(" my study event button clicked");
        Intent intent = new Intent(Main2Activity.this, MyEvents.class);
        startActivity(intent);


    }


    private void jToast(String a) {

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, a, duration);
        toast.show();

    }






}
