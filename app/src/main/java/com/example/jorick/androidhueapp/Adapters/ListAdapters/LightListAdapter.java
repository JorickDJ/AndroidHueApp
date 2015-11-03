package com.example.jorick.androidhueapp.Adapters.ListAdapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jorick.androidhueapp.CircleView;
import com.example.jorick.androidhueapp.Models.Light;
import com.example.jorick.androidhueapp.R;

/**
 * Created by Jorick on 07/10/15.
 */
public class LightListAdapter extends ArrayAdapter<Light> {

    public LightListAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.light_list_item, null);
        }

        Light light = getItem(position);

        float[] hsl = {
                (float) light.getHue() / 182,
                (float) light.getSat() / 255,
                (float) light.getBrightness() / 255
        };

        if (light != null) {
            TextView lblName = (TextView) v.findViewById(R.id.lblLightName);
            TextView lblId = (TextView) v.findViewById(R.id.lblLightDescription);
            CircleView vwColor = (CircleView) v.findViewById(R.id.vwLightColor);

            lblName.setText(light.getName());
            lblId.setText(light.getId());

            vwColor.setColor(ColorUtils.HSLToColor(hsl));
        }

        return v;
    }
}
