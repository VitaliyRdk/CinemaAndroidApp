package com.example.vitaliy.comandroidchinema.Data.Helpers.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vitaliy.comandroidchinema.Data.Models.Hall;
import com.example.vitaliy.comandroidchinema.R;

import java.util.List;

/**
 * Created by vitaliy on 11.06.2016.
 */
public class HallArrayAdapter extends ArrayAdapter <Hall> {
    private List<Hall> hallList;
    private Context context;

    public HallArrayAdapter(Context context, int resource, List<Hall> objects) {
        super(context, resource, objects);
        this.hallList = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_hall,parent,false);
        Hall hall = hallList.get(position);

        TextView tvName = (TextView) view.findViewById(R.id.hallName);
        TextView tvCount = (TextView) view.findViewById(R.id.hallCount);
        tvName.setText(hall.getName());
        tvCount.setText(context.getString(R.string.countFilms)+String.valueOf(hall.getCount()));

        return view;
    }
}
