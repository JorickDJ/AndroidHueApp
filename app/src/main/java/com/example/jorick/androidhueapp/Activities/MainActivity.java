package com.example.jorick.androidhueapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.jorick.androidhueapp.Adapters.ListAdapters.LightListAdapter;
import com.example.jorick.androidhueapp.Models.Bridge;
import com.example.jorick.androidhueapp.R;

public class MainActivity extends AppCompatActivity {

    private Bridge bridge;
    private LightListAdapter listAdapter;
    private ListView lstLights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.bridge = Bridge.getInstance(getApplicationContext());
        this.listAdapter = new LightListAdapter(this, R.layout.light_list_item);
        this.lstLights = (ListView) findViewById(R.id.lightListView);

        this.lstLights.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(getApplicationContext(), LightDetailActivity.class)
                        .putExtra("light", listAdapter.getItem((int) id));

                startActivity(detailIntent);
            }
        });

        refreshLightsList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            return true;
        } else if (id == R.id.action_refresh_list) {
            refreshLightsList();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshLightsList() {
        bridge.ScanForLights(new Bridge.OnDataChangedListener() {
            @Override
            public void onDataChanged() {
                listAdapter.clear();
                listAdapter.addAll(bridge.getLights());
                lstLights.setAdapter(listAdapter);
            }
        });
    }
}
