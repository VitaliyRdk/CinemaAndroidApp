package com.example.vitaliy.comandroidchinema.Data.Helpers.Parsers;

import com.example.vitaliy.comandroidchinema.Data.Helpers.DataURL;
import com.example.vitaliy.comandroidchinema.Data.Models.Film;
import com.example.vitaliy.comandroidchinema.Data.Models.Hall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vitaliy on 11.06.2016.
 */
public class Parser {
    public static List<Hall> getListHall (JSONObject jsonRoot) throws JSONException {
        List<Hall> halls = new ArrayList<>();
        JSONArray jsonHalls = jsonRoot.getJSONArray("data");
        for (int i=0;i<jsonHalls.length();i++){
            JSONObject jsonHall = jsonHalls.getJSONObject(i).getJSONObject("Hall");
            Hall hall = new Hall();
            hall.setId(jsonHall.getInt("id"));
            hall.setName(jsonHall.getString("Name"));
            hall.setCount(jsonHall.getInt("count"));
            halls.add(hall);
        }
        return halls;
    }

    public static List<Film> getListFilm (JSONObject jsonRoot) throws JSONException {
        List<Film> filmList = new ArrayList<>();
        JSONArray jsonFilms = jsonRoot.getJSONArray("data");
        for (int i=0;i<jsonFilms.length();i++){
            JSONObject jsonItem = jsonFilms.getJSONObject(i);
            JSONObject jsonfilm = jsonItem.getJSONObject("film");
            JSONObject jsonFilm = jsonfilm.getJSONObject("Film");

            Film film = new Film();
            film.setId(jsonFilm.getInt("id"));
            film.setName(jsonFilm.getString("Name"));
            film.setDescription(jsonFilm.getString("Description"));
            film.setDurationView(jsonFilm.getString("DurationView"));
            film.setGenre(jsonFilm.getString("Genre"));
            film.setDate(jsonFilms.getJSONObject(i).getString("date"));
            filmList.add(film);
        }
        return filmList;
    }

    public static Film getFilm (JSONObject jsonRoot) throws JSONException {
        JSONObject jsonFilms = jsonRoot.getJSONObject("data");
        JSONObject jsonFilm = jsonFilms.getJSONObject("Film");

        Film film = new Film();
        film.setId(jsonFilm.getInt("id"));
        film.setName(jsonFilm.getString("Name"));
        film.setDescription(jsonFilm.getString("Description"));
        film.setDurationView(jsonFilm.getString("DurationView"));
        film.setGenre(jsonFilm.getString("Genre"));
        return film;
    }

    public static List<String> getAppPhoto (JSONObject jsonRoot) throws JSONException {
        List<String> photos = new ArrayList<>();
        JSONObject jsonFilms = jsonRoot.getJSONObject("data");
        JSONArray jsonPhotos = jsonFilms.getJSONArray("Photo");
        for (int i=0;i<jsonPhotos.length();i++){
            JSONObject jsonPhoto = jsonPhotos.getJSONObject(i);
            photos.add(DataURL.downloadPhoto(jsonPhoto.getString("photo")));
        }

        return photos;
    }
}
