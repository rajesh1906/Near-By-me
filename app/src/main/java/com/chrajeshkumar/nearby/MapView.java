package com.chrajeshkumar.nearby;

import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;

import com.chrajeshkumar.nearby.Helper.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class MapView extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    GPSTracker gps;
    String latitude, longitude, location_name;

    FloatingActionButton fab;

    FloatingActionButton fab_share;

    FloatingActionButton fab_root_view;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab_share = (FloatingActionButton) findViewById(R.id.fab_share);
        fab_root_view = (FloatingActionButton) findViewById(R.id.fab_root_view);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        gettingLatLongs();
        Bundle bundle = getIntent().getBundleExtra("Location_info");

        latitude = bundle.getString("lat");
        longitude = bundle.getString("long");
        location_name = bundle.getString("location_name");
    }

    public void gettingLatLongs() {
        gps = new GPSTracker(MapView.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.latitude;
            double longitude = gps.longitude;

            // \n is for new line
//            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
//            gps.showSettingsAlert();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng location = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(latitude), Double.valueOf(longitude)), 17));
//        Marker marker = new Marker(googleMap);
        mMap.addMarker(new MarkerOptions().position(location).title(location_name));
        mMap.setOnMarkerClickListener(this);
//        MarkerAnimation.animateMarkerToGB()
//        mMap.moveCamera(CameraUpdateFactory.newCameraPosition());
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        //Make the marker bounce
        final Handler handler = new Handler();

        final long startTime = SystemClock.uptimeMillis();
        final long duration = 2000;

        Projection proj = mMap.getProjection();
        final LatLng markerLatLng = marker.getPosition();
        Point startPoint = proj.toScreenLocation(markerLatLng);
        startPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);

        final Interpolator interpolator = new BounceInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - startTime;
                float t = interpolator.getInterpolation((float) elapsed / duration);
                double lng = t * markerLatLng.longitude + (1 - t) * startLatLng.longitude;
                double lat = t * markerLatLng.latitude + (1 - t) * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });

        return true;
    }


    public String getCurrentCityName(double MyLat, double MyLong) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(MyLat, MyLong, 1);
            String cityName = addresses.get(0).getAddressLine(0);
            String stateName = addresses.get(0).getAddressLine(1);
            String countryName = addresses.get(0).getAddressLine(2);
            return cityName + ", " + stateName;
        } catch (Exception e) {
            e.printStackTrace();
            return "cannot get";
        }
    }
}
