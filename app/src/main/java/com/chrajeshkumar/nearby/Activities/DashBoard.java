package com.chrajeshkumar.nearby.Activities;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.chrajeshkumar.nearby.Helper.GPSTracker;
import com.chrajeshkumar.nearby.Helper.GetLat_Longs;
import com.chrajeshkumar.nearby.MapView;
import com.chrajeshkumar.nearby.R;
import com.chrajeshkumar.nearby.adapters.PagerAdapter;

import butterknife.ButterKnife;

/**
 * Created by ChRajeshKumar on 25-Jan-17.
 */

public class DashBoard extends AppCompatActivity implements GetLat_Longs
{
    String[] tab_names;
    TypedArray testArrayIcon;
    public static Activity activity;
    GPSTracker gps;
    double latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);
        activity = this;
        gettingLatLongs();
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tab_names = getResources().getStringArray(R.array.tab_names);
          testArrayIcon = getResources().obtainTypedArray(R.array.tab_icons);
        for(int i=0;i<tab_names.length;i++){
            tabLayout.addTab(tabLayout.newTab().setText(tab_names[i]));
        }
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(),DashBoard.this);
        viewPager.setAdapter(adapter);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i,tab_names,testArrayIcon));
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
    }
    public void gettingLatLongs(){
        gps = new GPSTracker(DashBoard.this);

        Log.e("lat is ","<><><"+gps.latitude+" longitude "+gps.longitude);
        // check if GPS enabled
        if(gps.canGetLocation()){

             latitude = gps.latitude;
             longitude = gps.longitude;

            // \n is for new line
//            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            latitude = 0.0;
            longitude = 0.0;
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
//            gps.showSettingsAlert();
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



}
