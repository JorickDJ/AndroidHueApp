package com.example.jorick.androidhueapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

public class LightDetail extends AppCompatActivity {

    private Light light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_detail);
        Bundle extra = getIntent().getExtras();
        light = extra.getParcelable("light");

        this.setTitle(light.name);

        SeekBar seeker = (SeekBar) findViewById(R.id.seekBar);
        Switch _switch = (Switch) findViewById(R.id.onSwitch);

        seeker.setProgress(light.brightness);
        _switch.setChecked(light.on);

        _switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                light.on = isChecked;

                Bridge bridge = Bridge.getInstance();
                bridge.sendLightState(light);
            }
        });
    }
}
