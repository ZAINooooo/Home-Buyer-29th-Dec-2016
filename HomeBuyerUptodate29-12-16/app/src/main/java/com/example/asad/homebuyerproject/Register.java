package com.example.asad.homebuyerproject;

/**
 * Created by Asad on 10/9/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import static java.security.AccessController.getContext;

import com.hbb20.CountryCodePicker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


//Calling when register button is clicked xml:activity_register
public class Register extends AppCompatActivity {


    private Button mRegister, mlogin;
    //  CountryCodePicker ccp;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    // private String termsandcondition;
    private DatabaseReference mDatabase;
    private TextView mName, mEmail, mPassword, mPhone, email_id, Name;
    private Boolean mgetValue;
    private TextView mTerms;
    private static final String TAG = "MainActivity";
    private String auth_failed = "Error";

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private CountryCodePicker ccp;
    private String ccp1;
    boolean msetvalue = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Calling Methods
        TypeCasting();
        RegisterButtonClick();
        LoginButtonClick();
        CountryCode();

        //   String ccp1= ccp.getSelectedCountryCodeWithPlus();
        //  Toast.makeText(Register.this,ccp1,Toast.LENGTH_SHORT).show();


        //Dtabase reference and storage
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        //Datebase reference
        mAuth = FirebaseAuth.getInstance();


        //For current users state
        //For user state weather user is logged in or not
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }

        };


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void CountryCode() {
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        try {
            ccp1 = ccp.getDefaultCountryCodeWithPlus();
            ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
                @Override
                public void onCountrySelected() {

                    ccp1 = ccp.getSelectedCountryCodeWithPlus();
                    Toast.makeText(Register.this, "Updated " + ccp.getSelectedCountryName(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {

        }

    }


    @Override
    public void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        mAuth.addAuthStateListener(mAuthListener);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    //TypeCastin is done here
    public void TypeCasting() {

        mName = (TextView) findViewById(R.id.name);
        mPhone = (TextView) findViewById(R.id.mobile);
        mEmail = (TextView) findViewById(R.id.email);
        mPassword = (TextView) findViewById(R.id.password);
        mRegister = (Button) findViewById(R.id.btnRegister);

        // String code = ccp.getSelectedCountryCodeWithPlus();
        //   Toast.makeText(Register.this,code,Toast.LENGTH_SHORT).show();
    }


    //Validate the data
    public boolean ValidateRegisterData() {

        try {
            boolean valid = Validate();
            //successful Validation
            if (valid) {
                msetvalue = true;
            }

        } catch (Exception ex) {

        }
        return msetvalue;
    }

    public boolean Validate() {

             msetvalue = true;

        if (!isValidMail(mEmail.getText().toString())) {
            mEmail.setError("Invalid Email Address");
            msetvalue = false;
        }
        if (!isValidName(mName.getText().toString())) {
            mName.setError("Name Should not be null");
            msetvalue = false;
        }
        if (mPhone.getText().toString().length() != 11) {
            mPhone.setError("Enter Valid Number");
            msetvalue = false;
        }
        if (!isValidPassword(mPassword.getText().toString())) {
            mPassword.setError("Try Upper/Lower Case and Numbers");
            return msetvalue = false;
        }
        return msetvalue;
    }

    private boolean isValidName(String name) {

        Pattern pattern;
        Matcher matcher;
        final String NAME_PATTERN = "(?=.*[a-z])(?=.*[A-Z]).+$";
        pattern = Pattern.compile(NAME_PATTERN);
        matcher = pattern.matcher(name);

        return matcher.matches();
    }


    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }


    //Register button click event
    public void RegisterButtonClick() {
        mRegister.setOnClickListener((new View.OnClickListener() {
            public void onClick(View v) {
                //Calling Validate Function
                mgetValue = ValidateRegisterData();
                try {
                    if (mgetValue) {
                        Toast.makeText(Register.this, "valid condition", Toast.LENGTH_SHORT).show();
                        //if validation is successful login user
                        //Calling method to save registration details
                        FirebaseSaveRegistrationData();
                    }
                } catch (Exception ex) {

                }
            }
        }));
    }


    //Firebase save registration detail of users

    public void FirebaseSaveRegistrationData() {

        final String mName1, mEmail1, mPassword1, mPhone1, mPhoneFinal;

        mName1 = mName.getText().toString();
        mEmail1 = mEmail.getText().toString();
        mPassword1 = mPassword.getText().toString();
        mPhone1 = mPhone.getText().toString();
        mPhoneFinal = ccp1.concat("-").concat(mPhone1);


        mAuth.createUserWithEmailAndPassword(mEmail1, mPassword1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());


                        try {

                            String user_id = mAuth.getCurrentUser().getUid();
                            //save user data along with its id
                            DatabaseReference current_user_db = mDatabase.child(user_id);
                            current_user_db.child("Name").setValue(mName1);
                            current_user_db.child("Phone").setValue(mPhoneFinal);
                        } catch (Exception ex) {

                        }
                        //After signup the user is pushed to signed out
                        mAuth.signOut();
                        Intent intent = new Intent(getApplication(), LoginRegister.class);
                        startActivity(intent);

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(Register.this, auth_failed, Toast.LENGTH_SHORT).show();
                        }


                    }
                });


    }


    //Login Button Click Event
    public void LoginButtonClick() {


        mlogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        mlogin.setOnClickListener((new View.OnClickListener() {
            public void onClick(View v) {

                //Calling Login Activity
                Intent intent = new Intent(getApplication(), LoginRegister.class);
                startActivity(intent);
            }
        }));
    }

    //Validation on Email
    private boolean isValidMail(String email) {

        try {
            Patterns.EMAIL_ADDRESS.matcher(email).matches();
        } catch (Exception ex) {

        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       /* getMenuInflater().inflate(R.menu.menu_register, menu);
        */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Register Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }
}
