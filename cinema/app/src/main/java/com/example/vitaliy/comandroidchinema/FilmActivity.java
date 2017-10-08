package com.example.vitaliy.comandroidchinema;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vitaliy.comandroidchinema.Data.Helpers.Adapters.FilmArrayAdapter;
import com.example.vitaliy.comandroidchinema.Data.Helpers.Components.CustomDialog;
import com.example.vitaliy.comandroidchinema.Data.Helpers.ConfigData;
import com.example.vitaliy.comandroidchinema.Data.Helpers.Interface.LoaderInterface;
import com.example.vitaliy.comandroidchinema.Data.Helpers.Loaders.FilmLoader;
import com.example.vitaliy.comandroidchinema.Data.Helpers.Parsers.Parser;
import com.example.vitaliy.comandroidchinema.Data.Models.Film;
import com.example.vitaliy.comandroidchinema.Data.Models.Hall;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class FilmActivity extends AppCompatActivity implements LoaderInterface{

    public static final String JSON_OBJ_LIST_FILMS = "JSON_OBJ_LIST_FILMS";

    private ListView listView;
    private ListView leftDrawer;
    private Hall hall = null;
    private List<Film> filmList = null;
    private List<Hall> hallList = null;
    private static SharedPreferences sharedPreferences;

    final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(ConfigData.spDataConfig,MODE_PRIVATE);
        String jsonObj = sharedPreferences.getString(ConfigData.objHall,"");

        listView = (ListView)findViewById(R.id.listFilmArray);
        leftDrawer = (ListView)findViewById(R.id.left_drawer);

        hall = gson.fromJson(jsonObj,Hall.class);
        setTitle(hall.getName());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Film film = filmList.get(position);
                String s = gson.toJson(film);
                sharedPreferences
                        .edit()
                        .putString(ConfigData.objFilm,s)
                        .commit();

                startActivity(new Intent(view.getContext(),ViewActivity.class));
            }
        });

        leftDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Hall hall = hallList.get(position);

                Gson gson = new Gson();
                String jsonText = gson.toJson(hall);
                sharedPreferences
                        .edit()
                        .putString(ConfigData.objHall,jsonText)
                        .commit();

                startActivity(new Intent(view.getContext(),FilmActivity.class));
            }
        });

        String s = sharedPreferences.getString(ConfigData.listHalls,"");
        if (s.compareToIgnoreCase("") == 0){
            finish();
        }
        Type type = new TypeToken<List<Hall>>(){}.getType();
        hallList =gson.fromJson(s,type);

        List<String> hallNames = new ArrayList<>();
        for (Hall hall:hallList) {
            hallNames.add(hall.getName());
        }
        ArrayAdapter<String> hallsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,hallNames);
        leftDrawer.setAdapter(hallsAdapter);
        if (savedInstanceState == null) {
            new FilmLoader(this, hall.getId()).execute();
        }else{
            if (savedInstanceState.containsKey(JSON_OBJ_LIST_FILMS)){
                Type type1 = new TypeToken<List<Film>>(){}.getType();
                filmList = gson.fromJson(savedInstanceState.getString(JSON_OBJ_LIST_FILMS),type1);
                updateFilmsList();
            }else{
                new FilmLoader(this, hall.getId()).execute();
            }
        }
    }

    private void updateFilmsList (){

        FilmArrayAdapter filmsAdapter = new FilmArrayAdapter(this, android.R.layout.simple_list_item_1,filmList);
        listView.setAdapter(filmsAdapter);
        Gson gson = new Gson();
        sharedPreferences.edit()
                .putString(ConfigData.listFilms,gson.toJson(filmList))
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void result(Response response) throws IOException, JSONException {
        try {
            String s = response.body().string();
            JSONObject jsonRoot = new JSONObject(s);
            if (jsonRoot.getInt("error") != 0) {
                Toast.makeText(this, jsonRoot.getString("date"), Toast.LENGTH_LONG).show();
            } else {
                filmList = Parser.getListFilm(jsonRoot);
                sharedPreferences.edit()
                        .putString(ConfigData.listFilms, gson.toJson(filmList))
                        .commit();
                updateFilmsList();
            }
        }catch (Exception error){
            CustomDialog.CreateDialog(this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(JSON_OBJ_LIST_FILMS,gson.toJson(filmList));
    }
}
