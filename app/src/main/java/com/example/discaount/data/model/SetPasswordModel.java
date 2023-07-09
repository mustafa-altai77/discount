package com.example.discaount.data.model;

import com.google.gson.annotations.SerializedName;

public class SetPasswordModel {
    @SerializedName("phone")
   String phone;

    @SerializedName("new_password")
    String new_password;

    public SetPasswordModel(String phone, String new_password) {
        this.phone = phone;
        this.new_password = new_password;
    }
}
