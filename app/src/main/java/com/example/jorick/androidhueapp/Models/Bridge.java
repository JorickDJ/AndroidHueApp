package com.example.jorick.androidhueapp.Models;

import com.example.jorick.androidhueapp.Tasks.FetchLightsTask;
import com.example.jorick.androidhueapp.Tasks.SendLightStateTask;

import java.util.ArrayList;

/**
 * Created by Jorick on 07/10/15.
 */
public class Bridge {

    public static Bridge instance = null;
    private boolean debug = true;
    private String ip;
    private String key;
    private ArrayList<Light> lights = new ArrayList<>();

    private Bridge() {
        if (this.debug) {
            this.ip = "10.0.0.103";
            this.key = "newdeveloper";
        } else {
            this.ip = "192.168.1.179";
            this.key = "41c7bc4460e4e214d3e1cf1fe7c1bf";
        }
    }

    public static Bridge getInstance() {
        if (instance == null) {
            instance = new Bridge();
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
        }).execute(ip, key);
    }

    public void sendLightState(Light light) {
        new SendLightStateTask(light).execute(ip, key);
    }

    public interface OnDataChangedListener {
        void onDataChanged();
    }
}
