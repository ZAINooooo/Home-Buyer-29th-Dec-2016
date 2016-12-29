package com.example.asad.homebuyerproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.*;
import android.widget.Toast;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

/**
 * Created by Asad on 9/2/2016.
 */
public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager ePager;
    private TextView Toolbartext;
    private NavigationView navigationView;
    //  private TextView mLoginButton;
    private Boolean myBooleanVariable = false;
    private View mLoginButton;
    private View new1;

    private TextView mEmailLogin,mNameLogin;
    private Firebase mRef;
    private Spinner mLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        Firebase.setAndroidContext(this);



        NavigationDrawerClickEvent(); //Attach header at run time
        TypeCasting(); //TypeCast of Attributes
        SetToolbar();    //setting Toolbar
        NavigationDrawerMethod(); //Show navigation drawer
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    //    Firebase.setAndroidContext(this);
     //   final Firebase rootRef = new Firebase("https://intense-heat-4844.firebaseio.com/property");
    }


    //Set Drawer
    //Calling Method on class "NavigationBar.java"
    public void NavigationDrawerMethod() {

        NavigationBar drawerfragment = (NavigationBar)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation1);
        drawerfragment.setup(R.id.fragment_navigation1, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

    }

    //Setting toolbar and toolbar text
    public void SetToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        Toolbartext.setText("Home Screen");

    }


    public void TypeCasting() {

        Toolbartext = (TextView) findViewById(R.id.toolbar_title);


    }




    /*Inflate view nad Login/Register click event on navigation drawer
    * Inflate header at runtime because its changes when user is online
    */
    public void NavigationDrawerClickEvent() {



        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            navigationView = (NavigationView) findViewById(R.id.nav_view);

        try {
            new1 = navigationView.inflateHeaderView(R.layout.nav_header);
        }catch (Exception ex)
        {
            Toast.makeText(getApplication(),"Asad",Toast.LENGTH_SHORT).show();
        }
            new1.findViewById(R.id.Login);
            //  if (!myBooleanVariable) {
            new1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Calling activity LoginRegister
                    Intent intent = new Intent(HomeActivity.this, LoginRegister.class);
                    startActivity(intent);
                }
            });


        //Account checking weather logged in if login then change header
        if (user != null) {

            //Remove previous header
            new1.setVisibility(View.GONE);
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            // Name, email address, and profile photo Url

            String name = user.getDisplayName();
            String email = user.getEmail();
            String uid = user.getUid();



            mLoginButton = navigationView.inflateHeaderView(R.layout.nav_header_login);
            mLogOut=(Spinner) mLoginButton.findViewById(R.id.spinner);

          /*  mRef=new Firebase("https://homebuyerteam-15a05.firebaseio.com/");
            Firebase userref=mRef.child("Users").child(user.getUid().toString());
            userref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String,String> map=dataSnapshot.getValue(Map.class);
                    String name=map.get("Name");
                    Toast.makeText(getApplication(),"Id:"+ name,Toast.LENGTH_SHORT).show();
                    mNameLogin.setText(name);

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });


            */


            mEmailLogin = (TextView) mLoginButton.findViewById(R.id.EmailLogin);
            mNameLogin = (TextView) mLoginButton.findViewById(R.id.NameLogin);
            mEmailLogin.setText(email);
            mNameLogin.setText(name);

            mLogOut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    if(selectedItem.equals("Log Out"))
                    {

                        FirebaseAuth.getInstance().signOut();
                        Intent intent= new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                        // do your stuff
                    }
                } // to close the onItemSelected
                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });


        }


    }

    //Inflate view nad Login/Register click event on navigation drawer


   /* public void NavigationDrawerClickEvent() {

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        mLoginButton = navigationView.inflateHeaderView(R.layout.nav_header);
        mLoginButton.findViewById(R.id.Login);
        //  if (!myBooleanVariable) {
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calling activity LoginRegister
                //  Intent intent = new Intent(getApplication(), LoginRegister.class);
                //  startActivity(intent);
            }
        });
    }*/

}



