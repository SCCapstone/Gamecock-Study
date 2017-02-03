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

public class LoginScreen extends AppCompatActivity implements View.OnClickListener
{
    private static final String TAG = "emailPass";
    private EditText emailField;
    private EditText passwordField;
    public static String email;

    // declare auth and listener
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

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

    private void createAccount(String email, String password)
    {
        Log.d(TAG, "createAccount:" + email);
        if (check() == false)
        {
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful() == false)
                        {
                            Toast.makeText(LoginScreen.this, "Unable to Create Account",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(LoginScreen.this, "Account Creation Successful!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                } );

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
