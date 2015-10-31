package com.example.jorick.androidhueapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

/**
 * Created by Jorick on 06/10/15.
 */
public class Light implements Parcelable {
    
    private String key;
    private String id;
    private String name;
    private int brightness;
    private boolean on;
    private int hue;
    private int sat;

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id != null) {
            this.id = id;
            return;
        }

        this.id = generateId(8);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public int getSat() {
        return sat;
    }

    public void setSat(int sat) {
        this.sat = sat;
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

    private String generateId(final int length) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < length; i++) {
            char c = (char) (r.nextInt((int) (Character.MAX_VALUE)));
            sb.append(c);
        }

        return sb.toString();
    }
}
