package com.example.discaount.data.model;

import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("token")
    String token;
    @SerializedName("user")
    UserProfile user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserProfile getUser() {
        return user;
    }
}
