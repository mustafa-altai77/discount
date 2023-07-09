package com.example.discaount.data.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {
    @SerializedName("success")
    boolean success;
    @SerializedName("data")
    Data data;
    @SerializedName("message")
    Message message;

    @SerializedName("errors")
    Errors errors;
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Message getMessage() {
        return message;
    }

    public Errors getErrors() {
        return errors;
    }

    public boolean getSuccess() {
        return success;
    }
}
