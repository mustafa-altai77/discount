package com.example.discaount.utils;

import android.app.Application;
import android.content.Context;

import com.example.discaount.utils.LocaleHelper;

public class MyApplication extends Application
{

    private static MyApplication appClass;
    public static MyApplication getintance()
    {
        return appClass;
    }


    @Override
    public void onCreate()
    {
        super.onCreate();
        appClass = this;
        MySharePreference.getInstance(this);
    }


    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

}