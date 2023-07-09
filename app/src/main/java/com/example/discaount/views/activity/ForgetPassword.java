package com.example.discaount.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.discaount.R;
import com.example.discaount.utils.BaseActivity;
import com.hbb20.CountryCodePicker;

public class ForgetPassword extends BaseActivity {
    ImageView imgFl;
    CountryCodePicker picker;
    EditText edMobile;
    Button btnChangePassword;

    //TODO
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        imgFl = findViewById(R.id.imgFl);
        edMobile = findViewById(R.id.conMobile);
        picker = findViewById(R.id.spDialcode);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        String lang = mySharePreference.getLanguage();
        if (lang.equals("ar")) {
            imgFl.setRotation(0);
        }
        imgFl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgetPassword.this, LoginActivity.class));
                finish();
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String allPhone = picker.getSelectedCountryCode() + edMobile.getText().toString();
                String messagePhone = getResources().getString(R.string.errer_phone_message);
                if (edMobile.getText().toString().length() < 9) {
                    edMobile.setError(messagePhone);
                    return;
                }
                Intent intent = new Intent(ForgetPassword.this, OtpForget.class);
                intent.putExtra("phone", allPhone);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ForgetPassword.this, Setting.class));
        finish();
    }
}