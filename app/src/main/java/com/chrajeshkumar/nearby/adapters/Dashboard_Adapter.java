package com.chrajeshkumar.nearby.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chrajeshkumar.nearby.MapView;
import com.chrajeshkumar.nearby.Pojo.Root;
import com.chrajeshkumar.nearby.R;

/**
 * Created by ChRajeshKumar on 8/31/2016.
 */
public class Dashboard_Adapter extends RecyclerView.Adapter<Dashboard_Adapter.ViewHolder>  {
     Context mContext;
    int error;
    View view;
    ViewHolder holder;
    Root root;
    public Dashboard_Adapter(Context c, Root root) {
        mContext = c;
        this.root = root;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_item_view, parent, false);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txt_titles.setText(root.getResults().get(position).getName());
        holder.txt_address_value.setText(root.getResults().get(position).getVicinity());

        holder.img_map_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("lat longs is ","<><<>lat is "+root.getResults().get(position).getGeometry().getLocation().getLat()+" long is "+root.getResults().get(position).getGeometry().getLocation().getLng());

                Bundle bundle = new Bundle();
                bundle.putString("lat",String.valueOf(root.getResults().get(position).getGeometry().getLocation().getLat()));
                bundle.putString("long",String.valueOf(root.getResults().get(position).getGeometry().getLocation().getLng()));
                bundle.putString("location_name",root.getResults().get(position).getName());
                Intent intent = new Intent(mContext, MapView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Location_info",bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return root.getResults().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_titles,txt_address_value;
        ImageView img_map_icon;
        public ViewHolder(View convertView) {
            super(convertView);
            txt_titles = (TextView) convertView.findViewById(R.id.txt_titles);
            txt_address_value = (TextView) convertView.findViewById(R.id.txt_address_value);
            img_map_icon = (ImageView) convertView.findViewById(R.id.img_map_icon);
        }

    }

}
