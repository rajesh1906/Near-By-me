package com.chrajeshkumar.nearby.Utils;

import android.content.Context;

import com.chrajeshkumar.nearby.Activities.DashBoard;
import com.chrajeshkumar.nearby.Activities.More_detail;
import com.chrajeshkumar.nearby.Helper.GetLat_Longs;
import com.chrajeshkumar.nearby.Helper.Getting_Lat_Instance;

/**
 * Created by ChRajeshKumar on 26-Jan-17.
 */

public class EndPoints {

    public static String searching = "";
    static int distance_local;
    public static Getting_Lat_Instance getting_lat_instance;
    public static GetLat_Longs getLat_longs;
    public static String getSearching(String latitude,String longitude,String type,String  coming_from) {
        if(coming_from.equals("DashBoard")) {
            if (DashBoard.distance != 500) {
                distance_local = DashBoard.distance * 1000;
            } else {
                distance_local = DashBoard.distance;
            }
//            getting_lat_instance = (Getting_Lat_Instance)DashBoard.activity;
//            getLat_longs = (GetLat_Longs)DashBoard.activity;
//            getting_lat_instance.getLat_Instance();
        }else{
            if (More_detail.distance != 500) {
                distance_local = More_detail.distance * 1000;
            } else {
                distance_local = More_detail.distance;
            }
        }
        /*if(latitude.equals("0.0")){
            searching = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+getLat_longs.getLatitude()+","+getLat_longs.getLongitude()+"&radius="+distance_local+"&types="+type+"&name="+type+"&sensor=false&key=AIzaSyCrPjCqDlVZxf3nIznYNizLDFeSx-AYDk4";
        }else{*/
            searching = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latitude+","+longitude+"&radius="+distance_local+"&types="+type.replace(" ","%20")+"&name="+type.replace(" ","%20")+"&sensor=false&key=AIzaSyCrPjCqDlVZxf3nIznYNizLDFeSx-AYDk4";
//        }

        return searching;

    }
}
