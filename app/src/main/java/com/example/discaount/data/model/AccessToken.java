package com.example.discaount.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccessToken {

    @SerializedName("token")
    @Expose
    private String getAccessToken;
    private String name;

    public String getName() {
        return name;
    }

    public String getGetAccessToken() {
        return getAccessToken;
    }
}