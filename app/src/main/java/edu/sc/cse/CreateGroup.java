package edu.sc.cse;
import edu.sc.cse.R;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.media.RingtoneManager;
import android.media.Ringtone;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
//import android.support.v4.app.NotificationCompat.Action.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


public class CreateGroup extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener
{

    //  AutoComplete
    AutoCompleteTextView autocomplete;


    private static final String TAG = "MainActivity";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private EditText locationField;
    private TextView timeField;
    private DatePicker dateField;
    private EditText descriptionField;
    private AutoCompleteTextView nameField;
    Button aButton;

    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private int notification_id;
    private RemoteViews remoteViews;
    private Context context;

    //  TimePicker
    Calendar c = Calendar.getInstance();
    TextView display;

    //  Class Names List
    private String[] states;
    public static   String groupDate,groupDescription,groupLocation,members ,groupTime,groupName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        context =this;
        notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        remoteViews=new RemoteViews(getPackageName(),R.layout.custom_notification);
        remoteViews.setImageViewResource(R.id.notif_icon,R.mipmap.ic_launcher);
        remoteViews.setTextViewText(R.id.notif_title,"You created an event!");
        notification_id=(int) System.currentTimeMillis();
        Intent button_intent=new Intent("button_clicked");
        button_intent.putExtra("id",notification_id);
        PendingIntent p_button_intent=PendingIntent.getBroadcast(context,123,button_intent,0);

        //  TimePicker
        Button changeTimeBtn = (Button) findViewById(R.id.chooseTimeButton);
        display = (TextView) findViewById(R.id.displayTimeText);
        changeTimeBtn.setOnClickListener(this);

        aButton = (Button) findViewById(edu.sc.cse.R.id.create_room);
        aButton.setOnClickListener(this);

        // AutoComplete class list box
        states = getResources().getStringArray(R.array.classes_list);
        autocomplete = (AutoCompleteTextView)
                findViewById(R.id.autoCompleteTextView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, states);
        autocomplete.setThreshold(2);
        autocomplete.setAdapter(adapter);


    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.chooseTimeButton:
                new TimePickerDialog(CreateGroup.this, t, c
                        .get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
                        false).show();
                break;

            case R.id.create_room:
                String tkn = FirebaseInstanceId.getInstance().getToken();

                //  Let user know they created a group
//                Toast.makeText(CreateGroup.this,
//                        "Group Created!", Toast.LENGTH_LONG).show();
//                Log.d("App", "Token [" + tkn + "]");

                //  If clicked button2, we go to addData(), where data is written to the DB
                boolean success = false;
                if (v.getId() == edu.sc.cse.R.id.create_room) {
                    success = addGroup();
                }

