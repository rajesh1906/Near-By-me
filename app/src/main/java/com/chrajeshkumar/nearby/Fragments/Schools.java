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
import android.widget.Toast;

import com.chrajeshkumar.nearby.Activities.DashBoard;
import com.chrajeshkumar.nearby.Helper.Api_interface;
import com.chrajeshkumar.nearby.Helper.GetLat_Longs;
import com.chrajeshkumar.nearby.Helper.Recalling;
import com.chrajeshkumar.nearby.Network.Api_CallBack;
import com.chrajeshkumar.nearby.Pojo.Root;
import com.chrajeshkumar.nearby.R;
import com.chrajeshkumar.nearby.Utils.CheckNetwork;
import com.chrajeshkumar.nearby.adapters.Dashboard_Adapter;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by ChRajeshKumar on 26-Jan-17.
 */

public class Schools extends Fragment implements Api_interface, Recalling {
    RecyclerView recycler_view;
    View view;
    public Context context;
    LinearLayoutManager mLayoutManager;
    Gson gson = new Gson();
    Root root;
    GetLat_Longs getLat_longs;
    boolean setone = true;
    TextView txt_notfound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getBaseContext();
        view = inflater.inflate(R.layout.tab_inflated_view, container, false);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        txt_notfound = (TextView) view.findViewById(R.id.txt_notfound);
        recycler_view.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        getLat_longs = (GetLat_Longs) DashBoard.activity;
        if (isVisibleToUser && setone) {
            setone = false;
            if(CheckNetwork.isOnline(DashBoard.activity)) {
                new Api_CallBack(Schools.this, getLat_longs.getLatitude(), getLat_longs.getLongitude(), "school");
            }else{
                Toast.makeText(DashBoard.activity,"Please check your internet connection",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void resultentApi(String result) {
        Log.e(" result is ", " resultent api is in fragement" + result);
        try {
            JSONObject jsonObject = new JSONObject((result).toString());
//            status
            if (jsonObject.getString("status").equals("OK")) {
                txt_notfound.setVisibility(View.GONE);
                recycler_view.setVisibility(View.VISIBLE);
                root = gson.fromJson(result, Root.class);
                recycler_view.setAdapter(new Dashboard_Adapter(context, root));
            } else {
                txt_notfound.setVisibility(View.VISIBLE);
                recycler_view.setVisibility(View.GONE);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Activity getactiviy_ref() {
        return (Activity) context;
    }

    @Override
    public void reCall_network_callback() {
        if(CheckNetwork.isOnline(DashBoard.activity)) {
            new Api_CallBack(Schools.this, getLat_longs.getLatitude(), getLat_longs.getLongitude(), "school");
        }else{
            Toast.makeText(DashBoard.activity,"Please check your internet connection",Toast.LENGTH_LONG).show();
        }
    }
}
