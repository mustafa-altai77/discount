package com.example.discaount.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ImageResponse {
    @SerializedName("data")
    ArrayList<String> path;

    private String messageResult;
    @SerializedName("errors")
    @Expose
    private String errors = null;
    @SerializedName("success")
    @Expose
    private boolean success;


    public ArrayList<String> getPath() {
        return path;
    }

    public String getMessageResult() {
        return messageResult;
    }

    public String getErrors() {
        return errors;
    }

    public boolean isSuccess() {
        return success;
    }
}
