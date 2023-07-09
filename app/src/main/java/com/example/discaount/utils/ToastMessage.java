package com.example.discaount.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.discaount.R;

public class ToastMessage {
    Typeface typeface;
    Context context;

    public ToastMessage(Context context) {
        this.context = context;
    }

    public void message(String message)
{
    typeface = Typeface.createFromAsset(context.getAssets(), "font/cairo_sembold.ttf");
    Toast toast=Toast.makeText(context,message,Toast.LENGTH_LONG);
    View view=toast.getView();
    TextView view1=view.findViewById(android.R.id.message);
    view1.setBackgroundColor(Color.parseColor("#66af01"));
    view1.setTextColor(Color.WHITE);
    view1.setTextSize(12);
    //view.setBackgroundResource(R.color.colorPrimary);
    view1.setTypeface(typeface);
    toast.show();
}
    public void messageError(String message ,boolean hasError )
    {
        View layout = LayoutInflater.from(context).inflate(
                R.layout.toast, null);
        ImageView image = (ImageView) layout.findViewById(R.id.image);
        LinearLayout linearLayout=(LinearLayout) layout.findViewById(R.id.toast_layout_root);
        if (hasError==true) {
            linearLayout.setBackgroundTintList(context.getResources().getColorStateList(R.color.tost_color_error));
            image.setImageResource(R.drawable.ic_baseline_error_24);
            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText("" + message);
            Toast toast = new Toast(context.getApplicationContext());
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        }
        else   {
            linearLayout.setBackgroundTintList(context.getResources().getColorStateList(R.color.purple_700));
            image.setImageResource(R.drawable.ic_check);
            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText("" + message);
            Toast toast = new Toast(context.getApplicationContext());
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        }
    }
}
