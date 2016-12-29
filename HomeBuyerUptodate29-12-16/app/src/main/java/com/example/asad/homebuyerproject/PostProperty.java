package com.example.asad.homebuyerproject;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PostProperty extends AppCompatActivity {

    private Toolbar toolbar;
    private RadioGroup mRadioGroup;
    private TextView mOwner, mBuilder, mAgent;
    private Spinner spin;
    private TextView text;
    private Button mNextActivity;
    private String Type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_property);


        TypeCasting();
        SetToolbar();
       // text.setText(spin.getSelectedItem().toString());


        //FOR BACK BUTTON ENABLED
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      /*  mNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent next = new Intent(PostProperty.this,SellRentActivty.class);
                startActivity(next);

            }
        });
        */


        mAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mAgent.setBackgroundColor(Color.parseColor("#E1F5FE"));
                mOwner.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mBuilder.setBackgroundColor(Color.parseColor("#FFFFFF"));
                Intent next = new Intent(PostProperty.this,Activity_Post_property_Click.class);
                Type="Agent";
                next.putExtra("TypeSelected",Type);
                startActivity(next);




            }
        });

        mOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mOwner.setBackgroundColor(Color.parseColor("#E1F5FE"));
                mAgent.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mBuilder.setBackgroundColor(Color.parseColor("#FFFFFF"));


                Intent next = new Intent(PostProperty.this,Activity_Post_property_Click.class);
                Type="Owner";
                next.putExtra("TypeSelected",Type);
                startActivity(next);


            }
        });

        mBuilder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBuilder.setBackgroundColor(Color.parseColor("#E1F5FE"));
                mOwner.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mAgent.setBackgroundColor(Color.parseColor("#FFFFFF"));

                Intent next = new Intent(PostProperty.this,Activity_Post_property_Click.class);
                Type="Builder";
                next.putExtra("TypeSelected",Type);
                startActivity(next);

            }
        });


       /* mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio) {

                    Builder_Broker_Fragment frag=new Builder_Broker_Fragment();
                    FragmentManager manager =getFragmentManager();
                    FragmentTransaction transaction= manager.beginTransaction();
                    transaction.replace(R.id.fragment,frag,"BrokerFragment");
                    //  transaction.replace(R.id.frag1,frag);
                    transaction.commit();
                    Toast.makeText(getApplication(),"clicked",Toast.LENGTH_SHORT).show();


                } else if (checkedId == R.id.radio1) {

                    OwnerFreagment frag=new OwnerFreagment();
                    FragmentManager manager =getFragmentManager();
                    FragmentTransaction transaction= manager.beginTransaction();
                    transaction.replace(R.id.fragment, frag, "OwnerFragment");

                    transaction.commit();
                    Toast.makeText(getApplication(),"clicked1",Toast.LENGTH_SHORT).show();

                } else {

                    Builder_Broker_Fragment frag=new Builder_Broker_Fragment();
                    FragmentManager manager =getFragmentManager();
                    FragmentTransaction transaction= manager.beginTransaction();
                    transaction.replace(R.id.fragment,frag,"BrokerFragment");
                    //   OwnerFreagment frag1=new OwnerFreagment();
                    //  transaction.remove(frag1);
                    transaction.commit();
                    Toast.makeText(getApplication(),"clicked2",Toast.LENGTH_SHORT).show();

                }

            }

        });

        */


    }
    public void SetToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        //   Toolbartext.setText("Test Screen");

    }

    public void TypeCasting() {
      //  mRadioGroup = (RadioGroup) findViewById(R.id.RadioButton);
        mAgent = (TextView) findViewById(R.id.radio2);
        mOwner = (TextView) findViewById(R.id.radio0);
        mBuilder = (TextView) findViewById(R.id.radio1);
     //   mNextActivity=(Button)findViewById(R.id.NextActivity);


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

        //FOR BACK BUTTON ALSO INCLUDE META DATA IN MANIFEST
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);

    }


}
