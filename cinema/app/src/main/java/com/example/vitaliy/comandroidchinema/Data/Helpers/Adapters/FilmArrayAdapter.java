package com.example.vitaliy.comandroidchinema.Data.Helpers.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vitaliy.comandroidchinema.Data.Models.Film;
import com.example.vitaliy.comandroidchinema.Data.Models.Hall;
import com.example.vitaliy.comandroidchinema.R;

import java.util.List;

/**
 * Created by vitaliy on 11.06.2016.
 */
public class FilmArrayAdapter extends ArrayAdapter<Film> {
    private List<Film> filmList;
    private Context context;

    public FilmArrayAdapter(Context context, int resource, List<Film> objects) {
        super(context, resource, objects);
        this.filmList = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_hall,parent,false);
        Film film = filmList.get(position);

        TextView tvName = (TextView) view.findViewById(R.id.hallName);
        TextView tvCount = (TextView) view.findViewById(R.id.hallCount);
        tvName.setText(film.getName());
        tvCount.setText(context.getString(R.string.dateFilm)+String.valueOf(film.getDate()));

        return view;
    }
}
