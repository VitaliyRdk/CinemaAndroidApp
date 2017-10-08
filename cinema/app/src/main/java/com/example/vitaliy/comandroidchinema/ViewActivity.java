package com.example.vitaliy.comandroidchinema;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vitaliy.comandroidchinema.Data.Helpers.Adapters.CustomPagerAdapter;
import com.example.vitaliy.comandroidchinema.Data.Helpers.Components.CustomDialog;
import com.example.vitaliy.comandroidchinema.Data.Helpers.ConfigData;
import com.example.vitaliy.comandroidchinema.Data.Helpers.DataURL;
import com.example.vitaliy.comandroidchinema.Data.Helpers.Interface.LoaderInterface;
import com.example.vitaliy.comandroidchinema.Data.Helpers.Loaders.FilmDetLoader;
import com.example.vitaliy.comandroidchinema.Data.Helpers.Loaders.FilmLoader;
import com.example.vitaliy.comandroidchinema.Data.Helpers.Parsers.Parser;
import com.example.vitaliy.comandroidchinema.Data.Models.Film;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class ViewActivity extends AppCompatActivity implements LoaderInterface {

    private static final String JSON_OBJ_LIST_FILM = "JSON_OBJ_LIST_FILM";
    private static final String JSON_CustomPagerAdapter = "JSON_CustomPagerAdapter";

    private Film film = null;
    private ListView leftDrawer;
    private List<Film> films;
    private TextView nameFilm;
    private TextView description;
    private TextView ganre;
    private TextView durationView;
    private static SharedPreferences sharedPreferences;
    private ViewPager mViewPager;
    private Button openWeb;
    private List<String> photos = new ArrayList<>();
    private CustomPagerAdapter mCustomPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        nameFilm = (TextView) findViewById(R.id.nameFilm);
        description = (TextView) findViewById(R.id.description);
        ganre = (TextView) findViewById(R.id.ganre);
        durationView = (TextView) findViewById(R.id.durationView);
        openWeb = (Button) findViewById(R.id.openWeb);

        sharedPreferences = getSharedPreferences(ConfigData.spDataConfig,MODE_PRIVATE);
        leftDrawer = (ListView)findViewById(R.id.left_drawer);



        mViewPager = (ViewPager) findViewById(R.id.imgFilmBanerView);


        String jsonObj = sharedPreferences.getString(ConfigData.objFilm,"");
        String filmsJson = sharedPreferences.getString(ConfigData.listFilms,"");
        if (jsonObj.compareToIgnoreCase("") == 0 || filmsJson.compareToIgnoreCase("") == 0)
        {
            finish();
        }
        leftDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Film film = films.get(position);
                Gson gson = new Gson();
                String s = gson.toJson(film);
                sharedPreferences
                        .edit()
                        .putString(ConfigData.objFilm,s)
                        .commit();

                startActivity(new Intent(view.getContext(),ViewActivity.class));
            }
        });

        final Gson gson = new Gson();
        Type typeFilmList = new TypeToken<List<Film>>(){}.getType();
        film = gson.fromJson(jsonObj,Film.class);
        films = gson.fromJson(filmsJson,typeFilmList);

        if (savedInstanceState == null) {
            new FilmDetLoader((LoaderInterface)this,film.getId()).execute();
        }else{
            if (savedInstanceState.containsKey(JSON_OBJ_LIST_FILM)){
                film = gson.fromJson(savedInstanceState.getString(JSON_OBJ_LIST_FILM),Film.class);
                Type type = new TypeToken<List<String>>(){}.getType();
                photos = gson.fromJson(savedInstanceState.getString(JSON_CustomPagerAdapter),type);
                startRender();
            }else{
                new FilmDetLoader((LoaderInterface)this,film.getId()).execute();
            }
        }

        openWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(DataURL.opneFilmInfo(film.getId())));
                startActivity(browserIntent);
            }
        });
        updateFilmsList();
    }

    private void updateFilmsList (){
        List<String> filmNames = new ArrayList<>();
        for (Film film:films) {
            filmNames.add(film.getName());
        }

        ArrayAdapter<String> filmsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,filmNames);
        leftDrawer.setAdapter(filmsAdapter);
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

    private void render (){
        nameFilm.setText(film.getName());
        description.setText(film.getDescription());
        setTitle(film.getName());
        ganre.setText(this.getString(R.string.Genre) + " " + film.getGenre());
        durationView.setText(this.getString(R.string.DurationView) + " " + film.getDurationView());
    }

    @Override
    public void result(Response response) throws IOException, JSONException {
        try{
            String s = response.body().string();
            JSONObject jsonRoot = new JSONObject(s);
            if (jsonRoot.getInt("error") != 0){
                Toast.makeText(this,jsonRoot.getString("date"),Toast.LENGTH_LONG).show();
            }else {
                film = Parser.getFilm(jsonRoot);
                photos = Parser.getAppPhoto(jsonRoot);
                startRender();
            }
        }catch (Exception error){
            CustomDialog.CreateDialog(this);
        }
    }

    private void startRender (){
        mCustomPagerAdapter = new CustomPagerAdapter(this, photos);
        mViewPager.setAdapter(mCustomPagerAdapter);
        render();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Gson gson = new Gson();
        outState.putString(JSON_CustomPagerAdapter,gson.toJson(photos));
    }
}
