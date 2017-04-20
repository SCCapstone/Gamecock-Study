package edu.sc.cse;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import android.os.Vibrator;

public class SearchScreen extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener
{

    private static final String TAG = "MainActivity";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    public ArrayList<String> test = new ArrayList<>();
    List<StudyGroup> studygroups = new ArrayList<>();
    private EditText filterText;
    private ArrayAdapter<String> listAdapter;
    public static String eventid;
    public static StudyGroup currentG;
    public static List<String> eventids = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        filterText = (EditText)findViewById(R.id.editText);
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
                ArrayList<String> tempid = new ArrayList<>();
                ArrayList<String> grooupD = new ArrayList<>();
                final ArrayList<StudyGroup> groupA = new ArrayList<StudyGroup>();
                for (DataSnapshot areaSnapshot: snapshot.getChildren()) {
                    final StudyGroup temp = areaSnapshot.getValue(StudyGroup.class);
                    groupA.add(temp);
                    tempid.add(temp.getEventid());

                    grooupD.add(temp.getCourse() + "\n" + temp.getDate() + "\t\t\t" + temp.getTime() + "\n" + temp.getLocation() + "\nHost: " + temp.getHost());
                }

                setidlist(tempid);
                // Puts items from database into a list
                lv_arr = new String[grooupD.size()];
                lv_arr = grooupD.toArray(lv_arr);
                listAdapter = new ArrayAdapter<String>(SearchScreen.this,
                        android.R.layout.simple_list_item_1, lv_arr);
                list.setAdapter(listAdapter);


                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> myAdapter, View myView, int pos, long mylng) {
                        String selectedFromList =(list.getItemAtPosition(pos).toString());
                        currentG = groupA.get(pos);

                        Toast.makeText(SearchScreen.this,
//                                            "You have joined StudyGroup! " + course + "\n" + "Hosted by: "+group[4], Toast.LENGTH_LONG).show();//changed from group[6] to group[4]
//                                }})Toast.makeText(SearchScreen.this,
                                "currentg is" + currentG.getEventid(), Toast.LENGTH_LONG).show();
                        joinGroup(selectedFromList,LoginScreen.email);

                        // this is your selected item
                        System.out.println(selectedFromList);
                    }
                });

                filterText.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        SearchScreen.this.listAdapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
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

        //final String course = group[0] + " " + group[1];//change from group[1] and group[2] to group[0] and group[1]
        //System.out.println("test"+course);
//TODO
        //Log.d(TAG, "ATTENTION!!!!!: "+ group[0] + " " + group[1] + " " + group[2] + " " + group[3] + " " + group[4]);

        final DatabaseReference myRef = database.getReference("StudyGroup/"+"/members");

         ArrayList<String> test2 = new ArrayList<>();
        Log.d("ZIGG", currentG.getEventid());
        myRef.child(currentG.getEventid() + "/members").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
//TODO error is with temp!!!!!
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
                            .setMessage("Are you sure you want to join " +currentG.getCourse() +"?")
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    temp.add(LoginScreen.email);
                                    final ArrayList<String> m = temp;
                                    myRef.child(currentG.getEventid()+"/members").setValue(temp);
                                    Toast.makeText(SearchScreen.this,
//                                            "You have joined StudyGroup! " + course + "\n" + "Hosted by: "+group[4], Toast.LENGTH_LONG).show();//changed from group[6] to group[4]
//                                }})Toast.makeText(SearchScreen.this,
                                    "Welcome to the group!", Toast.LENGTH_LONG).show();//changed from group[6] to group[4]
                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }
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
    public void setid(int i)
    {
        eventid = eventids.get(i);
    }
    public void setidlist(ArrayList<String> t){ eventids = t;}


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_findEvent) {
            startActivity(new Intent(SearchScreen.this, SearchScreen.class));
            // Handle the camera action
        } else if (id == R.id.nav_createEvent) {
            startActivity(new Intent(SearchScreen.this, CreateGroup.class));

        } else if (id == R.id.nav_myEvents) {
            startActivity(new Intent(SearchScreen.this, MyEvents.class));

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            startActivity(new Intent(SearchScreen.this, LoginScreen.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
