package com.example.jorick.androidhueapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jorick on 06/10/15.
 */
public class Light implements Parcelable {
    
    public String key;
    public String id;
    public String name;
    public int brightness;
    public boolean on;
    public int hue;
    public int sat;

    public Light() {

    }

    protected Light(Parcel in) {
        key = in.readString();
        id = in.readString();
        name = in.readString();
        brightness = in.readInt();
        on = in.readByte() != 0;
        hue = in.readInt();
        sat = in.readInt();
    }

    public static final Creator<Light> CREATOR = new Creator<Light>() {
        @Override
        public Light createFromParcel(Parcel in) {
            return new Light(in);
        }

        @Override
        public Light[] newArray(int size) {
            return new Light[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(brightness);
        dest.writeByte((byte) (on ? 1 : 0));
        dest.writeInt(hue);
        dest.writeInt(sat);
    }
}
