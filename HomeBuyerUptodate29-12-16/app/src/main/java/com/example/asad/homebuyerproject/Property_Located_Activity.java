package com.example.asad.homebuyerproject;


import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.jakewharton.rxbinding.widget.AdapterViewItemClickEvent;
import com.jakewharton.rxbinding.widget.RxAutoCompleteTextView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.asad.homebuyerproject.data.model.Location;
import com.example.asad.homebuyerproject.data.model.PlaceAutocompleteResult;
import com.example.asad.homebuyerproject.data.model.PlaceDetailsResult;
import com.example.asad.homebuyerproject.data.model.Prediction;
import com.example.asad.homebuyerproject.data.RestClient;
import com.example.asad.homebuyerproject.utils.KeyboardHelper;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class Property_Located_Activity extends AppCompatActivity implements OnMapReadyCallback {

    private Button mNextActivity;
    private Toolbar toolbar;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    // flag for GPS status
    boolean isGPSEnabled = false;
    // flag for network status
    boolean isNetworkEnabled = false;
    private static final long DELAY_IN_MILLIS = 500;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    private GoogleMap map;
    String datafrompreviousactivity;
    private ArrayList<String> propertyData=new ArrayList<>();
    private double lat,lon;
    private EditText fulladdress;
    String shortaddress,GPSaddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property__located_);
        SupportMapFragment mapFragment = (SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if (extras == null)
            {  Toast.makeText(Property_Located_Activity.this, "Previous Data Not Found", Toast.LENGTH_SHORT).show(); }
            else
            {  propertyData = extras.getStringArrayList("data");
                //Toast.makeText(this, "Index not "+propertyData.size(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(Property_Located_Activity.this, propertyData+" || ", Toast.LENGTH_SHORT).show();
            }
        }
        else
        { datafrompreviousactivity = (String) savedInstanceState.getSerializable("SellRentData"); }

        // TypeCasting();
       // Toast.makeText(this, "Index not "+propertyData.contains("selltype"), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Index not "+propertyData.indexOf("SellType"), Toast.LENGTH_SHORT).show();
        SetToolbar();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNextActivity=(Button)findViewById(R.id.NextActivity);
        fulladdress=(EditText)findViewById(R.id.addresscity);
        mNextActivity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (fulladdress.getText().toString().matches("")){
                    fulladdress.setError("Enter Detail Address");
                }
                else {
                    propertyData.add("propertylongitude");
                    propertyData.add(String.valueOf(lon));
                    propertyData.add("propertylontitude");
                    propertyData.add(String.valueOf(lat));
                    propertyData.add("propertyfulladress");
                    propertyData.add(fulladdress.getText().toString());
                    propertyData.add("propertyshortaddress");
                    propertyData.add(String.valueOf(shortaddress));
                    Toast.makeText(getApplication(), datafrompreviousactivity, Toast.LENGTH_SHORT).show();
                    Intent next = new Intent(Property_Located_Activity.this, Residential_Commercial_Activity.class);
                    //pass data to other activity which get from SellRentActivity
                    next.putStringArrayListExtra("data", propertyData);
                    startActivity(next);
                }
            }
        });
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {}
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}
            @Override
            public void onProviderEnabled(String s) {}
            @Override
            public void onProviderDisabled(String s){}

        };

        if (ActivityCompat.checkSelfPermission(Property_Located_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Property_Located_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
                }, 10);
            }
            return;
        } else {
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled ) {
                // no network provider is enabled
              //  showSettingsAlert();

            }
                configurebutton();

        }
        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autocomplete_text);
        addOnAutoCompleteTextViewItemClickedSubscriber(autoCompleteTextView);
        addOnAutoCompleteTextViewTextChangedObserver(autoCompleteTextView);
    }
    private boolean isValidName(String name) {
        Pattern pattern;
        Matcher matcher;
        final String NAME_PATTERN = "(?=.*[a-z])(?=.*[A-Z]).+$";
        pattern = Pattern.compile(NAME_PATTERN);
        matcher = pattern.matcher(name);
        return matcher.matches();
    }
    private void SetToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
