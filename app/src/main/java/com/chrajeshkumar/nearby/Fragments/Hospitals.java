package com.chrajeshkumar.nearby.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.chrajeshkumar.nearby.Activities.DashBoard;
import com.chrajeshkumar.nearby.Helper.Api_interface;
import com.chrajeshkumar.nearby.Helper.GetClass_name;
import com.chrajeshkumar.nearby.Helper.GetLat_Longs;
import com.chrajeshkumar.nearby.Helper.Recalling;
import com.chrajeshkumar.nearby.Network.Api_CallBack;
import com.chrajeshkumar.nearby.Pojo.Root;
import com.chrajeshkumar.nearby.R;
import com.chrajeshkumar.nearby.adapters.Dashboard_Adapter;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by ChRajeshKumar on 26-Jan-17.
 */

public class Hospitals extends Fragment implements Api_interface,GetClass_name, Response.Listener,
        Response.ErrorListener,Recalling {

    RecyclerView recycler_view;
    View view;
    public Context context;
    LinearLayoutManager mLayoutManager;
    Gson gson = new Gson();
    Root root;
    GetLat_Longs getLat_longs;
    Class from_class;
    boolean setone = true;
    private RequestQueue mQueue;
    static boolean initial_is=true;
    TextView txt_notfound;
    public static final String REQUEST_TAG = "Hospitals";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getBaseContext();
        view =  inflater.inflate(R.layout.tab_inflated_view, container, false);
        recycler_view = (RecyclerView)view .findViewById(R.id.recycler_view);
        txt_notfound = (TextView)view.findViewById(R.id.txt_notfound);
        recycler_view.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        from_class = Hospitals.class;
//        Volley_service();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        getLat_longs = (GetLat_Longs)DashBoard.activity;
        from_class = Hospitals.class;
        if(isVisibleToUser&&setone){
            setone = false;
            initial_is = false;
           new Api_CallBack(Hospitals.this,getLat_longs.getLatitude(),getLat_longs.getLongitude(),"hospital");

        }
    }

    @Override
    public void resultentApi(String result) {
        Log.e(" result is "," resultent api is in fragement"+result);

        try{
            JSONObject jsonObject = new JSONObject((result).toString());
//            status
            if(jsonObject.getString("status").equals("OK")) {
                txt_notfound.setVisibility(View.GONE);
                recycler_view.setVisibility(View.VISIBLE);
                root = gson.fromJson(result, Root.class);
                recycler_view.setAdapter(new Dashboard_Adapter(context, root));
            }else{
                txt_notfound.setVisibility(View.VISIBLE);
                recycler_view.setVisibility(View.GONE);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Activity getactiviy_ref() {
        return (Activity)context;
    }

    @Override
    public Class fromActivity() {
        return from_class;
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("error is ","<><>error"+error.toString());
    }

    @Override
    public void onResponse(Object response) {

        try {
           Log.e("response is ","<>in fragment<>"+(response).toString());
                resultentApi((response).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mQueue != null) {
            mQueue.cancelAll(REQUEST_TAG);
        }
    }

    @Override
    public void reCall_network_callback() {
        Log.e("coming to interface data is ","<<>><>");
//        if(initial_is)
        new Api_CallBack(Hospitals.this,getLat_longs.getLatitude(),getLat_longs.getLongitude(),"hospital");
    }
}
