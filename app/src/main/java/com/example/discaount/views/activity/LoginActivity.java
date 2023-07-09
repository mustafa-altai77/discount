package com.example.discaount.views.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.discaount.R;
import com.example.discaount.data.model.Errors;
import com.example.discaount.data.model.UserProfile;
import com.example.discaount.data.model.UserResponse2;
import com.example.discaount.data.network.ApiClient;
import com.example.discaount.data.network.ApiInterface;
import com.example.discaount.utils.BaseActivity;
import com.example.discaount.utils.LoadingDialog;
import com.example.discaount.utils.SharedPref;
import com.example.discaount.utils.ToastMessage;
import com.example.discaount.utils.Vibration;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hbb20.CountryCodePicker;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    EditText edMobile;

    TextInputLayout edPassword;
    TextView tvRegisterNow, tvSignAsGuest, tvForgetPassword2;
    ProgressDialog dialog;
    Button btnSignIn;
    CountryCodePicker picker;

    TextView sw_remember;
    Vibration vibration;
    Animation shake;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edMobile = findViewById(R.id.conMobile);
        edPassword = findViewById(R.id.tilPassword);
        tvSignAsGuest = findViewById(R.id.tvSignAsGuest);
        tvRegisterNow = findViewById(R.id.tvRegisterNow);
        btnSignIn = findViewById(R.id.btnSignIn);
        picker = findViewById(R.id.spDialcode);
        sw_remember = findViewById(R.id.sw_remember);
        tvForgetPassword2 = findViewById(R.id.tvForgetPassword2);
        vibration = new Vibration(this);
        dialog = new ProgressDialog(this);
        shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake);
        final String fire_token = FirebaseInstanceId.getInstance().getToken();
        Log.d("ALI", "" + fire_token);
        sw_remember.setMovementMethod(LinkMovementMethod.getInstance());
        init();
        picker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                String s = picker.getSelectedCountryCode();
                String s1 = picker.getSelectedCountryName();
                //  Toast.makeText(LoginActivity.this, "" + s + "\n" + s1, Toast.LENGTH_SHORT).show();
            }
        });
        tvForgetPassword2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetPassword.class));
                finish();
            }
        });
    }

    private void init() {

        tvRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(intent);
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();

            }
        });
        tvSignAsGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        edMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // countText.setText(9 - editable.toString().length() + " /9");
            }
        });
//        forget_password_text_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this,
//                        ForgetPasswordActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                loadingDialog.startLoadingDialog();
                final String phone = edMobile.getText().toString().trim();
                final String password = edPassword.getEditText().getText().toString().trim();
                FormValidator formValidator = new FormValidator();
                boolean isValidForm = formValidator.isValid();
                if (isValidForm) {
                    getLogin(phone, password);
                } else {
                    toastMessage.messageError(getResources().getString(R.string.fill_all_required), true);
                    loadingDialog.dismissDialog();
                }
            }
        });
    }

    private void getLogin(String phone, String password) {
        final String fire_token = FirebaseInstanceId.getInstance().getToken();
        //Log.d("ALI", "" + fire_token);
        String allPhone = picker.getSelectedCountryCode() + phone;
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<UserResponse2> call = apiService.getLogin(password, allPhone, fire_token);
        call.enqueue(new Callback<UserResponse2>() {
            @Override
            public void onResponse(Call<UserResponse2> call, Response<UserResponse2> response) {
                loadingDialog.dismissDialog();

                if (response.isSuccessful()) { // 200 OK
                    String accessToken = response.body().getData().getToken();
                    getProfile(accessToken);
                } else {
                    Errors errors = new Errors();
                    String err = errors.getAr();
                    vibration.vibration();
                    edMobile.startAnimation(shake);
                    edPassword.startAnimation(shake);
                    edMobile.setText("");
                    edPassword.getEditText().setText("");
                    toastMessage.messageError(getResources().getString(R.string.error_login), true);

                }
            }

            @Override
            public void onFailure(Call<UserResponse2> call, Throwable t) {
                loadingDialog.dismissDialog();
                dialog.dismiss();
                Toast.makeText(LoginActivity.this, "" + t, Toast.LENGTH_LONG).show();
                Log.d("sdssa", t + "");
            }
        });
    }

    class FormValidator {
        public boolean isValid() {
            return validateIdNumber() & validatePassword();
        }

        public boolean validateIdNumber() {
            if (edMobile.getText().toString().isEmpty() || edMobile.getText().toString().length() < 9) {
                return false;
            } else {
                return true;
            }
        }

        public boolean validatePassword() {
            if (edPassword.getEditText().getText().toString().isEmpty() || edPassword.getEditText().getText().toString().length() < 3) {
                return false;
            } else {
                return true;
            }
        }
    }

    private void getProfile(String token) {
        loadingDialog.startLoadingDialog();
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
                    toastMessage.messageError(getResources().getString(R.string.hello) + " " + fullName, false);
                    SharedPref.getInstance(LoginActivity.this).storeToken(token);
                    SharedPref.getInstance(LoginActivity.this).storeUserID(fullName, phone, address, age);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    toastMessage.messageError(getResources().getString(R.string.error), true);
                    Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.d("whatTHEFUCK", t + "");
                toastMessage.messageError(getResources().getString(R.string.no_internet), true);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }
}