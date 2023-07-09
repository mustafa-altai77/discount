package com.example.discaount.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.discaount.R;
import com.example.discaount.views.HomeFragment;
import com.example.discaount.views.activity.LoginActivity;
import com.example.discaount.views.activity.MainActivity;
import com.example.discaount.views.activity.SplashActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static com.example.discaount.utils.SharedPref.LANG;
import static com.example.discaount.utils.SharedPref.SHARED_PREF_NAME;
import static com.example.discaount.utils.SharedPref.mCtx;

public class Alert {
    private Activity activity;
    TextView t1, t2;
    Button button;



    public Alert(Activity myaActivity) {
        activity = myaActivity;
    }

    public void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(activity).inflate(
                R.layout.layout_show_dialog, null);
        builder.setView(view);
        t1 = view.findViewById(R.id.textTitle);
        // t1.setText(title);
        t2 = view.findViewById(R.id.textMessage);
        //t2.setText(message);
        button = view.findViewById(R.id.buttonAction);
        button.setText(activity.getResources().getString(R.string.okey));
        ImageView imageView = view.findViewById(R.id.imageMessage);
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.anim);
        imageView.setAnimation(animation);
        imageView.setImageResource(R.drawable.ic_delivery);
        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController;
                navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_homeFragment_to_offerFragment);
            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    public void changeLanguage() {
        {
            SharedPreferences sharedPreference = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            final String storage = sharedPreference.getString(LANG, "en");
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
            View view = LayoutInflater.from(activity).inflate(
                    R.layout.dialog_language, null);
            builder.setView(view);

            RadioGroup radioLanguage = view.findViewById(R.id.radioLanguage);
            if (storage.equals("en")) {
                RadioButton en = view.findViewById(R.id.englishLanguage);
                en.setChecked(true);

            } else {
                RadioButton ar = view.findViewById(R.id.arabicLanguage);
                ar.setChecked(true);
            }
            ImageView close_button = view.findViewById(R.id.close_button_language);

            radioLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    RadioButton radioLanguageButton = view.findViewById(i);
                    if (radioLanguageButton != null && i > -1) {

                        if (radioLanguageButton.getText().equals(activity.getResources().getString(R.string.english))) {
                            setLocale("en");
                        } else {
                            setLocale("ar");
                        }
                    }
                }
            });
            final AlertDialog alertDialog = builder.create();
            close_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  AlertDialog  alertDialog = builder.create();
                    alertDialog.dismiss();
                    Intent i = activity.getBaseContext().getPackageManager().getLaunchIntentForPackage(activity.getPackageName());
                    activity.startActivity(i);
                    activity.finish();
                }
            });
            if (alertDialog.getWindow() != null) {
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            alertDialog.show();
        }
    }

    private void setLocale(String lang) {
        // storage.setLanguage(lang);
        SharedPref.getInstance(activity).setLang(lang);
        SharedPreferences sharedPreference = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String storage = sharedPreference.getString(LANG, "en");
        Log.d("LANGUAGE", "" + lang + "\t" + storage);
        Locale locale = new Locale(lang);
        Resources res = activity.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration config = new Configuration();
        config.locale = locale;
        res.updateConfiguration(config, dm);
        activity.getBaseContext().getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());
        Intent intent = new Intent(activity, SplashActivity.class);
        activity.startActivity(intent);
        activity.finish();

    }

    private void callActivity() {
        Intent intent = new Intent(activity, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.startActivity(intent);
        activity.finish();
    }

    public void loadLocale() {
        SharedPreferences pref = activity.getSharedPreferences("settings", Activity.MODE_PRIVATE);
        String language = pref.getString("en", "");
        setLocale(language);

    }
}

