package com.example.helloworld;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//import static com.example.helloworld.R.id.email;
import static com.example.helloworld.R.id.create_room;
//import static com.example.helloworld.R.id.joinButton;

public class CreateGroup extends AppCompatActivity implements View.OnClickListener
{

    private static final String TAG = "MainActivity";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private EditText locationField;
    private EditText timeField;
    private EditText dateField;
    private EditText descriptionField;
    private EditText nameField;


    Button aButton;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        aButton = (Button)findViewById(create_room);
        aButton.setOnClickListener(this);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        } );
    }





    @Override
    public void onClick(View v)
    {

        //if clicked button2, we go to addData(), where data is written to the DB
        if(v.getId() == create_room)
        {
            addGroup();
        }
//        if(v.getId() == R.id.joinButton)
//        {
//            SearchScreen.joinGroup();
//        }


        //if button3 then we go ahead and read from the DB
//        if(v.getId() == button3)
//        {
//
//            //it is going to read from the 'message' part of the DB
//            DatabaseReference myRef = database.getReference("message");
//            // Read from the database
//            myRef.addValueEventListener(new ValueEventListener()
//            {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot)
//                {
//                    // This method is called once with the initial value and again
//                    // whenever data at this location is updated.
//                    String value = dataSnapshot.getValue(String.class);
//                    Log.d(TAG, "Value is: " + value);
//                    Toast.makeText(MainActivity.this, value, Toast.LENGTH_LONG).show();
//
//
//                }
//                @Override
//                public void onCancelled(DatabaseError error)
//                {
//                    // Failed to read value
//                    Log.w(TAG, "Failed to read value.", error.toException());
//
//                }
//
//            } );
//
//
//
//        }
    }

    public void addGroup()
    {
        nameField = (EditText) findViewById(R.id.Name);
        String groupName = nameField.getText().toString();

        locationField = (EditText) findViewById(R.id.Location);
        String groupLocation = locationField.getText().toString();

        timeField = (EditText) findViewById(R.id.Time);
        String groupTime = timeField.getText().toString();

        dateField = (EditText) findViewById(R.id.Date);
        String groupDate = dateField.getText().toString();

        descriptionField = (EditText) findViewById(R.id.Description);
        String groupDescription = descriptionField.getText().toString();


        DatabaseReference myRef = database.getReference(groupName);

//        //DatabaseReference myRef = database.getReference(aGroupName);
//        myRef.addValueEventListener(new ValueEventListener()
//        {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot)
//            {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
//                //Toast.makeText(CreateGroup.this, value, Toast.LENGTH_LONG).show();
//
//
//
//            }
//            @Override
//            public void onCancelled(DatabaseError error)
//            {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//
//            }
//
//        } );
        myRef.child("User").setValue(LoginScreen.email);

        myRef.child("Location").setValue(groupLocation);
        myRef.child("Date").setValue(groupDate);
        myRef.child("Time").setValue(groupTime);
        myRef.child("Description").setValue(groupDescription);





    }











}
