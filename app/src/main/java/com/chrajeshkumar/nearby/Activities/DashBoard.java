package com.chrajeshkumar.nearby.Activities;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.chrajeshkumar.nearby.Helper.GPSTracker;
import com.chrajeshkumar.nearby.Helper.GetLat_Longs;
import com.chrajeshkumar.nearby.Network.JSON.CustomJSONObjectRequest;
import com.chrajeshkumar.nearby.R;
import com.chrajeshkumar.nearby.adapters.PagerAdapter;

/**
 * Created by ChRajeshKumar on 25-Jan-17.
 */

public class DashBoard extends AppCompatActivity implements GetLat_Longs {
    String[] tab_names;
    TypedArray testArrayIcon;
    public static Activity activity;
    GPSTracker gps;
    double latitude, longitude;
    Spinner spinner;
    public static String distance = "100 mtrs";
    String[] range;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.home_view);
        spinner = (Spinner)findViewById(R.id.spinner);
        activity = this;
        gettingLatLongs();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tab_names = getResources().getStringArray(R.array.tab_names);
        testArrayIcon = getResources().obtainTypedArray(R.array.tab_icons);
        for (int i = 0; i < tab_names.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tab_names[i]));
        }
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(), DashBoard.this);
        viewPager.setAdapter(adapter);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i, tab_names, testArrayIcon));
        }
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        range = getResources().getStringArray(R.array.range);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, range);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                distance = range[position];
                Toast.makeText(DashBoard.this,"distance is "+distance,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void gettingLatLongs() {
        gps = new GPSTracker(DashBoard.this);

        Log.e("lat is ", "<><><" + gps.latitude + " longitude " + gps.longitude);
        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.latitude;
            longitude = gps.longitude;
        } else {
            latitude = 0.0;
            longitude = 0.0;
        }
    }

    @Override
    public String getLatitude() {
        return String.valueOf(latitude);
    }

    @Override
    public String getLongitude() {
        return String.valueOf(longitude);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
