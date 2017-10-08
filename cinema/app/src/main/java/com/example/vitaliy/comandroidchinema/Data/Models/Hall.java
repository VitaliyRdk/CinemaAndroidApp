package com.example.vitaliy.comandroidchinema.Data.Models;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vitaliy on 07.06.2016.
 */
public class Hall {
    int id;
    String name;
    int count;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
