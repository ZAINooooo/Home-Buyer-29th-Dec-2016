package com.example.asad.homebuyerproject;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Asad on 10/9/2016.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        // other setup code
    }

}
