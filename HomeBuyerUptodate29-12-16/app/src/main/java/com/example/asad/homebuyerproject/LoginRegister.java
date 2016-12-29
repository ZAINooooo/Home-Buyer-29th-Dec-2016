package com.example.asad.homebuyerproject;

/**
 * Created by Asad on 10/9/2016.
 */

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginRegister extends AppCompatActivity {

    private Button mRegister, mLogin;
    private TextView mEMAIL, mPassword;
    private Boolean mgetValue;
    private FirebaseAuth mAuth;
    String TAG="Login Activity"
    ;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        mAuth = FirebaseAuth.getInstance();
        //Calling Methods
        TypeCast();
        RegisterButtonClickEvent();
        LoginButonClickEvent();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //Type casting is done here
    public void TypeCast() {
        mRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        mLogin = (Button) findViewById(R.id.btnLogin);
        mEMAIL = (TextView) findViewById(R.id.email);
        mPassword = (TextView) findViewById(R.id.password);
    }


    //Register button cick event on Regiter Activity
    public void RegisterButtonClickEvent() {

        mRegister.setOnClickListener((new View.OnClickListener() {

            public void onClick(View v) {

                //Start Activity
                Intent intent = new Intent(getApplication(), Register.class);
                startActivity(intent);
            }
        }));
    }


    //Login Button Click Event
    public void LoginButonClickEvent() {
        mLogin.setOnClickListener((new View.OnClickListener() {
            public void onClick(View v) {

                //Call Validation
                mgetValue = mValidateLoginData();

                try {
                    if (mgetValue) {
                        //if validation is successful login user
                        FirebaseLoginUser();
                    }
                } catch (Exception ex) {

                }


            }
        }));

    }

    //Validating user data
    public boolean mValidateLoginData() {

        boolean msetvalue = false;
        if (!isValidMail(mEMAIL.getText().toString())) {
            mEMAIL.setError("Email is incorrect");
        } else if (mPassword.getText().toString().length() == 0) {
            mPassword.setError("Field Invalid");
        } else {
            //Means Validation is successful
            msetvalue = true;
        }
        return msetvalue;
    }


    //Firebase method to check authentication and login user
    public void FirebaseLoginUser() {
        mAuth.signInWithEmailAndPassword(mEMAIL.getText().toString(), mPassword.getText().toString())

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        Intent intent= new Intent(LoginRegister.this,HomeActivity.class);
                        startActivity(intent);


                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());

                        }

                    }
                });
    }

    //Validate email address
    private boolean isValidMail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        /*  getMenuInflater().inflate(R.menu.menu_login_register, menu);/*

         */
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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("LoginRegister Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
