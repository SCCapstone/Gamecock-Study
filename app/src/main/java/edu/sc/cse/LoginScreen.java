package edu.sc.cse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener
{
    private static final String TAG = "emailPass";
    private EditText emailField;
    private EditText passwordField;
    public static String email;

    public int userCount;

    // declare auth and listener
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;


    FirebaseDatabase database = FirebaseDatabase.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        // Buttons
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.create_account_button).setOnClickListener(this);

        emailField = (EditText) findViewById(R.id.email_field);
        passwordField = (EditText) findViewById(R.id.password_field);

        auth = FirebaseAuth.getInstance();

        authListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                }
                else
                {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };


        //  This is for initializing the a variable for the number of users, stored in the database
        DatabaseReference myRef = database.getReference();
        myRef.child("Number of users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // data available in snapshot.value()
                userCount = snapshot.getValue(int.class);
//                Toast.makeText(LoginScreen.this, Integer.toString(userCount),
//                        Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }

    @Override
    public void onStart()
    {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (authListener != null)
        {
            auth.removeAuthStateListener(authListener);
        }
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (check() == false) {
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful() == false) {
                            Toast.makeText(LoginScreen.this, "Unable to Create Account",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginScreen.this, "Account Creation Successful!",
                                    Toast.LENGTH_SHORT).show();


                        }
                    }

                });


        addUserToDatabase(email);
    }

    //This adds the user to the database and increments the count of users
    //Known to be buggy if creating multiple users in a single instance of the running app
    public void addUserToDatabase(String email) {


        DatabaseReference myRef = database.getReference("Users");

        int newCount = userCount + 1;

        myRef.child("User " + Integer.toString(newCount)).setValue(email);
        myRef = database.getReference();
        myRef.child("Number of users").setValue(newCount);

        createUsername(email);
    }

    public static String createUsername(String anEmail) {

        return anEmail.split("@")[0];
    }

    private boolean check()
    {
        boolean check = true;
        email = emailField.getText().toString();
        if (TextUtils.isEmpty(email))
        {
            emailField.setError("Enter an email.");
            check = false;
        }
        else
        {
            emailField.setError(null);
        }

        String password = passwordField.getText().toString();
        if (TextUtils.isEmpty(password))
        {
            passwordField.setError("Create a password.");
            check = false;
        }
        else
        {
            passwordField.setError(null);
        }
        return check;
    }

    private void signIn(String email, String password)
    {
        Log.d(TAG, "signIn:" + email);
        if (check() == false)
        {
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful() == false)
                        {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginScreen.this, "Unable to Login.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            startActivity(new Intent(LoginScreen.this, Main2Activity.class));
                        }
                    }
                } );
    }

    private void signOut()
    {
        auth.signOut();
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.create_account_button)
        {
            createAccount(emailField.getText().toString(), passwordField.getText().toString());

        }
        else
        {
            signIn(emailField.getText().toString(), passwordField.getText().toString());
        }
    }




}
