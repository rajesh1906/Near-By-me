package com.chrajeshkumar.nearby.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.chrajeshkumar.nearby.Activities.DashBoard;
import com.chrajeshkumar.nearby.Helper.Api_interface;
import com.chrajeshkumar.nearby.Helper.CustomeGridview;
import com.chrajeshkumar.nearby.Helper.GetClass_name;
import com.chrajeshkumar.nearby.Helper.GetLat_Longs;
import com.chrajeshkumar.nearby.Network.Network_callback;
import com.chrajeshkumar.nearby.Pojo.Root;
import com.chrajeshkumar.nearby.R;
import com.chrajeshkumar.nearby.adapters.Dashboard_Adapter;
import com.chrajeshkumar.nearby.adapters.More_Adapter;
import com.google.gson.Gson;

/**
 * Created by ChRajeshKumar on 26-Jan-17.
 */

public class More extends Fragment implements Api_interface,GetClass_name {
    View view;
    public Context context;
    Gson gson = new Gson();
    Root root;
    Class from_class;
    boolean setone = true;
    Dialog dialog;
    CustomeGridview gridview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getBaseContext();
        view =  inflater.inflate(R.layout.dashboard, container, false);
        gridview = (CustomeGridview)view.findViewById(R.id.gridview);
        String[] more_itmes = context.getResources().getStringArray(R.array.more_items);
        TypedArray more_icons = context.getResources().obtainTypedArray(R.array.more_icons);
        gridview.setAdapter(new More_Adapter(context,more_itmes,more_icons));
        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser&&setone){
            setone = false;
//            Show_channel_dialog();
//            new Network_callback(More.this,getLat_longs.getLatitude(),getLat_longs.getLongitude(),"hospital").execute();
        }
    }

    @Override
    public void resultentApi(String result) {
        Log.e(" result is "," resultent api is in fragement"+result);

    }

    @Override
    public Activity getactiviy_ref() {
        return (Activity)context;
    }

    @Override
    public Class fromActivity() {
        return from_class;
    }
}
