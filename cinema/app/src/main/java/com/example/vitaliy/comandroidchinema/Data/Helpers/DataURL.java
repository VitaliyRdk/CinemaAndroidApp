package com.example.vitaliy.comandroidchinema.Data.Helpers;

import okhttp3.Request;

/**
 * Created by vitaliy on 07.06.2016.
 */
public class DataURL {
    private static String rootURL = "http://androidcinema.esy.es/";
    private static String rootAPI = "api/";
    private static String baseURL = rootURL + rootAPI;

    public static Request allHallsUrl (){
        Request request = new Request.Builder()
                .url(baseURL + "getAllHalls/")
                .build();
        return request;
    }

    public static Request filmsUrl (int idHall) {
        Request request = new Request.Builder()
                .url(baseURL + "getAllFilms/" + String.valueOf(idHall))
                .build();
        return request;
    }

    public static Request filmUrl (int idFilm) {
        Request request = new Request.Builder()
                .url(baseURL + "getFilmById/" + String.valueOf(idFilm))
                .build();
        return request;
    }

    public static String downloadPhoto (String urlPhoto) {
        return rootURL + "images/" + urlPhoto;
    }

    public static String opneFilmInfo (int idFilm){
        return rootURL + "afisha/view/" + String.valueOf(idFilm);
    }

    public static String openweb (){
        return rootURL;
    }
}
