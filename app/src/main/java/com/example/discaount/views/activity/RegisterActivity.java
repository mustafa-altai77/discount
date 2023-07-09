package com.example.discaount.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.discaount.R;
import com.example.discaount.utils.BaseActivity;
import com.example.discaount.utils.ToastMessage;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hbb20.CountryCodePicker;

public class RegisterActivity extends BaseActivity {

    TextInputLayout editTextName, editTextAddress, editTextAge, editTextPassword;
    EditText editTextPhone;
    Button btnRegister;
    TextView tvSignIn;
    CheckBox checkBox;
    ProgressBar progressBar;
    CountryCodePicker picker;
    String code;
    Boolean checked = false;
    ToastMessage toastMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final String fire_token = FirebaseInstanceId.getInstance().getToken();
        Log.d("ANARASTA", "" + fire_token);
        editTextName = findViewById(R.id.lay_name);
        editTextPhone = findViewById(R.id.phone);
        editTextAddress = findViewById(R.id.address);
        editTextAge = findViewById(R.id.titl_age);
        editTextPassword = findViewById(R.id.password);
        btnRegister = findViewById(R.id.btnRegister);
        tvSignIn = findViewById(R.id.tvSignIn);
        progressBar = findViewById(R.id.progressBar);
        picker = findViewById(R.id.spDialcode);
        checkBox = findViewById(R.id.checkBox);
        code = picker.getSelectedCountryCode();
        toastMessage = new ToastMessage(this);
        checkBox.setMovementMethod(LinkMovementMethod.getInstance());


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox.isChecked()) {
                    checked = true;
                } else checked = false;
                final String first_name = editTextName.getEditText().getText().toString();
                final String phone = code + "" + editTextPhone.getText().toString();
                final String password = editTextPassword.getEditText().getText().toString();
                final String age = editTextAge.getEditText().getText().toString();
                final String address = editTextAddress.getEditText().getText().toString();
                String message = getResources().getString(R.string.fill_field);
                String message1 = getResources().getString(R.string.agree);
                String messagePhone = getResources().getString(R.string.errer_phone_message);
                String messageAddress = getResources().getString(R.string.error_address);

                if (first_name.isEmpty()) {
                    editTextName.setError(message);
                    return;
                } else {
                    editTextName.setError(null);
                }

                if (editTextPhone.getText().toString().length() < 9) {
                    editTextPhone.setError(messagePhone);
                    return;
                } else {
                    editTextPhone.setError(null);
                }
                if (password.isEmpty()) {
                    editTextPassword.setError(message);
                    return;
                } else {
                    editTextPassword.setError(null);
                }
                if (age.isEmpty()) {
                    editTextAge.setError(message);
                    return;
                } else {
                    editTextAge.setError(null);
                }

                if (checked == false) {
                    toastMessage.messageError(message1,true);
                    return;
                }
                if (address.isEmpty() || address.length() < 9) {
                    editTextAddress.setError(messageAddress);
                    return;
                } else {
                    editTextAddress.setError(null);
                }

                Intent intent = new Intent(getApplicationContext(), OtpActivity.class);
                intent.putExtra("name", first_name.trim());
                intent.putExtra("phone", phone);
                intent.putExtra("password", password);
                intent.putExtra("age", age.trim());
                intent.putExtra("address", address.trim());
                startActivity(intent);
                finish();
              //  Toast.makeText(RegisterActivity.this, ""+editTextPhone.getText().toString().length(), Toast.LENGTH_SHORT).show();
            }
        });
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}