//      Toolbartext.setText("Test Screen");
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    configurebutton();
                    return;
                }
        }
    }

    private void configurebutton() {
        ImageView img_view = (ImageView) findViewById(R.id.Gps);

        img_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if(isGPSEnabled) {
                    if (ActivityCompat.checkSelfPermission(Property_Located_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Property_Located_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }

                    if (locationManager != null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                        android.location.Location gpslocation = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        if (gpslocation != null) {
                            LatLng latLng = new LatLng(gpslocation.getLatitude(), gpslocation.getLongitude());
                            lat=gpslocation.getLatitude();
                            lon=gpslocation.getLongitude();
                            map.clear();
                            Marker marker = map.addMarker(new MarkerOptions().position(latLng).title("You Are Here"));
                            marker.showInfoWindow();
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                            Geocoder geocoder;
                            List<Address> addresses;
                            geocoder = new Geocoder(Property_Located_Activity.this, Locale.getDefault());
                            addresses = geocoder.getFromLocation(gpslocation.getLatitude(), gpslocation.getLongitude(), 1);
                            GPSaddress = addresses.get(0).getLocality()+","+addresses.get(0).getCountryName();
                            if(!GPSaddress.matches("")){
                                final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autocomplete_text);
                                setTextinAutoComplete(autoCompleteTextView);
                            }
                        }

                        if (locationManager != null) {
                            locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                            Log.d("Network", "Network");
                            android.location.Location networklocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (networklocation != null) {
                                LatLng latLng = new LatLng(networklocation.getLatitude(), networklocation.getLongitude());
                                lat=networklocation.getLatitude();
                                lon=networklocation.getLongitude();
                                map.clear();
                                Marker marker = map.addMarker(new MarkerOptions().position(latLng).title("Home Sweet Home"));
                                marker.showInfoWindow();
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                            }
                        }


                    }
                }
                    else {
                    Toast.makeText(Property_Located_Activity.this, "GPS is not Currently Available", Toast.LENGTH_SHORT).show();
                }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
private void setTextinAutoComplete(final AutoCompleteTextView autoCompleteTextView){
    autoCompleteTextView.setText(GPSaddress);
}
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    private void addOnAutoCompleteTextViewTextChangedObserver(final AutoCompleteTextView autoCompleteTextView) {
        Observable<PlaceAutocompleteResult> autocompleteResponseObservable = RxTextView.textChangeEvents(autoCompleteTextView).debounce(DELAY_IN_MILLIS, TimeUnit.MILLISECONDS)

                .map(new Func1<TextViewTextChangeEvent, String>() {
                    @Override
                    public String call(TextViewTextChangeEvent textViewTextChangeEvent) {
                        return textViewTextChangeEvent.text().toString();
                    }
                })

                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s.length() >= 2;
                    }
                })

                .observeOn(Schedulers.io()).flatMap(new Func1<String, Observable<PlaceAutocompleteResult>>() {
                    @Override
                    public Observable<PlaceAutocompleteResult> call(String s) {
                        return RestClient.INSTANCE.getGooglePlacesClient().autocomplete(s);
                    }
                })

                .observeOn(AndroidSchedulers.mainThread()).retry();


        compositeSubscription.add(autocompleteResponseObservable.subscribe(new Observer<PlaceAutocompleteResult>() {

            private static final String TAG = "PlaceAutocompleteResult";

            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted");

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError", e);
            }

            @Override
            public void onNext(PlaceAutocompleteResult placeAutocompleteResult) {
                Log.i(TAG, placeAutocompleteResult.toString());

                List<NameAndPlaceId> list = new ArrayList<>();
                for (Prediction prediction : placeAutocompleteResult.predictions) {
                    list.add(new NameAndPlaceId(prediction.description, prediction.placeId));
                }

                ArrayAdapter<NameAndPlaceId> itemsAdapter = new ArrayAdapter<>(Property_Located_Activity.this,
                        android.R.layout.simple_list_item_1, list);
                autoCompleteTextView.setAdapter(itemsAdapter);
                String enteredText = autoCompleteTextView.getText().toString();
                if (list.size() >= 1 && enteredText.equals(list.get(0).name)) {
                    if (!isValidName(autoCompleteTextView.getText().toString())){
                        autoCompleteTextView.setError("Field Can not be Empty");
                    }else {
                        shortaddress = autoCompleteTextView.getText().toString();
                    }
                   autoCompleteTextView.dismissDropDown();
                } else {
                    autoCompleteTextView.showDropDown();
                }
            }
        }));
    }

    public void showSettingsAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Property_Located_Activity.this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is Disable");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                Property_Located_Activity.this.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void addOnAutoCompleteTextViewItemClickedSubscriber(final AutoCompleteTextView autoCompleteTextView) {
        Observable<PlaceDetailsResult> adapterViewItemClickEventObservable = RxAutoCompleteTextView.itemClickEvents(autoCompleteTextView)

                .map(new Func1<AdapterViewItemClickEvent, String>() {
                    @Override
                    public String call(AdapterViewItemClickEvent adapterViewItemClickEvent) {
                        NameAndPlaceId item = (NameAndPlaceId) autoCompleteTextView.getAdapter()
                                .getItem(adapterViewItemClickEvent.position());
                        return item.placeId;
                    }
                })

                .observeOn(Schedulers.io()).flatMap(new Func1<String, Observable<PlaceDetailsResult>>() {
                    @Override
                    public Observable<PlaceDetailsResult> call(String placeId) {
                        return RestClient.INSTANCE.getGooglePlacesClient().details(placeId);
                    }
                })


                .observeOn(AndroidSchedulers.mainThread()).retry();

        compositeSubscription.add(adapterViewItemClickEventObservable.subscribe(new Observer<PlaceDetailsResult>() {

            private static final String TAG = "PlaceDetailsResult";

            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError", e);
            }

            @Override
            public void onNext(PlaceDetailsResult placeDetailsResponse) {
                Log.i(TAG, placeDetailsResponse.toString());
                updateMap(placeDetailsResponse);
            }
        }));
    }


    private void updateMap(PlaceDetailsResult placeDetailsResponse) {
        if (map != null) {
            map.clear();
            Location location = placeDetailsResponse.result.geometry.location;
            LatLng latLng = new LatLng(location.lat, location.lng);
            lat=location.lat;
            lon=location.lng;
            Marker marker = map.addMarker(new MarkerOptions().position(latLng).title(placeDetailsResponse.result.name));
            marker.showInfoWindow();
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            KeyboardHelper.hideKeyboard(Property_Located_Activity.this);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }


    //Location Listener methods


    private static class NameAndPlaceId {
        final String name;
        final String placeId;

        NameAndPlaceId(String name, String placeId) {
            this.name = name;
            this.placeId = placeId;
        }

        @Override
        public String toString()

        {
            return name;
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_residencial_search, menu);
//        return true;
//    }


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

