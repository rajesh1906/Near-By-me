package com.chrajeshkumar.nearby.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chrajeshkumar.nearby.Fragments.More;
import com.chrajeshkumar.nearby.Helper.Api_interface;
import com.chrajeshkumar.nearby.Helper.GetLat_Longs;
import com.chrajeshkumar.nearby.Network.Api_CallBack;
import com.chrajeshkumar.nearby.Pojo.Root;
import com.chrajeshkumar.nearby.R;
import com.chrajeshkumar.nearby.Utils.CheckNetwork;
import com.chrajeshkumar.nearby.adapters.Dashboard_Adapter;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by ChRajeshKumar on 31-Jan-17.
 */

public class More_detail extends AppCompatActivity implements Api_interface {
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    Root root;
    ImageView img_back;
    Spinner spinner;
    String[] range;
    TextView txt_header;
    GetLat_Longs getLat_longs;
    String category;
    Gson gson = new Gson();
    public static int distance = 500;
    TextView txt_notfound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_detail);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        img_back = (ImageView) findViewById(R.id.img_back);
        txt_header = (TextView) findViewById(R.id.txt_header);
        txt_notfound =(TextView) findViewById(R.id.txt_notfound);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        spinner = (Spinner) findViewById(R.id.spinner);
        recyclerView.setLayoutManager(mLayoutManager);

        Bundle extras = getIntent().getExtras();
        root = gson.fromJson(extras.getString("root_pojo"), Root.class);
        recyclerView.setAdapter(new Dashboard_Adapter(this, root));
        getLat_longs = (GetLat_Longs)DashBoard.activity;
        category = extras.getString("title");
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        range = getResources().getStringArray(R.array.range);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner,R.id.txt_item, range);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.custom_spinner);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                distance = Integer.parseInt(range[position].replaceAll("[\\D]", ""));

                if(CheckNetwork.isOnline(More_detail.this)) {
                    new Api_CallBack(More_detail.this, getLat_longs.getLatitude(), getLat_longs.getLongitude(), category);
                }else{
                    Toast.makeText(More_detail.this,"Please check your internet connection",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        txt_header.setText(category+" NearBy Me");
    }


    @Override
    public void resultentApi(String result) {
        try{
            JSONObject jsonObject =new JSONObject(result);
            if(jsonObject.getString("status").equals("OK")) {
                recyclerView.setVisibility(View.VISIBLE);
                txt_notfound.setVisibility(View.GONE);
                root = gson.fromJson(result, Root.class);
                recyclerView.setAdapter(new Dashboard_Adapter(this, root));
            }else{
                recyclerView.setVisibility(View.GONE);
                txt_notfound.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public Activity getactiviy_ref() {
        return More_detail.this;
    }
}
