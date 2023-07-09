package com.example.discaount.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.discaount.views.activity.MainActivity;

import java.util.Locale;


public class SharedPref {
    //Storage File
    public static final String SHARED_PREF_NAME = "larntech";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    //Username
    public static final String USER_ID = "id";
    public static final String F_NAME = "name";
    public static final String PHONE = "phone";
    public static final String AGE = "age";
    public static final String TOKEN = "token";

    public static final String ADDRESS = "address";
    public static final String LANG = "Locale.Helper.Selected.Language";
    public static final String IMAGES = "image";


    public static SharedPref mInstance;

    public static Context mCtx;


    public SharedPref(Context context) {
        mCtx = context;
    }


    public void setFirstTimeLaunch(boolean isFirstTime) {

    }

    public static synchronized SharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPref(context);
        }
        return mInstance;
    }

    public void storeUserLocal(String local) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
        editor.putString(ADDRESS, local);
        editor.commit();
    }

    //method to store user data
    public void storeUserID(String name, String phone, String adress, String age) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(F_NAME, name);
        editor.putString(PHONE, phone);
        editor.putString(ADDRESS, adress);
        editor.putString(AGE, age);
        editor.commit();
        // Toast.makeText(mCtx, ""+USER_ID, Toast.LENGTH_SHORT).show();
    }

    public void storeNumber(String phone) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PHONE, phone);
        editor.commit();
        // Toast.makeText(mCtx, ""+USER_ID, Toast.LENGTH_SHORT).show();
    }

    public void storeToken(String token) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN, token);
        editor.commit();
        // Toast.makeText(mCtx, ""+USER_ID, Toast.LENGTH_SHORT).show();
    }

    public boolean HasToken() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return !sharedPreferences.getString(TOKEN, "-1").equals("-1");
    }

    //check if user is logged in
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TOKEN, null) != null;
    }


    //find logged in user
    public String LoggedInUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_ID, null);
    }

    //Logout user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        mCtx.startActivity(new Intent(mCtx, MainActivity.class));
    }

    public void setLang(String lang) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LANG, lang);
        editor.apply();
        // Toast.makeText(mCtx, ""+USER_ID, Toast.LENGTH_SHORT).show();
    }

    public void storeImage(String image) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IMAGES, image);
        editor.commit();
    }

    public void ClearImage() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(IMAGES);
        editor.commit();
    }

}
