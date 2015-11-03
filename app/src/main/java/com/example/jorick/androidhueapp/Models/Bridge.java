package com.example.jorick.androidhueapp.Models;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.jorick.androidhueapp.Tasks.FetchLightsTask;
import com.example.jorick.androidhueapp.Tasks.SendLightStateTask;

import java.util.ArrayList;

/**
 * Created by Jorick on 07/10/15.
 */
public class Bridge {

    public static Bridge instance = null;
    private SharedPreferences preferences;
    private ArrayList<Light> lights = new ArrayList<>();

    private final String DEFAULT_IP = "127.0.0.1";
    private final String DEFAULT_KEY = "newdeveloper";

//    private final String DEFAULT_IP = "192.168.1.179";
//    private final String DEFAULT_KEY = "41c7bc4460e4e214d3e1cf1fe7c1bf";

    private Bridge(Context context) {
        this.preferences = context.getSharedPreferences("Bridge", 0);
    }

    public static Bridge getInstance(Context context) {
        if (instance == null) {
            instance = new Bridge(context);
        }

        return instance;
    }

    public ArrayList<Light> getLights() {
        return lights;
    }

    public void ScanForLights(final OnDataChangedListener odcl) {
        new FetchLightsTask(new FetchLightsTask.OnCompleteListener() {
            @Override
            public void onLightsLoaded(ArrayList<Light> l) {
                lights = l;
                odcl.onDataChanged();
            }
        }).execute(
                this.preferences.getString("BRIDGE_IP", DEFAULT_IP),
                this.preferences.getString("BRIDGE_KEY", DEFAULT_KEY)
        );
    }

    public void sendLightState(Light light) {
        new SendLightStateTask(light).execute(
                this.preferences.getString("BRIDGE_IP", DEFAULT_IP),
                this.preferences.getString("BRIDGE_KEY", DEFAULT_KEY)
        );
    }

    public interface OnDataChangedListener {
        void onDataChanged();
    }
}
