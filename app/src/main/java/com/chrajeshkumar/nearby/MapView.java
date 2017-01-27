package com.chrajeshkumar.nearby;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.chrajeshkumar.nearby.Helper.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapView extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    GPSTracker gps;
    String latitude,longitude,location_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapview_frame);
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


    public void gettingLatLongs(){
        gps = new GPSTracker(MapView.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.latitude;
            double longitude = gps.longitude;

            // \n is for new line
//            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
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
        mMap.addMarker(new MarkerOptions().position(location).title(location_name));
//        mMap.moveCamera(CameraUpdateFactory.newCameraPosition());
    }
}
