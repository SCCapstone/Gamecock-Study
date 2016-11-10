package com.example.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    /*private static final String TAG = "emailPass";

    private TextView mStatusTextView, mDetailTextView;
    private EditText mEmailField, mPasswordField;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(mAuthListener);
    }



    @Override
    public void onClick(View v) {

    }



}
