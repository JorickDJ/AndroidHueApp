package com.example.jorick.androidhueapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

import com.example.jorick.androidhueapp.Models.Bridge;
import com.example.jorick.androidhueapp.Models.Light;
import com.example.jorick.androidhueapp.R;

public class LightDetailActivity extends AppCompatActivity {

    private Light light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_detail);

        Bundle extra = getIntent().getExtras();
        light = extra.getParcelable("light");

        this.setTitle(light != null ? light.getName() : null);

        SeekBar _seeker = (SeekBar) findViewById(R.id.seekBar);
        SeekBar _seekerSaturation = (SeekBar) findViewById(R.id.seekSaturation);
        SeekBar _seekerHue = (SeekBar) findViewById(R.id.seekHue);
        Switch _switch = (Switch) findViewById(R.id.onSwitch);

        _seekerSaturation.setProgress(light.getSat());
        _seekerHue.setProgress(light.getHue());
        _seeker.setProgress(light.getBrightness());
        _switch.setChecked(light.isOn());

        _switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                light.setOn(isChecked);
                Bridge.getInstance(getApplicationContext()).sendLightState(light);
            }
        });

        _seeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                light.setBrightness(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Bridge.getInstance(getApplicationContext()).sendLightState(light);
            }
        });

        _seekerSaturation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                light.setSat(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Bridge.getInstance(getApplicationContext()).sendLightState(light);
            }
        });

        _seekerHue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                light.setHue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Bridge.getInstance(getApplicationContext()).sendLightState(light);
            }
        });
    }
}
