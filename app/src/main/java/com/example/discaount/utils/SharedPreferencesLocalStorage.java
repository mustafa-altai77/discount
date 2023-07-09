package com.example.discaount.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.discaount.data.model.AccessToken;
import com.example.discaount.data.model.LoginRequest;
import com.google.gson.Gson;


import java.util.Locale;

public class SharedPreferencesLocalStorage {
    public static final String SHARED_PREF_NAME = "larntech";
    private final Context _context;
    private final SharedPreferences _sharedPreferences;
    private final SharedPreferences.Editor _editor;
    private final String SELECTED_LANGUAGE = "com.zofirm.Selected.Language";
    private final String FileName = "com.zofirm.data.xml";
    private final String AccessTokenField = "com.zofirm.AccessToken";
    private final String UserField = "com.zofirm.UserField";
    private final String TOKEN = "token";
    public SharedPreferencesLocalStorage(Context context) {
        _context = context;
        _sharedPreferences = _context.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        _editor = _sharedPreferences.edit();
    }

    public void setAccessToken(AccessToken accessToken) {
        Gson gson = new Gson();
        String accessTokenAsString = gson.toJson(accessToken);
        _editor.putString(AccessTokenField, accessTokenAsString);
        _editor.commit();
    }

    public void storeUser(LoginRequest user) {
        Gson gson = new Gson();
        String result = gson.toJson(user);
        _editor.putString(UserField, result);
        _editor.commit();

    }

    public void logout() {
        _editor.remove(AccessTokenField);
        _editor.remove(UserField);
        _editor.commit();
    }

    public LoginRequest retrieveUser() {
        Gson gson = new Gson();
        String result = _sharedPreferences.getString(UserField, "");
        return gson.fromJson(result, LoginRequest.class);
    }

    public boolean isLogged() {
        return retrieveUser() != null;
    }

    public String getToken() {
        Gson gson = new Gson();
        String result = _sharedPreferences.getString(AccessTokenField, "");
        if (result.isEmpty())
            return null;
        AccessToken accessToken = gson.fromJson(result, AccessToken.class);
        return  accessToken.getGetAccessToken();
    }

    public void storeToken(String token) {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN, token);
        editor.commit();
        //Toast.makeText(mCtx, "commeted", Toast.LENGTH_SHORT).show();
    }

    public String getLanguage() {
        String defaultLanguage = Locale.getDefault().getLanguage();
        defaultLanguage = "ar";
        return _sharedPreferences.getString(SELECTED_LANGUAGE, defaultLanguage);
    }

    public void setLanguage(String language) {
        _editor.putString(SELECTED_LANGUAGE, language);
        _editor.apply();
    }

}
