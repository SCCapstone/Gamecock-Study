package edu.sc.cse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.NotificationManager;
import android.content.Intent;
import android.app.PendingIntent;
import android.support.v4.app.NotificationCompat;
import android.view.View;


public class PushActivity extends AppCompatActivity {


    NotificationCompat.Builder notification;
    private static final int uniqueID = 33;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        notification=new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

    }
    public void NotificationClicked(View view){
        //build notification
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setTicker("This is the ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Here is the title");
        notification.setContentText("I am the body text of the notification");

        Intent intent=new Intent(this,PushActivity.class);
        //gives access to intents in our app
        PendingIntent pendingIntent= PendingIntent.getActivity(this,0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        //Builds notification and issues it (issuing=sending out to device)
        NotificationManager nm=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID,notification.build());
    }
}
