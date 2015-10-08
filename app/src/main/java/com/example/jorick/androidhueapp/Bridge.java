package com.example.jorick.androidhueapp;

import java.util.ArrayList;

/**
 * Created by Jorick on 07/10/15.
 */
public class Bridge {

    public static Bridge instance = null;
    private final String ip = "192.168.1.179";
    private final String key = "41c7bc4460e4e214d3e1cf1fe7c1bf";
    private ArrayList<Light> lights = new ArrayList<>();

    private Bridge() {
        //
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
        new LightRetriever(new LightRetriever.OnCompleteListener() {
            @Override
            public void onLightsLoaded(ArrayList<Light> l) {
                lights = l;
                odcl.onDataChanged();
            }
        }).execute(ip, key);
    }

    public void sendLightState(Light light) {
        new LightSender(light).execute(ip, key);
    }

    public interface OnDataChangedListener {
        void onDataChanged();
    }
}
