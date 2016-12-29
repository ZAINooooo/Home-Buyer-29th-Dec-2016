package com.example.asad.homebuyerproject;

import android.app.Application;

/**
 * Created by zain on 11/25/2016.
 */
public class AutocompleteApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        String googleMapsKey = getString(R.string.google_maps_key);
        if (googleMapsKey.isEmpty()) {
            throw new RuntimeException("You should add your Google Maps API Key!");
        }
    }
}


