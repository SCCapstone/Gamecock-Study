package edu.sc.cse;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EventScreen extends AppCompatActivity {
    private TextView course;
    private TextView event_date;
    private TextView event_time;
    private TextView event_location;
    private TextView event_host;
    private TextView event_members;
    private TextView event_description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        course = (TextView) findViewById(R.id.course);
        event_date = (TextView) findViewById(R.id.event_date);
        event_time = (TextView) findViewById(R.id.event_time);
        event_location = (TextView) findViewById(R.id.event_location);
        event_host = (TextView) findViewById(R.id.event_host);
        event_members = (TextView) findViewById(R.id.event_members);
        event_description = (TextView) findViewById(R.id.event_description);
        course.setText(MyEvents.currentG.getCourse());
        event_date.setText(MyEvents.currentG.getDate());
        event_time.setText(MyEvents.currentG.getTime());
        event_location.setText(MyEvents.currentG.getLocation());
        event_host.setText(MyEvents.currentG.getHost());
        event_members.setText(MyEvents.currentG.getMembers().toString());
        event_description.setText(MyEvents.currentG.getDescription());

    }

}
