package com.example.vitaliy.comandroidchinema.Data.Models;

import org.json.JSONObject;

/**
 * Created by vitaliy on 07.06.2016.
 */
public class Film {
    int id;
    String name;
    String genre;
    String description;
    String durationView;
    String date;

    public Film (){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDurationView() {
        return durationView;
    }

    public void setDurationView(String durationView) {
        this.durationView = durationView;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
