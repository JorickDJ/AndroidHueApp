package com.example.jorick.androidhueapp.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jorick.androidhueapp.Models.Light;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jorick on 07/10/15.
 */
public class SendLightStateTask extends AsyncTask<String, Void, Void> {

    private Light light;

    public SendLightStateTask(Light l) {
        light = l;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            Log.i("Sender URL", "http://" + params[0] + "/api/" + params[1] + "/lights/" + light.getKey() + "/state");

            URL url = new URL("http://" + params[0] + "/api/" + params[1] + "/lights/" + light.getKey() + "/state");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("PUT");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            JSONObject json = new JSONObject();
            json.put("on", light.isOn());

            if (light.isOn()) {
                json.put("bri", light.getBrightness());
                json.put("hue", light.getHue());
                json.put("sat", light.getSat());
            }

            byte[] jsonData = json.toString().getBytes("UTF-8");

            urlConnection.connect();

            DataOutputStream oi = new DataOutputStream(urlConnection.getOutputStream());
            oi.write(jsonData);
            oi.flush();
            oi.close();

            urlConnection.getResponseCode();
        } catch (Exception e) {
            //
        }

        return null;
    }
}
