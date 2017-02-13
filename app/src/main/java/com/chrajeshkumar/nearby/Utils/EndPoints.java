package com.chrajeshkumar.nearby.Utils;

import android.content.Context;

import com.chrajeshkumar.nearby.Activities.DashBoard;
import com.chrajeshkumar.nearby.Activities.More_detail;

/**
 * Created by ChRajeshKumar on 26-Jan-17.
 */

public class EndPoints {

    public static String searching = "";
    static int distance_local;
    public static String getSearching(String latitude,String longitude,String type,String  coming_from) {
        if(coming_from.equals("DashBoard")) {
            if (DashBoard.distance != 500) {
                distance_local = DashBoard.distance * 1000;
            } else {
                distance_local = DashBoard.distance;
            }
        }else{
            if (More_detail.distance != 500) {
                distance_local = More_detail.distance * 1000;
            } else {
                distance_local = More_detail.distance;
            }
        }
            searching = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latitude+","+longitude+"&radius="+distance_local+"&types="+type+"&name="+type+"&sensor=false&key=AIzaSyCrPjCqDlVZxf3nIznYNizLDFeSx-AYDk4";
        return searching;

    }
}
