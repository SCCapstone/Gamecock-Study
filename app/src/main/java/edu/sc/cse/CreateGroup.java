package edu.sc.cse;
import edu.sc.cse.R;
import com.google.firebase.iid.FirebaseInstanceId;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//import static R.id.email;

//import static R.id.joinButton;

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

        aButton = (Button)findViewById(edu.sc.cse.R.id.create_room);
        aButton.setOnClickListener(this);



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        } );
    }





    @Override
    public void onClick(View v)
    {
        String tkn = FirebaseInstanceId.getInstance().getToken();
        Toast.makeText(CreateGroup.this, "Current token ["+tkn+"]",
                Toast.LENGTH_LONG).show();
        Log.d("App", "Token ["+tkn+"]");

        //if clicked button2, we go to addData(), where data is written to the DB
        if(v.getId() == edu.sc.cse.R.id.create_room)
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


        Intent intent = new Intent(CreateGroup.this, Main2Activity.class);
        startActivity(intent);


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
        myRef.child("Host User").setValue(LoginScreen.email);
        //myRef.child("").setValue("");

        myRef.child("Location").setValue(groupLocation);
        myRef.child("Date").setValue(groupDate);
        myRef.child("Time").setValue(groupTime);
        myRef.child("Description").setValue(groupDescription);
        myRef.child("Members").setValue(1);





    }











}