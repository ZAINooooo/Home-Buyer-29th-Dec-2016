package com.example.asad.homebuyerproject;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Asad on 9/3/2016.
 */
public class NavigationBar extends Fragment {

    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private boolean mFromSaveInstanceState;
    private boolean mUserLearnedDrawer;
    private View containerView;
    private NavigationView navigationView;
    private TextView mLoginButton;


    public NavigationBar() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSaveInstanceState = true;
        }


    } @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View layout = inflater.inflate(R.layout.fragment_navigation, container, false);

        mDrawerLayout = (DrawerLayout) layout.findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) layout.findViewById(R.id.nav_view);
        // code for header onClick
    /*    View mLoginButton = navigationView.inflateHeaderView(R.layout.nav_header);
        mLoginButton.findViewById(R.id.Login);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calling activity LoginRegister
                Intent intent = new Intent(getActivity(), LoginRegister.class);
                startActivity(intent);
            }
        });
        */
        //code for Header onClick

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                mDrawerLayout.closeDrawers();


                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_shortlist:
                   //     Toast.makeText(getActivity(), "Shortlist Selected", Toast.LENGTH_SHORT).show();
                        mDrawerLayout.closeDrawers();
                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.nav_home:
                  //      Toast.makeText(getActivity(), "HomeSelected", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getActivity(), TestActivity.class);
                        startActivity(intent);

                        return true;
                    case R.id.nav_residential:
                    //    Toast.makeText(getActivity(), "Residential Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_commercial:
                    //    Toast.makeText(getActivity(), "CommercialSelected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_postproperty:

                        Intent postproperty = new Intent(getActivity(), PostProperty.class);
                        startActivity(postproperty);

                        return true;
                    case R.id.nav_listing:
                      //  Toast.makeText(getActivity(), "Listing Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_viewResponces:
                     //   Toast.makeText(getActivity(), "View Responces Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_aboutUs:
                      //  Toast.makeText(getActivity(), "About Us Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_feedback:
                      //  Toast.makeText(getActivity(), "Feedback Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                     //   Toast.makeText(getActivity(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });


        return layout;


    }



    public void setup(int fragmentId, DrawerLayout drawerlayout, Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerlayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerlayout, toolbar, R.string.drawer_open, R.string.drawer_closed) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "");

                }
                getActivity().invalidateOptionsMenu();


            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        if (!mUserLearnedDrawer && mFromSaveInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
                               @Override
                               public void run() {
                                   mDrawerToggle.syncState();
                               }
                           }
        );
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }
}

