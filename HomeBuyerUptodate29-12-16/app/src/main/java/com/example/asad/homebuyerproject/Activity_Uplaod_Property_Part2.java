package com.example.asad.homebuyerproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity_Uplaod_Property_Part2 extends AppCompatActivity {

    private String Sell, Rent;
    private ArrayList<String> propertyData=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__uplaod__property__part2);

        //getting data

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                Sell = null;
                Rent = null;

            } else {
                /*Sell = extras.getString("Sell");
                Rent = extras.getString("Rent");*/
                propertyData=extras.getStringArrayList("data");
                Sell =propertyData.get(propertyData.indexOf("SellType")+1);
                Rent =propertyData.get(propertyData.indexOf("SellType")+1);
            }
        } else {
            Sell = (String) savedInstanceState.getSerializable("Sell");
            Rent = (String) savedInstanceState.getSerializable("Rent");

        }
        Toast.makeText(this, propertyData.get(propertyData.indexOf("Furnishing")+1), Toast.LENGTH_SHORT).show();

        Typecasting();
        OpenFragments();


/*
        String[] list = getResources().getStringArray(R.array.itemsforarea);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coverdarea.setAdapter(adapter);
        */

    }

    private void OpenFragments() {


      try {

          if (Sell != null) {

              if (Sell.equals("Sell") && Sell != null) {

                  Toast.makeText(this, propertyData.get(propertyData.indexOf("Furnishing")+1), Toast.LENGTH_SHORT).show();

                  Fragment_Sell_All_Part2 frag = new Fragment_Sell_All_Part2();
                  FragmentManager manager = getFragmentManager();
                  FragmentTransaction transaction = manager.beginTransaction();
                  transaction.replace(R.id.parent, frag, "Sell For All");
                  Bundle bundle=new Bundle();
                  bundle.putStringArrayList("data",propertyData);
                  frag.setArguments(bundle);
                  transaction.commit();

              }else if(Sell.equals("SellCustom"))
              {
                  String SellRemoveItem = "SellCommercialLand";
                  String SellRemoveItem2 = "SellIndustrialLand";
                  String SellRemoveItem3 = "SellPlot";
                  Fragment_Sell_All_Part2 frag = new Fragment_Sell_All_Part2();
                  FragmentManager manager = getFragmentManager();
                  FragmentTransaction transaction = manager.beginTransaction();
                  transaction.replace(R.id.parent, frag, "Sell custom");

                  //send data to fragment
                  Bundle bundle = new Bundle();
                  bundle.putString("SellData", SellRemoveItem);
                  bundle.putString("SellData2", SellRemoveItem2);
                  bundle.putString("SellData3", SellRemoveItem3);

                  bundle.putStringArrayList("data",propertyData);

                  frag.setArguments(bundle);
                  transaction.commit();
              }
          }
          if (Rent != null) {
              if (Rent.equals("Rent")) {

                  Fragment_Rent_All_Part2 frag = new Fragment_Rent_All_Part2();
                  FragmentManager manager = getFragmentManager();
                  FragmentTransaction transaction = manager.beginTransaction();
                  transaction.replace(R.id.parent, frag, "Rent For All");
                  Bundle bundle=new Bundle();
                  bundle.putStringArrayList("data",propertyData);
                  frag.setArguments(bundle);
                  transaction.commit();
              }else if(Rent.equals("RentCustom"))
              {

                  Toast.makeText(getApplication(),"custom",Toast.LENGTH_SHORT).show();
                  String RentRemoveItem = "RentCommercialLand";
                  String RentRemoveItem2 = "RentIndustrialLand";

                  Fragment_Rent_All_Part2 frag = new Fragment_Rent_All_Part2();
                  FragmentManager manager = getFragmentManager();
                  FragmentTransaction transaction = manager.beginTransaction();
                  transaction.replace(R.id.parent, frag, "Rent Custom");
                  //send data to fragment
                  Bundle bundle = new Bundle();
                  bundle.putStringArrayList("data",propertyData);
                  bundle.putString("RentData", RentRemoveItem);
                  bundle.putString("RentData2", RentRemoveItem2);
                  frag.setArguments(bundle);
                  transaction.commit();
              }
          }
      }
      catch (Exception ex)
        {

        }


    }

    private void Typecasting() {


    }
}