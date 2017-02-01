package com.chrajeshkumar.nearby.adapters;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chrajeshkumar.nearby.Fragments.Banks;
import com.chrajeshkumar.nearby.Fragments.Hospitals;
import com.chrajeshkumar.nearby.Fragments.More;
import com.chrajeshkumar.nearby.Fragments.Restaurants;
import com.chrajeshkumar.nearby.Fragments.Schools;
import com.chrajeshkumar.nearby.Helper.Recalling;
import com.chrajeshkumar.nearby.R;

/**
 * Created by ChRajeshKumar on 26-Jan-17.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private Context context;
    Hospitals tab1;
    Schools tab2;
    Restaurants tab3;
    Banks tab4;
    More tab5;
    Recalling recalling;
    public PagerAdapter(FragmentManager fm, int NumOfTabs,Context context) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                 tab1 = new Hospitals();
                return tab1;
            case 1:
                 tab2 = new Schools();
                return tab2;
            case 2:
                 tab3 = new Restaurants();
                return tab3;
            case 3:
                 tab4 = new Banks();
                return tab4;
            case 4:
                 tab5 = new More();
                return tab5;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    public View getTabView(int position,String[] tabTitles,TypedArray tabIcons) {
        View v = LayoutInflater.from(context).inflate(R.layout.custome_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.txt_tab);
        tv.setText(tabTitles[position]);
        ImageView imageView=(ImageView) v.findViewById(R.id.img_tab);
        imageView.setImageResource(tabIcons.getResourceId(position,-1));

        return v;
    }

    public void Calling_Fregment(int position){


        switch (position){
            case 0:
                 recalling = tab1;
                break;
            case 1:
                 recalling = tab2;
                break;
            case 2:
                recalling = tab3;
                break;
            case 3:
                recalling = tab4;
                break;
            case 4:
                recalling = tab5;
                break;
        }
        recalling.reCall_network_callback();
    }
}
