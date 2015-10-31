package com.example.jorick.androidhueapp.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jorick.androidhueapp.Models.Light;

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
public class FetchLightsTask extends AsyncTask<String, Void, ArrayList<Light>> {

    private OnCompleteListener ocl;

    public FetchLightsTask(OnCompleteListener ocl) {
        this.ocl = ocl;
    }

    @Override
    protected ArrayList<Light> doInBackground(String... params) {
        String response = "";
        ArrayList<Light> lights = new ArrayList<>();

        try {
            URL url = new URL("http://" + params[0] + "/api/" + params[1] + "/lights");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                response += line;
            }
            reader.close();

            JSONObject _o = new JSONObject(response);
            Iterator<?> keys = _o.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                String type = _o.getJSONObject(key).getString("type");
                JSONObject state = _o.getJSONObject(key).getJSONObject("state");

                if (type.equals("Extended color light")) {
                    Light light = new Light();
                    light.setKey(key);
                    light.setId(_o.getJSONObject(key).has("uniqueid") ? _o.getJSONObject(key).getString("uniqueid") : null);
                    light.setName(_o.getJSONObject(key).getString("name"));
                    light.setBrightness(state.getInt("bri"));
                    light.setOn(state.getBoolean("on"));
                    light.setHue(state.getInt("hue"));
                    light.setSat(state.getInt("sat"));

                    lights.add(light);
                }
            }
        } catch (Exception e) {
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
