package com.example.asad.homebuyerproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Activity_Post_property_Click extends AppCompatActivity {

    private Toolbar toolbar;
    private String Data;
    private String OwnerType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__post_property__click);


        //getting data from prviousactivity
        if (savedInstanceState == null) {


            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                Data = null;
            } else {
                Data = extras.getString("TypeSelected");

            }
        } else {
            Data = (String) savedInstanceState.getSerializable("TypeSelected");
        }

        TypeCasting();
        SetToolbar();
        OpenFragments();

        //FOR BACK BUTTON ENABLED
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void TypeCasting() {



    }
    public void SetToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        //   Toolbartext.setText("Test Screen");

    }


    private void OpenFragments() {


        if(Data.equals("Builder"))
        {
            Builder_Broker_Fragment frag=new Builder_Broker_Fragment();
            FragmentManager manager =getFragmentManager();
            FragmentTransaction transaction= manager.beginTransaction();
            transaction.replace(R.id.Replaced,frag,"BrokerFragment");
            //  transaction.replace(R.id.frag1,frag);

            Bundle bundle = new Bundle();
            OwnerType="Builder";
            bundle.putString("OwnerType",OwnerType);
            frag.setArguments(bundle);

            transaction.commit();
            Toast.makeText(getApplication(),"clicked",Toast.LENGTH_SHORT).show();

        }else if(Data.equals("Owner"))
        {
            OwnerFreagment frag=new OwnerFreagment();
            FragmentManager manager =getFragmentManager();
            FragmentTransaction transaction= manager.beginTransaction();
            transaction.replace(R.id.Replaced, frag, "OwnerFragment");


            Bundle bundle = new Bundle();
            OwnerType="Owner";
            bundle.putString("OwnerType",OwnerType);
            bundle.putString("Type",Data);
            frag.setArguments(bundle);

            transaction.commit();
            Toast.makeText(getApplication(),"clicked1",Toast.LENGTH_SHORT).show();

        }else if(Data.equals("Agent"))
        {
            Builder_Broker_Fragment frag=new Builder_Broker_Fragment();
            FragmentManager manager =getFragmentManager();
            FragmentTransaction transaction= manager.beginTransaction();
            transaction.replace(R.id.Replaced,frag,"BrokerFragment");
            //   OwnerFreagment frag1=new OwnerFreagment();
            //  transaction.remove(frag1);

            Bundle bundle = new Bundle();
            OwnerType="Agent";
            bundle.putString("OwnerType",OwnerType);
            frag.setArguments(bundle);
            transaction.commit();
            Toast.makeText(getApplication(),"clicked2",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplication(),"Error",Toast.LENGTH_SHORT).show();
        }
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
