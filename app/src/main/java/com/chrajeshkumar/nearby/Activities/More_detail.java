package com.chrajeshkumar.nearby.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import com.chrajeshkumar.nearby.Pojo.Root;
import com.chrajeshkumar.nearby.R;
import com.chrajeshkumar.nearby.adapters.Dashboard_Adapter;
import com.google.gson.Gson;

/**
 * Created by ChRajeshKumar on 31-Jan-17.
 */

public class More_detail extends AppCompatActivity
{
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    Root root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_detail);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        Gson gson = new Gson();
        Bundle extras = getIntent().getExtras();
        root = gson.fromJson(extras.getString("root_pojo"), Root.class);
        recyclerView.setAdapter(new Dashboard_Adapter(this,root));
    }


}
