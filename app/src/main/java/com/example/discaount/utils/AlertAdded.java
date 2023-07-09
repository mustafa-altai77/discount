package com.example.discaount.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.discaount.R;

public class AlertAdded {
    private Activity activity;

     AlertDialog alertDialog,alertDialog2;


    public AlertAdded(Activity activity) {
        this.activity = activity;

    }

    public void showSuccessDialogAdd(String nameDrug) {
        activity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(activity).inflate(
                R.layout.add_dialog, null);
        builder.setView(view);
        RelativeLayout relativeAdded = view.findViewById(R.id.relativeAdded);
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.anim);
        relativeAdded.setAnimation(animation);
       alertDialog = builder.create();

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                    showNotisDialogAdd(nameDrug);
                }
            }
        }, 1000);
    }

    public void showNotisDialogAdd(String nameDrug) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(activity).inflate(
                R.layout.notification_add, null);
        builder.setView(view);
        TextView nameDragNotification=view.findViewById(R.id.nameDragNotification);
        nameDragNotification.setText(nameDrug);
//        RelativeLayout relativeAdded = view.findViewById(R.id.relativeAdded);
//        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.anim);
//        relativeAdded.setAnimation(animation);
        alertDialog2 = builder.create();
        WindowManager.LayoutParams wmlp = alertDialog2.getWindow().getAttributes();

        wmlp.gravity = Gravity.TOP | Gravity.LEFT;
        wmlp.x = 100;
        wmlp.y = 10;

        alertDialog2.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (alertDialog2.isShowing()) {
               //    alertDialog.dismiss();
                    alertDialog2.dismiss();
                }
            }
        }, 2000);
    }
}


