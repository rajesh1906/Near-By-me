package com.chrajeshkumar.nearby.Network;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.chrajeshkumar.nearby.Activities.DashBoard;
import com.chrajeshkumar.nearby.Fragments.Banks;
import com.chrajeshkumar.nearby.Fragments.Hospitals;
import com.chrajeshkumar.nearby.Fragments.More;
import com.chrajeshkumar.nearby.Fragments.Restaurants;
import com.chrajeshkumar.nearby.Fragments.Schools;
import com.chrajeshkumar.nearby.Helper.Api_interface;
import com.chrajeshkumar.nearby.Utils.EndPoints;
import com.chrajeshkumar.nearby.Utils.UrltoValue;

import java.net.URL;

/**
 * Created by ChRajeshKumar on 26-Jan-17.
 */

public class Network_callback extends AsyncTask<URL, Integer, Long>{
    String response,for_search;
    Hospitals hospitals;
    Api_interface api_interface;
    ProgressDialog progressDialog;
    String latitude,longitude;
    Schools schools;
    Restaurants restaurants;
    Banks banks;
    More more;

    public Network_callback(Hospitals hospitals,String latitude,String longitude,String for_search){
        this.hospitals = hospitals;
        this.api_interface = hospitals;
        this.latitude = latitude;
        this.longitude = longitude;
        this.for_search = for_search;

    }
    public Network_callback( Schools schools,String latitude,String longitude,String for_search){
        this.schools = schools;
        this.api_interface = schools;
        this.latitude = latitude;
        this.longitude = longitude;
        this.for_search = for_search;

    }
    public Network_callback( Restaurants restaurants,String latitude,String longitude,String for_search){
        this.restaurants = restaurants;
        this.api_interface = restaurants;
        this.latitude = latitude;
        this.longitude = longitude;
        this.for_search = for_search;

    }
    public Network_callback(Banks banks, String latitude, String longitude, String for_search){
        this.banks = banks;
        this.api_interface = banks;
        this.latitude = latitude;
        this.longitude = longitude;
        this.for_search = for_search;

    }
    public Network_callback(More more, String latitude, String longitude, String for_search){
        this.more = more;
        this.api_interface = banks;
        this.latitude = latitude;
        this.longitude = longitude;
        this.for_search = for_search;

    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(DashBoard.activity);
        progressDialog.setMessage("Fetching data.......");
        progressDialog.show();
        progressDialog.setCancelable(false);

    }

    @Override
    protected Long doInBackground(URL... params) {
        try {
            response = UrltoValue.getValuefromUrl(EndPoints.getSearching(latitude,longitude,for_search));
            Log.e("request string is ","<><"+EndPoints.getSearching(latitude,longitude,for_search));
            Log.e("response is ","<><"+response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
        if (null != progressDialog) {
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
//        Log.e("response is ","<><><response is "+response);
        api_interface.resultentApi(response);
    }
}
