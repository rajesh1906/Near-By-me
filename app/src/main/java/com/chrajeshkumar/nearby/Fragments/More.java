package com.chrajeshkumar.nearby.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.chrajeshkumar.nearby.Activities.DashBoard;
import com.chrajeshkumar.nearby.Activities.More_detail;
import com.chrajeshkumar.nearby.Helper.Api_interface;
import com.chrajeshkumar.nearby.Helper.CustomeGridview;
import com.chrajeshkumar.nearby.Helper.GetClass_name;
import com.chrajeshkumar.nearby.Helper.GetLat_Longs;
import com.chrajeshkumar.nearby.Helper.Recalling;
import com.chrajeshkumar.nearby.Network.Api_CallBack;
import com.chrajeshkumar.nearby.Pojo.Root;
import com.chrajeshkumar.nearby.R;
import com.chrajeshkumar.nearby.adapters.More_Adapter;
import com.google.gson.Gson;

/**
 * Created by ChRajeshKumar on 26-Jan-17.
 */

public class More extends Fragment implements Api_interface,GetClass_name,Recalling {
    View view;
    public Context context;
    Gson gson = new Gson();
    Root root;
    Class from_class;
    boolean setone = true;
    Dialog dialog;
    CustomeGridview gridview;
    GetLat_Longs getLat_longs;
    String[] more_itmes;
    RecyclerView recycler_view;
    LinearLayoutManager mLayoutManager;
    CardView card_view;
    String selected_category;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getBaseContext();
        view =  inflater.inflate(R.layout.dashboard, container, false);
        gridview = (CustomeGridview)view.findViewById(R.id.gridview);
        recycler_view = (RecyclerView)view.findViewById(R.id.recycler_view);
        card_view = (CardView)view.findViewById(R.id.card_view);
        more_itmes = context.getResources().getStringArray(R.array.more_items);
        TypedArray more_icons = context.getResources().obtainTypedArray(R.array.more_icons);
        gridview.setAdapter(new More_Adapter(context,more_itmes,more_icons));
        getLat_longs = (GetLat_Longs)DashBoard.activity;
        recycler_view.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected_category = more_itmes[position];
                new Api_CallBack(More.this,getLat_longs.getLatitude(),getLat_longs.getLongitude(),more_itmes[position]);
            }
        });
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

        Log.e(" result is "," resultent api is in fragement"+result);
//        recycler_view.setVisibility(View.VISIBLE);
//        card_view.setVisibility(View.GONE);
        try{
            /*root = gson.fromJson(result,Root.class);
            recycler_view.setAdapter(new Dashboard_Adapter(context,root));*/
            root = gson.fromJson(result,Root.class);
            Intent intent = new Intent(context, More_detail.class);
            intent.putExtra("root_pojo", gson.toJson(root));
            intent.putExtra("title",selected_category);
            startActivity(intent);

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
    public void reCall_network_callback() {

    }
}
