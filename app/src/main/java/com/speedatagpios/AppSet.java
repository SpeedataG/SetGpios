package com.speedatagpios;

import android.annotation.SuppressLint;
import android.app.Application;

/**
 * @author xuyan
 */
public class AppSet extends Application {

    @SuppressLint("StaticFieldLeak")
    private static AppSet sInstance;
    public boolean power;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;


    }


    public static AppSet getInstance() {
        return sInstance;
    }

    public boolean isPower() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }

}

