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

import com.chrajeshkumar.nearby.Activities.DashBoard;
import com.chrajeshkumar.nearby.Helper.Api_interface;
import com.chrajeshkumar.nearby.Helper.GetLat_Longs;
import com.chrajeshkumar.nearby.Network.Network_callback;
import com.chrajeshkumar.nearby.Pojo.Root;
import com.chrajeshkumar.nearby.R;
import com.chrajeshkumar.nearby.adapters.Dashboard_Adapter;
import com.google.gson.Gson;

/**
 * Created by ChRajeshKumar on 26-Jan-17.
 */

public class Banks extends Fragment implements Api_interface {
    RecyclerView recycler_view;
    View view;
    public Context context;
    LinearLayoutManager mLayoutManager;
    Gson gson = new Gson();
    Root root;
    GetLat_Longs getLat_longs;
    boolean setone = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getBaseContext();
        view =  inflater.inflate(R.layout.tab_inflated_view, container, false);
        recycler_view = (RecyclerView)view .findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        getLat_longs = (GetLat_Longs) DashBoard.activity;
        if(isVisibleToUser&&setone){
            setone = false;
            new Network_callback(Banks.this,getLat_longs.getLatitude(),getLat_longs.getLongitude(),"Banks").execute();
        }
    }

    @Override
    public void resultentApi(String result) {
        Log.e(" result is "," resultent api is in fragement"+result);
        try{
            root = gson.fromJson(result,Root.class);
            recycler_view.setAdapter(new Dashboard_Adapter(context,root));


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Activity getactiviy_ref() {
        return (Activity)context;
    }
}
