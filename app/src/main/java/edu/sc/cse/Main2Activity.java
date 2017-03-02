package edu.sc.cse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;


//  Susana Cuellar edited this on 11/16/17
//  this is the navigation screen that will take you to events, find events, etc.
//  almost positive it is right
//  waiting on james to check login screen
//  modified on 11/27/17


public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }

    //  Connect button with search screen
    public void StudyEventButton(View view) {


        Intent intent = new Intent(Main2Activity.this, SearchScreen.class);
        startActivity(intent);

    }

    //  Switch Activity Method to create group/event page
    public void createEvent(View view) {

        Intent intent = new Intent(Main2Activity.this, CreateGroup.class);
        startActivity(intent);

    }

    public void myEvents(View view) {


        Intent intent = new Intent(Main2Activity.this, MyEvents.class);
        startActivity(intent);

    }
}
