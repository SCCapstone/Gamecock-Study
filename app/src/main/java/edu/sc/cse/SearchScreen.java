package edu.sc.cse;

import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchScreen extends AppCompatActivity implements View.OnClickListener
{

    private static final String TAG = "MainActivity";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    public ArrayList<String> test = new ArrayList<>();
    List<StudyGroup> studygroups = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DatabaseReference myRef2 = database.getReference();

        myRef2.child("StudyGroup").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                final ListView list = (ListView) findViewById(edu.sc.cse.R.id.listView);
                String[] lv_arr = {};

                ArrayList<String> grooupD = new ArrayList<>();
                for (DataSnapshot areaSnapshot: snapshot.getChildren()) {
                    final StudyGroup temp = areaSnapshot.getValue(StudyGroup.class);
                    studygroups.add(temp);
                    grooupD.add("Course: " +" "+temp.getCourse() + "  " +"\nDate: " +temp.getDate()+ "   " +"\nHost: "+temp.getHost());
                }

                // Puts items from database into a list
                lv_arr = new String[grooupD.size()];
                lv_arr = grooupD.toArray(lv_arr);
                list.setAdapter(new ArrayAdapter<String>(SearchScreen.this,
                        android.R.layout.simple_list_item_1, lv_arr));

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> myAdapter, View myView, int pos, long mylng) {
                        String selectedFromList =(list.getItemAtPosition(pos).toString());
                        joinGroup(selectedFromList,LoginScreen.email);

                        // this is your selected item
                        System.out.println(selectedFromList);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

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

    }


    public void joinGroup(String groupD, String user )
    {
        final String[] group = groupD.split("\\s+");
        final String course = group[1] + " " + group[2];
        System.out.println("test"+course);

        final DatabaseReference myRef = database.getReference("StudyGroup");

         ArrayList<String> test2 = new ArrayList<>();

        myRef.child( course +"/members").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                final ArrayList<String> temp  = snapshot.getValue(t);
                if(temp.contains(LoginScreen.email))
                    {
                        Toast.makeText(SearchScreen.this,
                                "You are already a member.", Toast.LENGTH_SHORT).show();
                    }
                else {
                    new AlertDialog.Builder(SearchScreen.this)
                            .setTitle("Confirmation")
                            .setMessage("Are you sure you want to join " +course +"?")
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    temp.add(LoginScreen.email);
                                    final ArrayList<String> m = temp;
                                    myRef.child(course +"/members").setValue(temp);
                                    Toast.makeText(SearchScreen.this,
                                            "You have joined StudyGroup: " + course + "\n" + "Hosted by: "+group[6], Toast.LENGTH_LONG).show();
                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

    }

}
