package com.example.discaount.views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.discaount.R;
import com.example.discaount.utils.BaseActivity;
import com.example.discaount.utils.LoadingDialog;

import static com.example.discaount.utils.SharedPref.LANG;
import static com.example.discaount.utils.SharedPref.SHARED_PREF_NAME;
import static com.example.discaount.utils.SharedPref.mCtx;

public class TermDiscount extends BaseActivity {
    WebView webView;
    LoadingDialog loadingDialog;
    Toolbar toolbar;
    SharedPreferences sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_discount);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        webView = findViewById(R.id.web_view_);
        webView.setWebViewClient(new WebViewClient());
        sharedPreference = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String lang = sharedPreference.getString(LANG, "SELECT");
        if (lang.equals("en")) {
            webView.loadUrl("http://shayoub.com/discount-term/term-and-condition-en.html");
        } else {
            webView.loadUrl("http://shayoub.com/discount-term/Terms-and-Conditions-ar.html");
        }
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        loadingDialog = new LoadingDialog(this);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
        super.onBackPressed();
    }

    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            loadingDialog.startLoadingDialog();
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            loadingDialog.dismissDialog();
        }
    }
}