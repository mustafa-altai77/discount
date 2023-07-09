package com.example.discaount.views.activity;

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
import com.example.discaount.data.model.OfferRequest;
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

public class ChangePassword extends BaseActivity {
    ImageView imgFl;
    TextInputLayout currentPassword, newPassword, rePassword;
    Button btnChangePassword;
    TextView tvForgetPassword;
    SharedPreferences sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        imgFl = findViewById(R.id.imgFl);
        currentPassword = findViewById(R.id.currentPassword);
        newPassword = findViewById(R.id.newPassword);
        rePassword = findViewById(R.id.rePassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        tvForgetPassword = findViewById(R.id.tvForgetPassword);
        String lang = mySharePreference.getLanguage();
        if (lang.equals("ar")) {
            imgFl.setRotation(0);
        }
        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangePassword.this, ForgetPassword.class));
            }
        });
        imgFl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangePassword.this, Setting.class));
                finish();
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentPass = currentPassword.getEditText().getText().toString();
                final String newP = newPassword.getEditText().getText().toString();
                final String reP= rePassword.getEditText().getText().toString();
                FormValidator formValidator = new FormValidator();
                boolean isValidForm = formValidator.isValid();
                if (isValidForm) {
                    if (!newP.equals(reP))
                    {
                        toastMessage.messageError(getResources().getString(R.string.pass_match), true);
                    }
                    else {
                        changePassword(currentPass, newP);
                    }
                }
                else {
                    toastMessage.messageError(getResources().getString(R.string.fill_all_required), true);
                    loadingDialog.dismissDialog();
                }
            }
        });
    }

    private void changePassword(String currentPassword, String newPassword) {
        sharedPreference = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String token = sharedPreference.getString(TOKEN, "token");
        final String phone = sharedPreference.getString(PHONE, "phone");
        loadingDialog.startLoadingDialog();
        ChangePassModel passModel = new ChangePassModel(phone, currentPassword,newPassword);

        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<ChangePassModel> call = apiService.changePassword(token, passModel);
        call.enqueue(new Callback<ChangePassModel>() {
            @Override
            public void onResponse(Call<ChangePassModel> call, Response<ChangePassModel> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()) {
                    toastMessage.messageError(getResources().getString(R.string.passChange), false);
                    SharedPref.getInstance(ChangePassword.this).logout();
                    Intent in = new Intent(ChangePassword.this, LoginActivity.class);
                    startActivity(in);
                    finish();
                } else {
                    toastMessage.messageError(getResources().getString(R.string.wrong)+""+response.code(), true);
                }
            }

            @Override
            public void onFailure(Call<ChangePassModel> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.d("FAA", t + "");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ChangePassword.this, Setting.class));
        finish();
    }


    class FormValidator {
        public boolean isValid() {
            return validateOldPassword() & validatePassword();
        }

        public boolean validateOldPassword() {
            if (currentPassword.getEditText().toString().isEmpty()) {
                return false;
            } else {
                return true;
            }
        }

        public boolean validatePassword() {
            if (newPassword.getEditText().getText().toString().isEmpty() || rePassword.getEditText().getText().toString().isEmpty()) {
                return false;
            } else {
                return true;
            }
        }

    }
}