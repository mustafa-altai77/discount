package com.example.discaount.views.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.example.discaount.R;
import com.example.discaount.data.model.UserProfile;
import com.example.discaount.data.model.UserResponse;
import com.example.discaount.data.model.UserResponse2;
import com.example.discaount.data.network.ApiClient;
import com.example.discaount.data.network.ApiInterface;
import com.example.discaount.utils.BaseActivity;
import com.example.discaount.utils.LoadingDialog;
import com.example.discaount.utils.SharedPref;
import com.example.discaount.utils.ToastMessage;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.discaount.utils.SharedPref.SHARED_PREF_NAME;
import static com.example.discaount.utils.SharedPref.TOKEN;
import static com.example.discaount.utils.SharedPref.mCtx;

public class OtpActivity extends BaseActivity {

    PinEntryEditText otpView;
    TextView otp_label;
    TextView resendOtp;
    String Number, first_name, password, age, address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otpView = findViewById(R.id.otp_view);
        otp_label = findViewById(R.id.otp_label);
        resendOtp = findViewById(R.id.resend_otp);
      //  ButterKnife.bind(this);
        first_name = getIntent().getStringExtra("name");
        Number = getIntent().getStringExtra("phone");
        password = getIntent().getStringExtra("password");
        age = getIntent().getStringExtra("age");
        address = getIntent().getStringExtra("address");
        registerRequest();
        if (Number == null) {
            otp_label.setText(R.string.enter_sms_otp);

            resendOtp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resend(Number);
                }
            });
        } else {
            resendOtp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resend(Number);
                }
            });
        }

        if (otpView != null) {
            otpView.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if (str.toString().length() != 4) {
//                        Toast.makeText(getApplicationContext(), R.string.fill_all_required,
//                                Toast.LENGTH_SHORT).show();
                    } else {
                        final String Number = getIntent().getStringExtra("phone");
                        if (Number != null) {

                            //  FirebaseApp.initializeApp(OtpActivity.this);
                            getToken(Number, str.toString());
                        }

//                        String idNumber1 = getIntent().getStringExtra("idNumber");
//                        if (idNumber1 != null) {
//                            confirmMobile();//str.toString());
//                        }

                    }
                }
            });
        }
        //   Log.i("ANARASTA", FirebaseInstanceId.getInstance().getToken());
    }

    private void resend(String number) {
        loadingDialog.startLoadingDialog();
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<UserResponse2> call = apiService.resendOtp(number);

        call.enqueue(new Callback<UserResponse2>() {
            @Override
            public void onResponse(Call<UserResponse2> call, Response<UserResponse2> response) {
                if (response.isSuccessful()) {
                    toastMessage.messageError(getResources().getString(R.string.otp_sended), false);

                    final String accessToken = response.body().getData().getToken();
                    SharedPref.getInstance(OtpActivity.this).storeToken(accessToken);

                }
            }

            @Override
            public void onFailure(Call<UserResponse2> call, Throwable t) {

                // alert.showWarningDialog();
            }
        });
    }

    private void getToken(String phone, String otp) {
        final String fire_token = FirebaseInstanceId.getInstance().getToken();
        //  Toast.makeText(this, phone + "  " + fire_token + "", Toast.LENGTH_SHORT).show();

        loadingDialog.startLoadingDialog();
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<UserResponse> call = apiService.confirmMobile(otp, fire_token, phone);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()) {

                    final String accessToken = response.body().getData().getToken();
                    SharedPref.getInstance(OtpActivity.this).storeToken(accessToken);
//                    SharedPref.getInstance(OtpActivity.this).storeUserID(first_name, phone, address, age);
//                    Intent intent = new Intent(OtpActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
                    getData();

                } else {
                    toastMessage.messageError(getResources().getString(R.string.invalid_otp), true);
                    otpView.setText("");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                toastMessage.messageError(getResources().getString(R.string.no_internet), true);
            }
        });
    }

    private void registerRequest() {

        loadingDialog.startLoadingDialog();

        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<UserResponse> call = apiService.register(password, Number, address, first_name, age);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()) {// 200 OK
                    toastMessage.messageError(getResources().getString(R.string.otp_sended), false);
                } else {
                    toastMessage.messageError(getResources().getString(R.string.the_phone_registred), true);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
            }
        });
    }

    private void getData() {
        loadingDialog.startLoadingDialog();
        SharedPreferences sharedPref = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String token = sharedPref.getString(TOKEN, "token");
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<UserProfile> call = apiService.getMyData(token);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                loadingDialog.dismissDialog();
                if (response.isSuccessful()) {
                    String fullName = response.body().getName();
                    String phone = response.body().getPhone();
                    String age = response.body().getAge();
                    String address = response.body().getAddress();
                    SharedPref.getInstance(OtpActivity.this).storeUserID(fullName, phone, address, age);
                    Intent intent = new Intent(OtpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    toastMessage.messageError(getResources().getString(R.string.error), true);
                    Intent intent = new Intent(OtpActivity.this, SplashActivity.class);
                    startActivity(intent);
                    finish();

                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                loadingDialog.dismissDialog();
                toastMessage.messageError(getResources().getString(R.string.no_internet), true);
            }
        });
    }

}