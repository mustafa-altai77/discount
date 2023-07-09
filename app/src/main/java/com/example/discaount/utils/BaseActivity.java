package com.example.discaount.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.discaount.utils.LocaleHelper;

public class BaseActivity extends AppCompatActivity {
    public BaseActivity context;
    public MySharePreference mySharePreference;
    public ToastMessage toastMessage;
    public LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = BaseActivity.this;
        mySharePreference = MySharePreference.getInstance(context);
        LocaleHelper.setLocale(context, mySharePreference.getLanguage());
        toastMessage = new ToastMessage(this);
        loadingDialog=new LoadingDialog(this);
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

}