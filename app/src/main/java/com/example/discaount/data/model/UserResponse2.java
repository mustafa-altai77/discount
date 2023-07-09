package com.example.discaount.data.model;


import com.google.gson.annotations.SerializedName;

public class UserResponse2 {
    @SerializedName("success")
    boolean success;
    @SerializedName("data")
    Data data;
    @SerializedName("message")
    String message;
    @SerializedName("errors")
    Errors errors;
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public Errors getErrors() {
        return errors;
    }

    public boolean getSuccess() {
        return success;
    }
}
