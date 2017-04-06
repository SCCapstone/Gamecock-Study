package edu.sc.cse;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class EventScreen extends AppCompatActivity {
    private EditText course;
    private EditText event_date;
    private EditText event_time;
    private EditText event_location;
    private EditText event_host;
    private EditText event_members;
    private EditText event_description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        course = (EditText) findViewById(R.id.course);
        event_date = (EditText) findViewById(R.id.event_date);
        event_time = (EditText) findViewById(R.id.event_time);
        event_location = (EditText) findViewById(R.id.event_location);
        event_host = (EditText) findViewById(R.id.event_host);
        event_members = (EditText) findViewById(R.id.event_members);
        event_description = (EditText) findViewById(R.id.event_description);
        course.setText(MyEvents.currentG.getCourse());
        event_date.setText(MyEvents.currentG.getDate());
        event_time.setText(MyEvents.currentG.getTime());
        event_location.setText(MyEvents.currentG.getLocation());
        event_host.setText(MyEvents.currentG.getHost());
        event_members.setText(MyEvents.currentG.getMembers().toString());
        event_description.setText(MyEvents.currentG.getDescription());

    }

}
