package edu.sc.cse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyEvents extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DatabaseReference myRef2 = database.getReference();


        //ArrayList<String> test2 = new ArrayList<>();


        myRef2.child("StudyGroup").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // data available in snapshot.value()
                //Iterable<DataSnapshot> children =  snapshot.getChildren()
                // ;
                // StudyGroup temp = new StudyGroup();

                final ListView list = (ListView) findViewById(edu.sc.cse.R.id.listView);
                String[] lv_arr = {};

                ArrayList<String> grooupD = new ArrayList<>();
                for (DataSnapshot areaSnapshot: snapshot.getChildren()) {
                    final StudyGroup temp = areaSnapshot.getValue(StudyGroup.class);
                    //studygroups.add(temp);
                    if(temp.getMembers().contains(LoginScreen.email)) {
                        grooupD.add("Course: " + temp.getCourse() + "\nDate: " + temp.getDate() + "\nHost: " + temp.getHost());
                    }
                    }

                // Puts items from database into a list
                lv_arr = new String[grooupD.size()];
                lv_arr = grooupD.toArray(lv_arr);
                list.setAdapter(new ArrayAdapter<String>(MyEvents.this,
                        android.R.layout.simple_list_item_1, lv_arr));


                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> myAdapter, View myView, int pos, long mylng) {
                        String selectedFromList =(list.getItemAtPosition(pos).toString());

           //             joinGroup(selectedFromList,LoginScreen.email);
                        System.out.println(selectedFromList);
                        // this is your selected item
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
//            }
//        });
    }

}
