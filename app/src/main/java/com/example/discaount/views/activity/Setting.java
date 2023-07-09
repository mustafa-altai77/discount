package com.example.discaount.views.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.discaount.BuildConfig;
import com.example.discaount.LangugeActivity;
import com.example.discaount.R;
import com.example.discaount.utils.Alert;
import com.example.discaount.utils.BaseActivity;
import com.example.discaount.utils.SharedPref;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.discaount.utils.SharedPref.F_NAME;
import static com.example.discaount.utils.SharedPref.IMAGES;
import static com.example.discaount.utils.SharedPref.SHARED_PREF_NAME;
import static com.example.discaount.utils.SharedPref.mCtx;

public class Setting extends BaseActivity {

    RelativeLayout profile, login, share, aboutUs, reLang, relChangePassword;
    TextView login2, version;
    Alert alert;
    ImageView imgFl;
    View viewHidden,viewHidden2;
    CircleImageView imgProfileInSetting;
    SharedPreferences sharedPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        alert = new Alert(this);
        version = findViewById(R.id.version);
        profile = findViewById(R.id.profile);

        login = findViewById(R.id.login);
        login2 = findViewById(R.id.login2);
        viewHidden = findViewById(R.id.viewHidden);
        viewHidden2 = findViewById(R.id.viewHidden2);
        imgFl = findViewById(R.id.imgFl);
        share = findViewById(R.id.share);
        aboutUs = findViewById(R.id.aboutUs);
        reLang = findViewById(R.id.reLang);
        relChangePassword = findViewById(R.id.relChangePassword);
        imgProfileInSetting = findViewById(R.id.imgProfileInSetting);


        sharedPreference = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String image = sharedPreference.getString(IMAGES, "image");

        if (!sharedPreference.equals("image")) {
            imgProfileInSetting.setImageURI(Uri.parse(image));
        }
        if (SharedPref.getInstance(Setting.this).isLoggedIn()) {
            login2.setText("" + getResources().getString(R.string.logout));
        }
        relChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Setting.this, ChangePassword.class));
                finish();
            }
        });
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, AboutActivity.class);
                startActivity(intent);
                finish();
            }
        });
        version.setText(getResources().getString(R.string.version) + " " + BuildConfig.VERSION_NAME);
        imgFl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Setting.this, MainActivity.class));
                finish();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "" + getResources().getString(R.string.app_name));
                    String shareMessage = "\n" + getResources().getString(R.string.sendVia) + "\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    Toast.makeText(Setting.this, "" + e, Toast.LENGTH_SHORT).show(); //e.toString();
                }
            }
        });
        final String name = sharedPreference.getString(F_NAME, "name");

        if (name.equals("name")) {
            profile.setVisibility(View.GONE);
            relChangePassword.setVisibility(View.GONE);
            viewHidden.setVisibility(View.GONE);
            viewHidden2.setVisibility(View.GONE);

        }
        String lang = mySharePreference.getLanguage();
        if (lang.equals("ar")) {
            imgFl.setRotation(0);
        }

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
        reLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, LangugeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.getInstance(Setting.this).logout();
                Intent in = new Intent(Setting.this, SplashActivity.class);
                startActivity(in);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Setting.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}