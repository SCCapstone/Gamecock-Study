package edu.sc.cse;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventScreen extends AppCompatActivity implements View.OnClickListener {
    private TextView course;
    private TextView event_date;
    private TextView event_time;
    private TextView event_location;
    private TextView event_host;
    private TextView event_members;
    private TextView event_description;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.eventB).setOnClickListener(this);
        course = (TextView) findViewById(R.id.course);
        event_date = (TextView) findViewById(R.id.event_date);
        event_time = (TextView) findViewById(R.id.event_time);
        event_location = (TextView) findViewById(R.id.event_location);
        event_host = (TextView) findViewById(R.id.event_host);
        event_members = (TextView) findViewById(R.id.event_members);
        event_description = (TextView) findViewById(R.id.event_description);
        course.setText("Course: "+MyEvents.currentG.getCourse());
        event_date.setText("Date: "+MyEvents.currentG.getDate());
        event_time.setText("Time: "+MyEvents.currentG.getTime());
        event_location.setText("Location: " +MyEvents.currentG.getLocation());
        event_host.setText("Host: "+MyEvents.currentG.getHost());
        event_members.setText("Members: "+ MyEvents.currentG.getMembers().toString());
        event_description.setText("Description: " + MyEvents.currentG.getDescription());

    }

    public void leavegroup()
    {
        //final String[] group = groupD.split("\\s+");

        //final String course = group[0] + " " + group[1];//change from group[1] and group[2] to group[0] and group[1]
        System.out.println("test"+course);

        //Log.d(TAG, "ATTENTION!!!!!: "+ group[0] + " " + group[1] + " " + group[2] + " " + group[3] + " " + group[4]);

        final DatabaseReference myRef = database.getReference("StudyGroup");

        ArrayList<String> test2 = new ArrayList<>();

        myRef.child( MyEvents.currentG.getCourse() +"/members").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
//TODO error is with temp!!!!!
                GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                final ArrayList<String> temp  = snapshot.getValue(t);



                    new AlertDialog.Builder(EventScreen.this)
                            .setTitle("Confirmation")
                            .setMessage("Are you sure you want to leave study event: " + MyEvents.currentG.getCourse() +"?")
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    temp.remove(LoginScreen.email);
                                    final ArrayList<String> m = temp;
                                    myRef.child(MyEvents.currentG.getCourse()+"/members").setValue(temp);
                                    startActivity(new Intent(EventScreen.this, MyEvents.class));
                                   // Toast.makeText(EventScreen.this,
                                    //        "You have joined StudyGroup: " + course + "\n" + "Hosted by: "+group[4], Toast.LENGTH_LONG).show();//changed from group[6] to group[4]
                                }})
                            .setNegativeButton(android.R.string.no, null).show();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.eventB)
        {
            leavegroup();

        }

    }

}
