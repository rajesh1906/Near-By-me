package com.chrajeshkumar.nearby.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.chrajeshkumar.nearby.Helper.GPSTracker;
import com.chrajeshkumar.nearby.Helper.GetLat_Longs;
import com.chrajeshkumar.nearby.Helper.Getting_Lat_Instance;
import com.chrajeshkumar.nearby.R;
import com.chrajeshkumar.nearby.adapters.PagerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by ChRajeshKumar on 25-Jan-17.
 */

public class DashBoard extends AppCompatActivity implements GetLat_Longs {
    String[] tab_names;
    TypedArray testArrayIcon;
    public static Activity activity;
    GPSTracker gps;
    public static double latitude, longitude;
    Spinner spinner;
    public static int distance = 500;
    String[] range;
    public boolean coming_to;
    private AdView mAdView;
    private static InterstitialAd mInterstitialAd;
    private static AdRequest inter_adRequest;
    private AdRequest banner_adRequest;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.home_view);
        spinner = (Spinner) findViewById(R.id.spinner);
        activity = this;
        context = getApplicationContext();
        gettingLatLongs();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tab_names = getResources().getStringArray(R.array.tab_names);
        testArrayIcon = getResources().obtainTypedArray(R.array.tab_icons);
        for (int i = 0; i < tab_names.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tab_names[i]));
        }
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF8C00"));
//        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
//        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#FF8C00"));
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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner, R.id.txt_item, range);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.custom_spinner);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                distance = Integer.parseInt(range[position].replaceAll("[\\D]", ""));
                Toast.makeText(DashBoard.this, "distance is " + range[position], Toast.LENGTH_LONG).show();
                adapter.Calling_Fregment(viewPager.getCurrentItem());
//                Log.e("view pager position is ", "<><><" + viewPager.getCurrentItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        initializeInterstitialAdds();
        my_adds();
    }

    protected static void initializeInterstitialAdds() {
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-9862631671335648/2333635418");
        inter_adRequest = new AdRequest.Builder()
                .addTestDevice("2C14496EAB6A6D68D5DE6F8A124A53F8")
                .build();
        mInterstitialAd.loadAd(inter_adRequest);
    }

    public void my_adds() {
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-9862631671335648~6903435817");
        mAdView = (AdView) findViewById(R.id.adView);
        banner_adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("2C14496EAB6A6D68D5DE6F8A124A53F8")
                .build();
        mAdView.loadAd(banner_adRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (coming_to) {
            gettingLatLongs();
        }
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    public void gettingLatLongs() {
        gps = new GPSTracker(DashBoard.this);

        Log.e("lat is ", "<><><" + gps.latitude + " longitude " + gps.longitude);
        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.latitude;
            longitude = gps.longitude;
            coming_to = false;
        } else {
            coming_to = true;
            showSettingsAlert();
            latitude = 0.0;
            longitude = 0.0;
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DashBoard.activity);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                DashBoard.activity.startActivity(intent);
                finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "onActiviy called", Toast.LENGTH_LONG).show();
    }

    /*@Override
    public void getLat_Instance() {
        gettingLatLongs();
    }*/
}
