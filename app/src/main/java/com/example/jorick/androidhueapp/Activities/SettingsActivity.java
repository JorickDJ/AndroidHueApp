package com.example.jorick.androidhueapp.Activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jorick.androidhueapp.R;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;
    private TextView txtBridgeIp;
    private TextView txtBridgeKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.preferences = getApplicationContext().getSharedPreferences("Bridge", 0);
        this.preferencesEditor = this.preferences.edit();

        this.txtBridgeIp = (TextView) findViewById(R.id.txtBridgeIp);
        this.txtBridgeKey = (TextView) findViewById(R.id.txtBridgeKey);

        this.txtBridgeIp.setText(this.preferences.getString("BRIDGE_IP", "10.0.0.103"));
        this.txtBridgeKey.setText(this.preferences.getString("BRIDGE_KEY", "newdeveloper"));
    }

    @Override
    protected void onPause() {
        super.onPause();

        saveSettings();
    }

    private void saveSettings() {
        this.preferencesEditor.putString("BRIDGE_IP", txtBridgeIp.getText().toString());
        this.preferencesEditor.putString("BRIDGE_KEY", txtBridgeKey.getText().toString());
        this.preferencesEditor.apply();
    }
}
