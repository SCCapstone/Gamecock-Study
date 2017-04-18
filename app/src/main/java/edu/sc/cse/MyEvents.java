package edu.sc.cse;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;


public class MyEvents extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static StudyGroup currentG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        final DatabaseReference myRef2 = database.getReference();

        myRef2.child("StudyGroup").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                final ListView list = (ListView) findViewById(edu.sc.cse.R.id.listView);
                String[] lv_arr = {};
                final ArrayList<StudyGroup> groupA = new ArrayList<StudyGroup>();
                ArrayList<String> grooupD = new ArrayList<>();
                for (DataSnapshot areaSnapshot: snapshot.getChildren()) {
                    final StudyGroup temp = areaSnapshot.getValue(StudyGroup.class);

                    if (temp.getMembers().contains(LoginScreen.email)) {
                        groupA.add(temp);
                        grooupD.add(temp.getCourse() + "\n" + temp.getDate() + "\t\t\t" + temp.getTime() + "\nHost: " + temp.getHost());
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
                        System.out.println(selectedFromList);
                        currentG = groupA.get(pos);
                        Intent intent = new Intent(MyEvents.this, EventScreen.class);
                        startActivity(intent);

                        //Toast.makeText(MyEvents.this, currentG.getHost(), Toast.LENGTH_SHORT).show();
                        // This is your selected item
                        Log.d("GROUP",currentG.getHost() + "test");

                    }
                });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_findEvent) {
            startActivity(new Intent(MyEvents.this, SearchScreen.class));
            // Handle the camera action
        } else if (id == R.id.nav_createEvent) {
            startActivity(new Intent(MyEvents.this, CreateGroup.class));

        } else if (id == R.id.nav_myEvents) {
            startActivity(new Intent(MyEvents.this, MyEvents.class));

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MyEvents.this, LoginScreen.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }







}
