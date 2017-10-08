package com.example.vitaliy.comandroidchinema.Data.Helpers.Loaders;

import android.os.AsyncTask;
import android.util.Log;

import com.example.vitaliy.comandroidchinema.Data.Helpers.DataURL;
import com.example.vitaliy.comandroidchinema.Data.Helpers.Interface.LoaderInterface;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by vitaliy on 11.06.2016.
 */
public class FilmLoader extends AsyncTask<String, Void, Response> {
    private LoaderInterface loaderInterface;
    private int idHall;

    public FilmLoader(LoaderInterface loaderInterface, int idHall){
        this.loaderInterface = loaderInterface;
        this.idHall = idHall;
    }

    @Override
    protected Response doInBackground(String... params) {
        Response response = null;
        try {
            OkHttpClient client = new OkHttpClient.Builder().build();

            response = client.newCall(DataURL.filmsUrl(idHall)).execute();

        }catch (Exception error)
        {
            Log.d("HallLoader", "doInBackground: " + error.getMessage());
        }
        return response;
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPreExecute();
        try {
            loaderInterface.result(response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
