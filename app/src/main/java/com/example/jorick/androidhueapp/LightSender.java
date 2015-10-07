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
            Log.i("Sender URL", "http://" + params[0] + "/api/" + params[1] + "/lights/" + light.key + "/state");

            URL url = new URL("http://"+params[0]+"/api/"+params[1]+"/lights/"+light.key+"/state");
            HttpURLConnection urlConnection = (HttpURLConnection)  url.openConnection();

            urlConnection.setRequestMethod("PUT");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            JSONObject json = new JSONObject();
            json.put("on", light.on);

            if (light.on) {
                json.put("bri", light.brightness);
                json.put("hue", light.hue);
                json.put("sat", light.sat);
            }

            byte[] jsonData = json.toString().getBytes("UTF-8");

            urlConnection.connect();

            DataOutputStream oi = new DataOutputStream(urlConnection.getOutputStream());
            oi.write(jsonData);
            oi.flush();
            oi.close();

            Log.i("HTTP Response:", ""+urlConnection.getResponseCode());
        } catch (Exception e) {
            //
        }

        return null;
    }
}
