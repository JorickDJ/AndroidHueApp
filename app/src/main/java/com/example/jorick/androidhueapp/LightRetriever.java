package com.example.jorick.androidhueapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Jorick on 06/10/15.
 */
public class LightRetriever extends AsyncTask<String, Void, ArrayList<Light>> {

    private OnCompleteListener ocl;

    public LightRetriever(OnCompleteListener ocl) {
        this.ocl = ocl;
    }

    @Override
    protected ArrayList<Light> doInBackground(String... params) {
        String response = "";
        ArrayList<Light> lights = new ArrayList<>();
        try{
            URL url = new URL("http://"+params[0]+"/api/"+params[1]+"/lights");
            HttpURLConnection urlConnection = (HttpURLConnection)
                    url.openConnection();
            BufferedReader
                    reader = new
                    BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            while((line = reader.readLine()) != null){
                response += line;
            }
            reader.close();

            JSONObject _o = new JSONObject(response);

            Iterator<?> keys = _o.keys();

            while(keys.hasNext()) {
                Light light = new Light();
                String key = (String)keys.next();
                JSONObject state = _o.getJSONObject(key).getJSONObject("state");

                light.key = key;
                light.id = _o.getJSONObject(key).getString("uniqueid");
                light.name = _o.getJSONObject(key).getString("name");
                light.brightness = state.getInt("bri");
                light.on = state.getBoolean("on");

                lights.add(light);

            }
        } catch (Exception e){
            Log.e("Dikke error, ey", e.getMessage());
        }

        return lights;
    }

    @Override
    protected void onPostExecute(ArrayList<Light> lights) {
        ocl.onLightsLoaded(lights);
    }

    public interface OnCompleteListener {
        void onLightsLoaded(ArrayList<Light> lights);
    }
}
