package com.example.discaount.views.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.discaount.R;
import com.example.discaount.utils.BaseActivity;

import static com.example.discaount.utils.SharedPref.LANG;
import static com.example.discaount.utils.SharedPref.SHARED_PREF_NAME;
import static com.example.discaount.utils.SharedPref.mCtx;

public class ContactUs extends BaseActivity {
    RelativeLayout callDiscount, sendMessage;
    ImageView imgFl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        callDiscount = findViewById(R.id.callDiscount);
        sendMessage = findViewById(R.id.sendMessage);
        imgFl = findViewById(R.id.imgFl);
        imgFl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactUs.this,Setting.class));
                finish();
            }
        });
        String lang = mySharePreference.getLanguage();
        if (lang.equals("ar")) {
            imgFl.setRotation(0);
        }
        callDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:249918888185"));
                startActivity(intent);
            }
        });
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@shayoub.com"});
                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ContactUs.this, Setting.class);
        startActivity(intent);
        finish();
    }
}