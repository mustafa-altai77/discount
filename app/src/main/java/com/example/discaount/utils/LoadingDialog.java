package com.example.discaount.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.discaount.R;

/**
 * Created by Mustafa on 4/24/2020.
 */

public class LoadingDialog {
    private Activity activity;
    private AlertDialog dialog;
    TextView textView;
    Typeface typeface;

    public LoadingDialog(Activity myaActivity) {
        activity = myaActivity;
    }

    public void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        //builder.setView(inflater.inflate(R.layout.custom_dialog2, null));
        View view = inflater.inflate(R.layout.custom_dialog2, null);
        builder.setView(view);
       // textView = view.findViewById(R.id.txtWa);
        dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
    }
//    public void startLoadingDialog(boolean cancelable) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        LayoutInflater inflater = activity.getLayoutInflater();
//        //builder.setView(inflater.inflate(R.layout.custom_dialog2, null));
//        View view = inflater.inflate(R.layout.custom_dialog2, null);
//        builder.setView(view);
//        //textView = view.findViewById(R.id.txtWa);
//        dialog = builder.create();
//        dialog.setCancelable(cancelable);
//        dialog.show();
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.setCanceledOnTouchOutside(false);
//    }

    public void dismissDialog() {
        dialog.dismiss();
    }

   /*LoadingDialog(Activity myaActivity) {
       activity = myaActivity;
   }
   void startLoadingDialog() {
       LayoutInflater factory = LayoutInflater.from(activity);
       View DialogView = factory.inflate(R.layout.custom_dialog2, null);
       Dialog main_dialog = new Dialog(activity,R.style.SpinKitView_Large_CubeGrid);
       main_dialog.setContentView(DialogView);

       dialog=(ProgressBar)DialogView.findViewById(R.id.spinKit);
       main_dialog.setCancelable(false);
       main_dialog.setCanceledOnTouchOutside(false);
       dialog.setProgress(0);
       dialog.setMax(100);
       main_dialog.show();
   }*/
}