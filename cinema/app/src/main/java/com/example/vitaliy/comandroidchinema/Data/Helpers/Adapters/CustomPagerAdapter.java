package com.example.vitaliy.comandroidchinema.Data.Helpers.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.vitaliy.comandroidchinema.Data.Helpers.Components.CustomDialog;
import com.example.vitaliy.comandroidchinema.Data.Models.Film;
import com.example.vitaliy.comandroidchinema.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vitaliy on 11.06.2016.
 */
public class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    List<String> photos;

    public CustomPagerAdapter(Context context, List<String> photos) {
        mContext = context;
        this.photos = photos;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        try{
            Picasso.with(mContext).load(photos.get(position)).into(imageView);
        }catch (Exception error){
            Toast.makeText(mContext,"Error",Toast.LENGTH_LONG).show();
        }


        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}