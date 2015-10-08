package com.example.jorick.androidhueapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

public class LightDetailActivity extends AppCompatActivity {

    private Light light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_detail);

        Bundle extra = getIntent().getExtras();
        light = extra.getParcelable("light");

        this.setTitle(light.name);

        SeekBar _seeker = (SeekBar) findViewById(R.id.seekBar);
        SeekBar _seekerSaturation = (SeekBar) findViewById(R.id.seekSaturation);
        SeekBar _seekerHue = (SeekBar) findViewById(R.id.seekHue);
        Switch _switch = (Switch) findViewById(R.id.onSwitch);

        _seekerSaturation.setProgress(light.sat);
        _seekerHue.setProgress(light.hue);
        _seeker.setProgress(light.brightness);
        _switch.setChecked(light.on);

        _switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                light.on = isChecked;
                Bridge.getInstance().sendLightState(light);
            }
        });

        _seeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                light.brightness = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Bridge.getInstance().sendLightState(light);
            }
        });

        _seekerSaturation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                light.sat = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Bridge.getInstance().sendLightState(light);
            }
        });

        _seekerHue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                light.hue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Bridge.getInstance().sendLightState(light);
            }
        });
    }
}
