package com.chrajeshkumar.nearby.Network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.chrajeshkumar.nearby.Activities.DashBoard;
import com.chrajeshkumar.nearby.Fragments.Banks;
import com.chrajeshkumar.nearby.Fragments.Hospitals;
import com.chrajeshkumar.nearby.Fragments.More;
import com.chrajeshkumar.nearby.Fragments.Restaurants;
import com.chrajeshkumar.nearby.Fragments.Schools;
import com.chrajeshkumar.nearby.Helper.Api_interface;
import com.chrajeshkumar.nearby.Network.JSON.CustomJSONObjectRequest;
import com.chrajeshkumar.nearby.Network.JSON.CustomVolleyRequestQueue;
import com.chrajeshkumar.nearby.Utils.EndPoints;

import org.json.JSONObject;

/**
 * Created by ChRajeshKumar on 30-Jan-17.
 */

public class Api_CallBack implements Response.Listener,
        Response.ErrorListener {
    String response,for_search;
    Hospitals hospitals;
    Api_interface api_interface;
    ProgressDialog progressDialog;
    String latitude,longitude;
    Schools schools;
    Restaurants restaurants;
    Banks banks;
    More more;
    private RequestQueue mQueue;
    Context context;
    Activity activity;
    String coming_from;
    public Api_CallBack(Hospitals hospitals,String latitude,String longitude,String for_search){
        this.hospitals = hospitals;
        this.api_interface = hospitals;
        this.latitude = latitude;
        this.longitude = longitude;
        this.for_search = for_search;
        activity = DashBoard.activity;
        coming_from = "DashBoard";
        Volley_service();


    }
    public Api_CallBack( Schools schools,String latitude,String longitude,String for_search){
        this.schools = schools;
        this.api_interface = schools;
        this.latitude = latitude;
        this.longitude = longitude;
        this.for_search = for_search;
        activity = DashBoard.activity;
        coming_from = "DashBoard";
        Volley_service();
    }
    public Api_CallBack( Restaurants restaurants,String latitude,String longitude,String for_search){
        this.restaurants = restaurants;
        this.api_interface = restaurants;
        this.latitude = latitude;
        this.longitude = longitude;
        this.for_search = for_search;
        activity = DashBoard.activity;
        coming_from = "DashBoard";
        Volley_service();

    }
    public Api_CallBack(Banks banks, String latitude, String longitude, String for_search){
        this.banks = banks;
        this.api_interface = banks;
        this.latitude = latitude;
        this.longitude = longitude;
        this.for_search = for_search;
        activity = DashBoard.activity;
        coming_from = "DashBoard";
        Volley_service();

    }
    public Api_CallBack(More more, String latitude, String longitude, String for_search){
        this.more = more;
        this.api_interface = more;
        this.latitude = latitude;
        this.longitude = longitude;
        this.for_search = for_search;
        activity = DashBoard.activity;
        coming_from = "DashBoard";
        Volley_service();

    }
    public Api_CallBack(Context context, String latitude, String longitude, String for_search){
        this.context = context;
        this.api_interface = (Api_interface)context;
        this.latitude = latitude;
        this.longitude = longitude;
        this.for_search = for_search;
        activity = (Activity)context;
        coming_from = "More_datail";
        Volley_service();

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("error is ","<><>error"+error.toString());
    }

    @Override
    public void onResponse(Object response) {

        try {
            if (null != progressDialog) {
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
            Log.e("response is ","<>in fragment<>"+(response).toString());
//            resultentApi((response).toString());
            api_interface.resultentApi(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Volley_service(){
        // Instantiate the RequestQueue.
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Fetching "+for_search+"....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        mQueue = CustomVolleyRequestQueue.getInstance(activity)
                .getRequestQueue();
        String url = EndPoints.getSearching(latitude,longitude,for_search,coming_from);
        final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method
                .GET, url,
                new JSONObject(), this, this);
        mQueue.add(jsonRequest);
    }

}
