package com.example.asad.homebuyerproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class Residential_Commercial_Activity extends AppCompatActivity {


    private Toolbar toolbar;
    private Button mNextActivity;
    private RadioGroup mRadioGroup;
    private String datafromlocateedactivity;
    private String presidentialcommercial;

    private ArrayList<String> propertyData=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residential__commercial_);


        //getting data from prviousactivity
        if (savedInstanceState == null) {


            Bundle extras = getIntent().getExtras();
            if (extras == null)
            {  Toast.makeText(Residential_Commercial_Activity.this, "Previous Data Not Found", Toast.LENGTH_SHORT).show(); }
            else
            {  propertyData = extras.getStringArrayList("data");
                //Toast.makeText(this, "Index not "+propertyData.size(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(Property_Located_Activity.this, propertyData+" || ", Toast.LENGTH_SHORT).show();
            }
        } else {
            datafromlocateedactivity= (String) savedInstanceState.getSerializable("SellRentData");
        }


        Firstopenfragment();
        SetToolbar();
        TypeCasting();

        //FOR BACK BUTTON ENABLED
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RelativeLayout rl = (RelativeLayout) findViewById(R.id.Replace);

                if (checkedId == R.id.Commercial) {
                    //calling fragment  Builder_Broker_Fragment
                    CommercialFragment frag = new CommercialFragment();
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.Replace, frag, "Commercial");
                    presidentialcommercial="Commercial";

                    //send data to fragment
                    Bundle bundle = new Bundle();
                    //Toast.makeText(Residential_Commercial_Activity.this, "SellRentData"+propertyData.get(propertyData.indexOf("SellType")+1), Toast.LENGTH_SHORT).show();
                    bundle.putString("SellRentData",propertyData.get(propertyData.indexOf("SellType")+1) );
                    bundle.putString("ResidentialCommercialData", presidentialcommercial);
                    bundle.putStringArrayList("data",propertyData);
                    frag.setArguments(bundle);
                    //  transaction.replace(R.id.frag1,frag);
                    transaction.commit();
                    Toast.makeText(getApplication(), "clicked", Toast.LENGTH_SHORT).show();

                } else if (checkedId == R.id.Residential) {

                    ResidentialFragment frag = new ResidentialFragment();
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.Replace, frag, "Residential");
                    presidentialcommercial="Residential";
                    //send data to fragment
                    Bundle bundle = new Bundle();
                    bundle.putString("SellRentData",propertyData.get(propertyData.indexOf("SellType")+1) );
                    bundle.putString("ResidentialCommercialData", presidentialcommercial);
                    bundle.putStringArrayList("data",propertyData);
                    frag.setArguments(bundle);
                    transaction.commit();
                }
            }
        });
    }
    private void Firstopenfragment() {
        ResidentialFragment frag = new ResidentialFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        presidentialcommercial="Residential";
        //send data to fragment
        RadioButton r=(RadioButton)findViewById(R.id.Residential);
        Bundle bundle = new Bundle();
        bundle.putString("SellRentData",propertyData.get(propertyData.indexOf("SellType")+1) );
        bundle.putString("ResidentialCommercialData", presidentialcommercial);
        bundle.putStringArrayList("data",propertyData);
        frag.setArguments(bundle);
        transaction.replace(R.id.Replace, frag, "Residential");
        r.setChecked(true);
        transaction.commit();
    }

    public void SetToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        //   Toolbartext.setText("Test Screen");

    }

    public void TypeCasting() {

        mRadioGroup = (RadioGroup) findViewById(R.id.RadioButton);
        mNextActivity = (Button) findViewById(R.id.NextActivity);


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

