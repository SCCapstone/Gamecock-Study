package edu.sc.cse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchScreen extends AppCompatActivity implements View.OnClickListener
{

    private static final String TAG = "MainActivity";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Button aButton2;
    private EditText searchField;
    public int memberCount;
    public List<String> members = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        aButton2 = (Button)findViewById(edu.sc.cse.R.id.joinButton);
        aButton2.setOnClickListener(this);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//
//            }
//        });




    }

    @Override
    public void onClick(View v)
    {

        //if clicked button2, we go to addData(), where data is written to the DB
        if(v.getId() == edu.sc.cse.R.id.joinButton)
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

        DatabaseReference myRef = database.getReference("StudyGroup/CSCE 211");


        String temp = myRef.child("members").toString();

        myRef.child("members").addListenerForSingleValueEvent(new ValueEventListener() {
            String user = LoginScreen.email;
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // data available in snapshot.value()
                Iterable<DataSnapshot> children =  snapshot.getChildren();
                for(DataSnapshot child: children){
                    String member = snapshot.getValue(String.class);
                    members.add(member);
                }
                members.add(user);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //memberCount = memberCount + 1;



        myRef.child("User" + memberCount).setValue(LoginScreen.email);
        myRef.child("members").setValue(members); //known bug!! Single user can repeatedly join and increase group count!
        //myRef.child(LoginScreen.email).setValue(LoginScreen.email);
        DatabaseReference ref = database.getReference("StudyGroup");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StudyGroup studyGroup = dataSnapshot.getValue(StudyGroup.class);
                System.out.println(studyGroup);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

}
