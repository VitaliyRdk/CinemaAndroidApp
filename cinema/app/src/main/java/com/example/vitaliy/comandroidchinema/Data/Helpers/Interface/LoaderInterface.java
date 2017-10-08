package com.example.vitaliy.comandroidchinema.Data.Helpers.Interface;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by vitaliy on 11.06.2016.
 */
public interface LoaderInterface {
    void result (Response response) throws IOException, JSONException;
}
