package com.example.vitaliy.comandroidchinema.Data.Helpers.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vitaliy.comandroidchinema.Data.Models.Film;
import com.example.vitaliy.comandroidchinema.Data.Models.Menu;
import com.example.vitaliy.comandroidchinema.R;

import java.util.List;

/**
 * Created by vitaliy on 11.06.2016.
 */
public class MenuArrayAdapter  extends ArrayAdapter <Menu> {
    private Context context;
    private List<Menu> menus;

    public MenuArrayAdapter(Context context,int resource,List<Menu> menus) {
        super(context, resource, menus);
        this.context = context;
        this.menus = menus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_menu,parent,false);
        Menu manu = menus.get(position);
        ImageView icon = (ImageView)view.findViewById(R.id.manu_icon);
        TextView name = (TextView)view.findViewById(R.id.name_Menu);
        icon.setImageResource(manu.getIcon());
        name.setText(manu.getName());

        return view;
    }
}
