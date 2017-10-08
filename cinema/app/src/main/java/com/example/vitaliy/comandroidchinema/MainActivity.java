package com.example.vitaliy.comandroidchinema;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vitaliy.comandroidchinema.Data.Helpers.Adapters.HallArrayAdapter;
import com.example.vitaliy.comandroidchinema.Data.Helpers.Adapters.MenuArrayAdapter;
import com.example.vitaliy.comandroidchinema.Data.Helpers.Components.CustomDialog;
import com.example.vitaliy.comandroidchinema.Data.Helpers.ConfigData;
import com.example.vitaliy.comandroidchinema.Data.Helpers.DataURL;
import com.example.vitaliy.comandroidchinema.Data.Helpers.Interface.LoaderInterface;
import com.example.vitaliy.comandroidchinema.Data.Helpers.Loaders.FilmLoader;
import com.example.vitaliy.comandroidchinema.Data.Helpers.Loaders.HallLoader;
import com.example.vitaliy.comandroidchinema.Data.Helpers.Parsers.Parser;
import com.example.vitaliy.comandroidchinema.Data.Models.Film;
import com.example.vitaliy.comandroidchinema.Data.Models.Hall;
import com.example.vitaliy.comandroidchinema.Data.Models.Menu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements LoaderInterface {

    private static final String JSON_OBJ_LIST_HALLS = "JSON_OBJ_LIST_HALLS";

    private ListView listView;
    private ListView menuView;
    private List<Menu> menus;
    private List<Hall> halls = null;
    private static SharedPreferences sharedPreferences;

    final Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("List Halls");
        sharedPreferences = getSharedPreferences(ConfigData.spDataConfig,MODE_PRIVATE);

        listView = (ListView)findViewById(R.id.listHalls);
        menuView = (ListView)findViewById(R.id.left_drawer);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Hall hall = halls.get(position);
                Gson gson = new Gson();
                String jsonText = gson.toJson(hall);
                sharedPreferences
                        .edit()
                        .putString(ConfigData.objHall,jsonText)
                        .commit();

                startActivity(new Intent(view.getContext(),FilmActivity.class));
            }
        });
        menus = new ArrayList<>();
        final Menu menu = new Menu("Web", "openWebSyite");
        menu.setIcon(android.R.drawable.ic_menu_view);
        menus.add(menu);

        Menu menu1 = new Menu("Info", "openInfoActivity");
        menu1.setIcon(android.R.drawable.ic_menu_help);
        menus.add(menu1);

        MenuArrayAdapter adapt = new MenuArrayAdapter(this,1,menus);

        menuView.setAdapter(adapt);
        menuView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Menu menu2 = menus.get(position);
                if (menu2.getOption().compareToIgnoreCase("openWebSyite") == 0){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(DataURL.openweb()));
                    startActivity(browserIntent);
                }else{
                    startActivity(new Intent(view.getContext(),InfoActivity.class));
                }
            }
        });
        if (savedInstanceState == null) {
            new HallLoader((LoaderInterface)this).execute();
        }else{
            if (savedInstanceState.containsKey(JSON_OBJ_LIST_HALLS)){
                Type type1 = new TypeToken<List<Hall>>(){}.getType();
                halls = gson.fromJson(savedInstanceState.getString(JSON_OBJ_LIST_HALLS),type1);
                updateHallList();
            }else{
                new HallLoader((LoaderInterface)this).execute();
            }
        }
    }

    private void updateHallList (){
        //ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,hallNames);
        HallArrayAdapter adapt = new HallArrayAdapter(this,android.R.layout.simple_list_item_1,halls);
        listView.setAdapter(adapt);
    }

    @Override
    public void result(Response response) throws IOException, JSONException {
        try {
            String s = response.body().string();
            JSONObject jsonRoot = new JSONObject(s);
            if (jsonRoot.getInt("error") != 0) {
                Toast.makeText(this, jsonRoot.getString("date"), Toast.LENGTH_LONG).show();
            } else {
                halls = Parser.getListHall(jsonRoot);
                sharedPreferences.edit()
                        .putString(ConfigData.listHalls, gson.toJson(halls))
                        .commit();
                updateHallList();
            }
        }catch (Exception e){
            CustomDialog.CreateDialog(this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(JSON_OBJ_LIST_HALLS,gson.toJson(halls));
    }
}