                Intent intent = new Intent(CreateGroup.this, Main2Activity.class);
                PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);



                //  Notifications
                if(success == true) {
                    builder = new NotificationCompat.Builder(context);
                    builder.setSmallIcon(R.mipmap.ic_launcher)
                            .setAutoCancel(true)
                            .setCustomContentView(remoteViews)
                            .setContentIntent(pendingIntent);
                    notificationManager.notify(notification_id, builder.build());

                    //vibrate on notification


                    //  Sound to notification
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    r.play();


                    startActivity(intent);
                    break;
                }

            default:
                break;

        }
    }

    //  vibrate on noification
    public void vibrate(long[] pattern, int repeat){

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //  Start without a delay
        //  Vibrate for 100 milliseconds
        //  Sleep for 1000 milliseconds
        long[] pat = {0, 100, 1000};
        v.vibrate(pat, 0);
    }

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            int hour = hourOfDay % 12;
            display.setText(String.format("%02d:%02d %s", hour == 0 ? 12 : hour,
                    minute, hourOfDay < 12 ? "AM" : "PM"));
        }
    };
    //generate id
    public String generateID()
    {
        Random r = new Random();
        int d1 = r.nextInt(9);
        int d2 = r.nextInt(9);
        int d3 = r.nextInt(9);
        int d4 = r.nextInt(9);
        String id = d1+""+d2+""+d3+""+d4;
//        DatabaseReference myRef = database.getReference();
//        List<String> eventid = new ArrayList<>();
//        eventid.add(id);
//        myRef.child("EventID").setValue(eventid);
        return (id);
    }
    //create method to check if id is in use
    public void checkid()
    {
        Random r = new Random();
        int d1 = r.nextInt(9);
        int d2 = r.nextInt(9);
        int d3 = r.nextInt(9);
        int d4 = r.nextInt(9);
        final String eventid = d1+""+d2+""+d3+""+d4;
        List<String> members = new ArrayList<>();
        //create function which generates random 4 digits
        final DatabaseReference myRef = database.getReference("EventID");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                String eventid = generateID();
                List<String> ids = new ArrayList<>();
                GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                final ArrayList<String> temp  = snapshot.getValue(t);
                ids = temp;
                boolean duplicate = true;
                while(duplicate)
                {
                    if(temp.contains(eventid))
                    {
                        eventid = generateID();
                    }
                    else {
                        ids.add(eventid);
                        myRef.setValue(ids);
                        addgrouptofb(eventid);
                        System.out.println(temp);
                        duplicate = false;
                    }
                }

                //members.add(LoginScreen.email);
                //myRef.child(eventid).setValue(new StudyGroup(eventid,groupDate,groupDescription,groupLocation,members ,groupTime,groupName,LoginScreen.email));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

        //generateID();
        //Log.d("Email", LoginScreen.email);
        //members.add(LoginScreen.email);
        //myRef.child("test").setValue(new StudyGroup(eventid,groupDate,groupDescription,groupLocation,members ,groupTime,groupName,LoginScreen.email));
    }


    public boolean addGroup( )
    {
        nameField = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
         groupName = nameField.getText().toString();
        if(TextUtils.isEmpty(groupName)) {
            Toast.makeText(CreateGroup.this,
                    "Cannot create group without a name.", Toast.LENGTH_LONG).show();
            return false;
        }

        locationField = (EditText) findViewById(R.id.Location);
         groupLocation = locationField.getText().toString();

        if(TextUtils.isEmpty(groupLocation)) {
            Toast.makeText(CreateGroup.this,
                    "Cannot create group without a location.", Toast.LENGTH_LONG).show();
            return false;
        }

        //  This is the new time picker (works)
        timeField = (TextView) findViewById(R.id.displayTimeText);
         groupTime = timeField.getText().toString();

        if(TextUtils.isEmpty(groupTime)) {
            Toast.makeText(CreateGroup.this,
                    "Cannot create group without a time.", Toast.LENGTH_LONG).show();
            return false;
        }


        //  This is for the new Date Picker
        dateField = (DatePicker)findViewById(R.id.datePicker);
        Integer dobYear = dateField.getYear();
        Integer dobMonth = dateField.getMonth() + 1;
        Integer dobDate = dateField.getDayOfMonth();
        StringBuilder String=new StringBuilder();
        String.append(dobMonth.toString()).append("/").append(dobDate.toString()).append("/").append(dobYear.toString());
         groupDate=String.toString();


        descriptionField = (EditText) findViewById(R.id.Description);
         groupDescription = descriptionField.getText().toString();
        if(TextUtils.isEmpty(groupDescription)) {
            Toast.makeText(CreateGroup.this,
                    "Cannot create group without a description.", Toast.LENGTH_LONG).show();
            return false;
        }

        //boolean success;
        //success = false;
        //String id = generateID();
        checkid();



        Toast.makeText(CreateGroup.this,
                "Group Created!", Toast.LENGTH_LONG).show();
//        Log.d("App", "Token [" + tkn + "]");
        return true;

    }
    public void addgrouptofb(String id)
    {
        List<String> members = new ArrayList<>();
        members.add(LoginScreen.email);
        DatabaseReference myref =  database.getReference("StudyGroup");
        myref.child(id).setValue(new StudyGroup(id,groupDate,groupDescription,groupLocation,members ,groupTime,groupName,LoginScreen.email));
    }


    public void studyRoomButtonClick(View view) {
        Uri uri = Uri.parse("http://libcal.library.sc.edu/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);


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
            startActivity(new Intent(CreateGroup.this, SearchScreen.class));
            // Handle the camera action
        } else if (id == R.id.nav_createEvent) {
            startActivity(new Intent(CreateGroup.this, CreateGroup.class));

        } else if (id == R.id.nav_myEvents) {
            startActivity(new Intent(CreateGroup.this, MyEvents.class));

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            startActivity(new Intent(CreateGroup.this, LoginScreen.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}

