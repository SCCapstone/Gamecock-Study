package edu.sc.cse;
import edu.sc.cse.R;
import com.google.firebase.iid.FirebaseInstanceId;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;



public class CreateGroup extends AppCompatActivity implements View.OnClickListener
{

    private static final String TAG = "MainActivity";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private EditText locationField;
    private TextView timeField;
    private DatePicker dateField;
    private EditText descriptionField;
    private EditText nameField;
    Button aButton;

    //TimePicker
    Calendar c = Calendar.getInstance();
    TextView display;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TimePicker
        Button changeTimeBtn = (Button) findViewById(R.id.chooseTimeButton);
        display = (TextView) findViewById(R.id.displayTimeText);
        changeTimeBtn.setOnClickListener(this);

        aButton = (Button) findViewById(edu.sc.cse.R.id.create_room);
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
            public void onClick(View v) {


                switch (v.getId()) {

                    case R.id.chooseTimeButton:
                        new TimePickerDialog(CreateGroup.this, t, c
                                .get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
                                true).show();
                        break;

                    case R.id.create_room:
                        String tkn = FirebaseInstanceId.getInstance().getToken();

                        //what is this for?
                        //Toast.makeText(CreateGroup.this, "Current token ["+tkn+"]",
                        //Toast.LENGTH_LONG).show();

                        //Let usuer know they created a group!d
                        Toast.makeText(CreateGroup.this,
                                "Group Created!", Toast.LENGTH_LONG).show();
                        Log.d("App", "Token [" + tkn + "]");

                        //if clicked button2, we go to addData(), where data is written to the DB
                        if (v.getId() == edu.sc.cse.R.id.create_room) {
                            addGroup();
                        }

                        Intent intent = new Intent(CreateGroup.this, Main2Activity.class);
                        startActivity(intent);
                        break;

                    default:
                        break;

                }
            }

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String AM_PM ;
            if(hourOfDay < 12) {
                AM_PM = "AM";
            } else {
                AM_PM = "PM";
            }
            display.setText(hourOfDay + ":" + minute + " " + AM_PM);
        }
    };


    public void addGroup()
    {
        nameField = (EditText) findViewById(R.id.Name);
        String groupName = nameField.getText().toString();

        locationField = (EditText) findViewById(R.id.Location);
        String groupLocation = locationField.getText().toString();


        //This is the new time picker (works)
        timeField = (TextView) findViewById(R.id.displayTimeText);
        String groupTime = timeField.getText().toString();

        //This is for the new Date Picker
        dateField = (DatePicker)findViewById(R.id.datePicker);
        Integer dobYear = dateField.getYear();
        Integer dobMonth = dateField.getMonth() + 1;
        Integer dobDate = dateField.getDayOfMonth();
        StringBuilder String=new StringBuilder();
        String.append(dobMonth.toString()).append("/").append(dobDate.toString()).append("/").append(dobYear.toString());
        String groupDate=String.toString();

        //This is the older date picker (works)
//        dateField = (EditText) findViewById(R.id.Date);
//        String groupDate = dateField.getText().toString();

        descriptionField = (EditText) findViewById(R.id.Description);
        String groupDescription = descriptionField.getText().toString();


        DatabaseReference myRef = database.getReference(groupName);

        myRef.child("Host User").setValue(LoginScreen.email);
        //myRef.child("").setValue("");
        myRef.child("Location").setValue(groupLocation);
        myRef.child("Date").setValue(groupDate);
        myRef.child("Time").setValue(groupTime);
        myRef.child("Description").setValue(groupDescription);
        myRef.child("Members").setValue(1);

    }

    public void studyRoomButtonClick(View view) {
        Uri uri = Uri.parse("http://libcal.library.sc.edu/"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);


    }
}

