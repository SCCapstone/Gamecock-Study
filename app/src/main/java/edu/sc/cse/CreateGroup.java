package edu.sc.cse;
import edu.sc.cse.R;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
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
                Toast.makeText(CreateGroup.this,
                        "Group Created!", Toast.LENGTH_LONG).show();
                Log.d("App", "Token [" + tkn + "]");

                //  If clicked button2, we go to addData(), where data is written to the DB
                if (v.getId() == edu.sc.cse.R.id.create_room) {
                    addGroup();
                }

                Intent intent = new Intent(CreateGroup.this, Main2Activity.class);
                PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);

                //  Notifications
                builder=new NotificationCompat.Builder(context);
                builder.setSmallIcon(R.mipmap.ic_launcher)
                        .setAutoCancel(true)
                        .setCustomContentView(remoteViews)
                        .setContentIntent(pendingIntent);
                notificationManager.notify(notification_id,builder.build());

                //  Sound to notification
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();

                startActivity(intent);
                break;

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


    public void addGroup()
    {
        nameField = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        String groupName = nameField.getText().toString();

        locationField = (EditText) findViewById(R.id.Location);
        String groupLocation = locationField.getText().toString();


        //  This is the new time picker (works)
        timeField = (TextView) findViewById(R.id.displayTimeText);
        String groupTime = timeField.getText().toString();

        //  This is for the new Date Picker
        dateField = (DatePicker)findViewById(R.id.datePicker);
        Integer dobYear = dateField.getYear();
        Integer dobMonth = dateField.getMonth() + 1;
        Integer dobDate = dateField.getDayOfMonth();
        StringBuilder String=new StringBuilder();
        String.append(dobMonth.toString()).append("/").append(dobDate.toString()).append("/").append(dobYear.toString());
        String groupDate=String.toString();


        descriptionField = (EditText) findViewById(R.id.Description);
        String groupDescription = descriptionField.getText().toString();
        List<String> members = new ArrayList<>();

        DatabaseReference myRef = database.getReference("StudyGroup");
        members.add(LoginScreen.email);
        myRef.child(groupName).setValue(new StudyGroup(groupDate,groupDescription,groupLocation,members ,groupTime,groupName,LoginScreen.email));


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

