package com.example.discaount.views.activity;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.discaount.R;
import com.example.discaount.utils.BaseActivity;
import com.example.discaount.utils.Constants;
import com.example.discaount.utils.SharedPref;
import com.example.discaount.utils.SharedPreferencesLocalStorage;
import com.example.discaount.utils.ToastMessage;
import com.google.firebase.iid.FirebaseInstanceId;


import butterknife.ButterKnife;

import static com.example.discaount.utils.SharedPref.IMAGES;
import static com.example.discaount.utils.SharedPref.LANG;
import static com.example.discaount.utils.SharedPref.SHARED_PREF_NAME;
import static com.example.discaount.utils.SharedPref.mCtx;

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                checkConnection();
            }
        }, 1500);
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public void checkConnection() {
        if (isOnline()) {
            if (SharedPref.getInstance(this).isLoggedIn()) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        }
        else {
            ToastMessage toastMessage=new ToastMessage(SplashActivity.this);
            toastMessage.messageError(getResources().getString(R.string.no_internet),true);
        }
    }
}