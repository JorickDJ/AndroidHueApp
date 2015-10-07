package com.example.jorick.androidhueapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jorick on 07/10/15.
 */
public class LightSender extends AsyncTask<String, Void, Void> {

    private Light light;

    public LightSender(Light l) {
        light = l;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            URL url = new URL("http://" + params[0] + "/api/" + params[1] + "/lights/" + light.key);
            HttpURLConnection urlConnection = (HttpURLConnection)
                    url.openConnection();
            urlConnection.setRequestMethod("PUT");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");


            JSONObject json = new JSONObject();
            //json.put("bri", light.brightness);
            json.put("on", light.on);
            byte[] jsonData = json.toString().getBytes("UTF-8");

            urlConnection.connect();

            DataOutputStream oi = new DataOutputStream(urlConnection.getOutputStream());
            oi.write(jsonData);
            oi.flush();
            oi.close();

            Log.i("Light status", "" + urlConnection.getResponseCode());
        } catch (Exception e) {
            //
        }

        return null;
    }
}
