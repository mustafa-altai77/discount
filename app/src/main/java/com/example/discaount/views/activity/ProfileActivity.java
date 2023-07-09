package com.example.discaount.views.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.discaount.R;
import com.example.discaount.utils.BaseActivity;
import com.example.discaount.utils.SharedPref;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.discaount.utils.SharedPref.ADDRESS;
import static com.example.discaount.utils.SharedPref.AGE;
import static com.example.discaount.utils.SharedPref.F_NAME;
import static com.example.discaount.utils.SharedPref.IMAGES;
import static com.example.discaount.utils.SharedPref.PHONE;
import static com.example.discaount.utils.SharedPref.SHARED_PREF_NAME;
import static com.example.discaount.utils.SharedPref.TOKEN;
import static com.example.discaount.utils.SharedPref.mCtx;

public class ProfileActivity extends BaseActivity {

    TextView full_name_edittext_edit, addressProfile2, ageProfile, mobile_edittext_edit;
    ImageView imageEdit;

    CircleImageView profile_image_edit;
    private Uri mImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        full_name_edittext_edit = findViewById(R.id.full_name_edittext_edit);
        imageEdit = findViewById(R.id.imageEdit);
        profile_image_edit = findViewById(R.id.profile_image_edit);
        mobile_edittext_edit = findViewById(R.id.mobile_edittext_edit);
        addressProfile2 = findViewById(R.id.addressProfile2);
        ageProfile = findViewById(R.id.ageProfile);


        SharedPreferences sharedPref = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String fullName = sharedPref.getString(F_NAME, "name");
        final String phone = sharedPref.getString(PHONE, "phone");
        final String address = sharedPref.getString(ADDRESS, "address");
        final String age = sharedPref.getString(AGE, "age");
        mobile_edittext_edit.setText(phone);
        full_name_edittext_edit.setText(fullName);
        ageProfile.setText(age);
        addressProfile2.setText(address);

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String image = sharedPreferences.getString(IMAGES, "image");

        if (!sharedPreferences.equals("image")) {
            profile_image_edit.setImageURI(Uri.parse(image));
        }
        imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSelect();
            }
        });
    }

    public void imageSelect() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                1);
    }

    //TODO 3. Save the image URI:
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    SharedPref.getInstance(ProfileActivity.this).ClearImage();
                    mImageUri = data.getData();
                    grantUriPermission(this.getPackageName(), mImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                    getContentResolver().takePersistableUriPermission(mImageUri, takeFlags);

                    SharedPref.getInstance(ProfileActivity.this).storeImage(String.valueOf(mImageUri));
                    profile_image_edit.setImageURI(mImageUri);
                    profile_image_edit.invalidate();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ProfileActivity.this, Setting.class);
        startActivity(intent);
        finish();
    }
}