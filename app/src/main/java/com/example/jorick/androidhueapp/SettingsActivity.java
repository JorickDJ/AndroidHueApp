package com.example.jorick.androidhueapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final SharedPreferences settings = getSharedPreferences("Bridge", 0);
        final SharedPreferences.Editor settingsEditor = settings.edit();

        TextView txtBridgeIp = (TextView) findViewById(R.id.txtBridgeIp);
        TextView txtBridgeKey = (TextView) findViewById(R.id.txtBridgeKey);

        final Button button = (Button) findViewById(R.id.btnSaveSettings);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                settingsEditor.putString("COUNTRY", txtCountry.getText().toString());
                settingsEditor.apply();
            }
        });
    }
}
