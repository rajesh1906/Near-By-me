package com.chrajeshkumar.nearby.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chrajeshkumar.nearby.R;

/**
 * Created by ChRajeshKumar on 27-Jan-17.
 */

public class More_Adapter extends BaseAdapter {
    Context mContext;
    String[] list_item_values;
    TypedArray list_icons;

    public More_Adapter(Context mContext, String[] list_item_values, TypedArray list_icons) {
        this.mContext = mContext;
        this.list_item_values = list_item_values;
        this.list_icons = list_icons;
    }

    @Override
    public int getCount() {
        return list_item_values.length;
    }

    @Override
    public Object getItem(int position) {
        return list_item_values;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.menu_item, null);
            TextView txt_titles = (TextView) grid.findViewById(R.id.txt_title);
            ImageView menu_img = (ImageView) grid.findViewById(R.id.menu_img);
            txt_titles.setText(list_item_values[position]);
            menu_img.setImageResource(list_icons.getResourceId(position, -1));
        } else {
            grid = (View) convertView;
        }

        return grid;
    }

}
