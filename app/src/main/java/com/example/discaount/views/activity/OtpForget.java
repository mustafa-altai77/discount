package com.example.discaount.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.example.discaount.R;
import com.example.discaount.data.model.UserResponse;
import com.example.discaount.data.model.UserResponse2;
import com.example.discaount.data.network.ApiClient;
import com.example.discaount.data.network.ApiInterface;
import com.example.discaount.utils.BaseActivity;
import com.example.discaount.utils.SharedPref;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpForget extends BaseActivity {
    PinEntryEditText otpView;
    TextView resendOtp;
    String Number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_forget);
        otpView = findViewById(R.id.otp_view);
        resendOtp = findViewById(R.id.resend_otp);
        Number = getIntent().getStringExtra("phone");
        if (Number == null) {
            startActivity(new Intent(OtpForget.this, MainActivity.class));
            finish();
        } else {
            resend(Number);
        }
        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resend(Number);
            }
        });
        if (otpView != null) {
            otpView.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if (str.toString().length() != 4) {
                    } else {
                        final String Number1 = getIntent().getStringExtra("phone");
                        if (Number1 != null) {
                            verifyCode(Number1, str.toString());
                        }
                    }
                }
            });
        }
    }

    private void resend(String number) {
        loadingDialog.startLoadingDialog();
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<UserResponse2> call = apiService.resendOtp(number);
        call.enqueue(new Callback<UserResponse2>() {
            @Override
            public void onResponse(Call<UserResponse2> call, Response<UserResponse2> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()) {
                    toastMessage.messageError(getResources().getString(R.string.otp_sended), false);
                    final String accessToken = response.body().getData().getToken();
                    SharedPref.getInstance(OtpForget.this).storeToken(accessToken);

                } else {
                    toastMessage.messageError(getResources().getString(R.string.unvalid), true);
                }
            }

            @Override
            public void onFailure(Call<UserResponse2> call, Throwable t) {
                loadingDialog.dismissDialog();
                toastMessage.messageError(getResources().getString(R.string.no_internet), true);
                Log.d("StEr", "" + t);
            }
        });
    }

    private void verifyCode(String phone, String otp) {
        final String fire_token = FirebaseInstanceId.getInstance().getToken();
        loadingDialog.startLoadingDialog();
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<UserResponse> call = apiService.confirmMobile(otp, fire_token, phone);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()) {
                    final String accessToken = response.body().getData().getToken();
                    SharedPref.getInstance(OtpForget.this).storeToken(accessToken);
                    Intent intent = new Intent(OtpForget.this, SetPassword.class);
                    intent.putExtra("phone", phone);
                    startActivity(intent);
                    finish();

                } else {
                    toastMessage.messageError(getResources().getString(R.string.invalid_otp) + response.code(), true);
                    otpView.setText("");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                toastMessage.messageError(getResources().getString(R.string.no_internet) + t, true);
            }
        });
    }

}