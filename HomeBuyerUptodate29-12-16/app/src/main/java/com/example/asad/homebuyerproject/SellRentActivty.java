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
import android.widget.Toast;

import java.util.ArrayList;

public class SellRentActivty extends AppCompatActivity {

    private Toolbar toolbar;
    private RadioGroup mRadioGroup;
    private RadioButton mSell, mRent;
    private Button mNextActivity;
    private String psellrent;
    private ArrayList<String> propertyData=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_rent_activty);

        TypeCasting();
        SetToolbar();

        //FOR BACK BUTTON ENABLED
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Bundle extras = getIntent().getExtras();
                if(extras==null){
                    Toast.makeText(SellRentActivty.this, "Previous Data Not Found", Toast.LENGTH_SHORT).show();
                }else {
                    propertyData = extras.getStringArrayList("data");
                }
                if (checkedId == R.id.Sell) {

                    psellrent="Sell";

                } else
                {
                    psellrent="Rent";
                }
                propertyData.add("SellType");
                propertyData.add(psellrent);
                Intent next = new Intent(SellRentActivty.this,Property_Located_Activity.class);
                next.putStringArrayListExtra("data",propertyData);
                next.putExtra("SellRentData",psellrent);
                startActivity(next);

            }

        });


    }
    public void TypeCasting() {
        mRadioGroup = (RadioGroup) findViewById(R.id.RadioButton);
        mSell = (RadioButton) findViewById(R.id.Sell);
        mRent = (RadioButton) findViewById(R.id.Rent);
        mNextActivity=(Button)findViewById(R.id.NextActivity);


    }

    public void SetToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        //   Toolbartext.setText("Test Screen");

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
