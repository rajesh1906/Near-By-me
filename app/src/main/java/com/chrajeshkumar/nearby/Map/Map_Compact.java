package com.chrajeshkumar.nearby.Map;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.util.List;
import java.util.Locale;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 * Created by ChRajeshKumar on 30-Jan-17.
 */

public class Map_Compact {
    Context context;
    public Map_Compact(Context context){
        this.context = context;
    }



 /*   public double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }*/

    public double getDistence(double latA, double latB, double lngA , double lngB)
    {
        double dlon,dlat;
        dlon = lngB - lngA;
        dlat = latB - latA;
        double a = pow(sin(dlat / 2),2) + (cos(latA) * cos(latB) *pow(sin(dlon / 2),2));
        double c = 2 * atan2( sqrt(a), sqrt(1-a) );
       double d = 6373.0 * c; //(where R is the radius of the Earth)
       return d;
    }

    public static float distance(float lat1, float lng1, float lat2, float lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        return dist;
    }

    public static String distance(double lat1, double lat2, double lon1,double lon2) {
        double theta = lon1 - lon2;
        double dist = sin(deg2rad(lat1))  * sin(deg2rad(lat2)) + cos(deg2rad(lat1)) * cos(deg2rad(lat2)) * cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        double value;
        String distence;
        if(dist>1){
            value = dist;
            distence = (int)value+" KM";
        }else{
            value =dist*1000;
            distence = (int)value+" mtrs";
        }
        return distence;
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }


    public String getCurrentCityName(double MyLat, double MyLong) {
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
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
