package com.example.discaount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.discaount.utils.BaseActivity;
import com.example.discaount.utils.LocaleHelper;
import com.example.discaount.views.activity.Setting;
import com.example.discaount.views.activity.SplashActivity;

import java.util.Locale;
import java.util.Set;

public class LangugeActivity extends BaseActivity {
    RelativeLayout relAr, relEn;
    ImageView imgFl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languge);
        relAr = findViewById(R.id.relAr);
        relEn = findViewById(R.id.relEn);
        imgFl = findViewById(R.id.imgFl);
        relEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySharePreference.setLanguage("en");
                startActivity(new Intent(LangugeActivity.this, SplashActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });
        imgFl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LangugeActivity.this, Setting.class));
                finish();
            }
        });

        relAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySharePreference.setLanguage("ar");
                // startActivity(new Intent(LangugeActivity.this, SplashActivity.class));
                startActivity(new Intent(LangugeActivity.this, SplashActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });
        String lang = mySharePreference.getLanguage();
        if (lang.equals("ar")) {
            imgFl.setRotation(0);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LangugeActivity.this, Setting.class));
        finish();
    }
}