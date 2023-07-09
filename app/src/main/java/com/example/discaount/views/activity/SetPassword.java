package com.example.discaount.views.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.discaount.R;
import com.example.discaount.data.model.ChangePassModel;
import com.example.discaount.data.model.SetPasswordModel;
import com.example.discaount.data.network.ApiClient;
import com.example.discaount.data.network.ApiInterface;
import com.example.discaount.utils.BaseActivity;
import com.example.discaount.utils.SharedPref;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.discaount.utils.SharedPref.PHONE;
import static com.example.discaount.utils.SharedPref.SHARED_PREF_NAME;
import static com.example.discaount.utils.SharedPref.TOKEN;
import static com.example.discaount.utils.SharedPref.mCtx;

public class SetPassword extends BaseActivity {
    ImageView imgFl;
    TextInputLayout  newPasswordSet, rePasswordSet;
    Button btnSetPassword;
    SharedPreferences sharedPreference;
    String Number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        imgFl = findViewById(R.id.imgFl);
        newPasswordSet = findViewById(R.id.newPasswordSet);
        rePasswordSet = findViewById(R.id.rePasswordSet);
        btnSetPassword = findViewById(R.id.btnSetPassword);
        Number = getIntent().getStringExtra("phone");
        String lang = mySharePreference.getLanguage();
        if (lang.equals("ar")) {
            imgFl.setRotation(0);
        }

        imgFl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(ChangePassword.this, Setting.class));
              //  finish();
            }
        });
        btnSetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newP = newPasswordSet.getEditText().getText().toString();
                final String reP= rePasswordSet.getEditText().getText().toString();
                FormValidator formValidator = new FormValidator();
                boolean isValidForm = formValidator.isValid();
                if (isValidForm) {
                    if (!newP.equals(reP))
                    {
                        toastMessage.messageError(getResources().getString(R.string.pass_match), true);
                    }
                    else {
                        changePassword(Number, newP);
                    }
                }
                else {
                    toastMessage.messageError(getResources().getString(R.string.fill_all_required), true);
                    loadingDialog.dismissDialog();
                }
            }
        });
    }

    private void changePassword(String phone, String newPassword) {
        sharedPreference = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String token = sharedPreference.getString(TOKEN, "token");
        loadingDialog.startLoadingDialog();
        Log.d("ANARASTAFR",token+"\n"+phone+"\n"+newPassword);
        SetPasswordModel passModel = new SetPasswordModel(phone,newPassword);
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<SetPasswordModel> call = apiService.setPassword(token, passModel);
        call.enqueue(new Callback<SetPasswordModel>() {
            @Override
            public void onResponse(Call<SetPasswordModel> call, Response<SetPasswordModel> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()) {
                    toastMessage.messageError(getResources().getString(R.string.passChange), false);
                    SharedPref.getInstance(SetPassword.this).logout();
                    Intent in = new Intent(SetPassword.this, LoginActivity.class);
                    startActivity(in);
                    finish();
                } else {
                    toastMessage.messageError(getResources().getString(R.string.wrong)+""+response.code(), true);
                }
            }
            @Override
            public void onFailure(Call<SetPasswordModel> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.d("FAA", t + "");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       /// startActivity(new Intent(ChangePassword.this, Setting.class));
      //  finish();
    }


    class FormValidator {
        public boolean isValid() {
            return  validatePassword();
        }


        public boolean validatePassword() {
            if (newPasswordSet.getEditText().getText().toString().isEmpty() || rePasswordSet.getEditText().getText().toString().isEmpty()) {
                return false;
            } else {
                return true;
            }
        }

    }
}