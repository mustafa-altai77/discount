package com.example.discaount.data.model;

import com.google.gson.annotations.SerializedName;

public class ChangePassModel {
    @SerializedName("phone")
    String phone;

    @SerializedName("old_password")
    String old_password;

    @SerializedName("new_password")
    String new_password;

    public ChangePassModel(String phone, String old_password, String new_password) {
        this.phone = phone;
        this.old_password = old_password;
        this.new_password = new_password;
    }
}
