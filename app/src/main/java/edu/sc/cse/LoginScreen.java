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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener
{
    private static final String TAG = "emailPass";
    private static final String TAG2 = "FacebookLogin";
    private EditText emailField;
    private EditText passwordField;
    public static String email;

    public int userCount;

    // declare auth and listener
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private CallbackManager mCallbackManager;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login_screen);

        // Buttons
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.create_account_button).setOnClickListener(this);

        emailField = (EditText) findViewById(R.id.email_field);
        passwordField = (EditText) findViewById(R.id.password_field);

        auth = FirebaseAuth.getInstance();



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG2, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG2, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                updateUI(user);
                // [END_EXCLUDE]
            }
        };

        mCallbackManager = CallbackManager.Factory.create();
        final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG2, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());

                if (AccessToken.getCurrentAccessToken() != null) {
                    RequestData();
                }


                //startActivity(new Intent(LoginScreen.this, Main2Activity.class));

            }

            @Override
            public void onCancel() {
                Log.d(TAG2, "facebook:onCancel");
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG2, "facebook:onError", error);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        });
        // [END initialize_fblogin]



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
    private void RequestData() {

        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object,GraphResponse response) {

                final JSONObject json = response.getJSONObject();



                try {
                    if(json != null){
                        String test = "<b>Name :</b> "+json.getString("name")+"<br><br><b>Email :</b> "+json.getString("email")+"<br><br><b>Profile link :</b> "+json.getString("link");
                    /*details_txt.setText(Html.fromHtml(text));
                    profile.setProfileId(json.getString("id"));*/

                        Log.e(TAG, json.getString("name"));
                        Log.e(TAG, json.getString("email"));
                        setEmailField(json.getString("email"));
                        Log.e(TAG, json.getString("id"));
                        //web.loadData(text, "text/html", "UTF-8");

                    }







                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void setEmailField(String em)
    {
        email = em;
        System.out.println(email);
    }

//    static void signOut() {
//        auth.signOut();
//        LoginManager.getInstance().logOut();
//
//        updateUI(null);
//    }
    @Override
    public void onStart()
    {
        super.onStart();

        auth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop()
    {
        super.onStop();

        if(mAuthListener != null)
        {
            auth.removeAuthStateListener(mAuthListener);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
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
        signIn(email, password);
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

    private void updateUI(FirebaseUser user) {
        //hideProgressDialog();
        if (user != null) {
            //  mStatusTextView.setText(getString(R.string.facebook_status_fmt, user.getDisplayName()));
            //mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));


            //findViewById(R.id.button_facebook_login).setVisibility(View.GONE);
            //findViewById(R.id.button_facebook_signout).setVisibility(View.VISIBLE);
        } else {

//                mStatusTextView.setText(R.string.signed_out);
            // mDetailTextView.setText(null);

            //              findViewById(R.id.button_facebook_login).setVisibility(View.VISIBLE);
            //            findViewById(R.id.button_facebook_signout).setVisibility(View.GONE);
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG2, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
        //showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG2, "signInWithCredential:onComplete:" + task.isSuccessful());
                        startActivity(new Intent(LoginScreen.this, Main2Activity.class));
                        System.out.println(email);

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG2, "signInWithCredential", task.getException());
                            //                  Toast.makeText(FacebookLoginActivity.this, "Authentication failed.",
                            //                        Toast.LENGTH_SHORT).show();
                        }


                        // [START_EXCLUDE]
                        //          hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.create_account_button)
        {
            createAccount(emailField.getText().toString(), passwordField.getText().toString());

        }
        else if(v.getId() == R.id.sign_in_button)
        {
            mAuthListener = new FirebaseAuth.AuthStateListener()
            {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
                {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null)
                    {
                        // User is signed in
                        Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                        //Toast.makeText(LoginScreen.this, user.getUid(),
                        //    Toast.LENGTH_SHORT).show();

                        // This line will keep the user from having to log in every time they run the app
                        //results in a 'null' user error in creating groups
                        //startActivity(new Intent(LoginScreen.this, Main2Activity.class));
                    }
                    else
                    {
                        // User is signed out
                        Log.d(TAG, "onAuthStateChanged:signed_out");
                    }
                }
            };
            signIn(emailField.getText().toString(), passwordField.getText().toString());
        }
    }




}











