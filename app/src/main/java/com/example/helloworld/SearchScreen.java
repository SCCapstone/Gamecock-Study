package com.example.helloworld;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.helloworld.R.id.create_room;
import static com.example.helloworld.R.id.joinButton;

public class SearchScreen extends AppCompatActivity implements View.OnClickListener
{

    private static final String TAG = "MainActivity";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Button aButton2;
    private EditText searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        aButton2 = (Button)findViewById(joinButton);
        aButton2.setOnClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });




    }

    @Override
    public void onClick(View v)
    {

        //if clicked button2, we go to addData(), where data is written to the DB
        if(v.getId() == joinButton)
        {
            joinGroup();
        }


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




    public void joinGroup()
    {
        searchField = (EditText) findViewById(R.id.subject);
        String searchedGroup = searchField.getText().toString();

        DatabaseReference myRef = database.getReference(searchedGroup);

        myRef.child("User").setValue(LoginScreen.email);
    }

}
