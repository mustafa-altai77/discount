package com.example.discaount.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderAllResponse {
    @SerializedName("success")
    boolean success;
    @SerializedName("data")
    MyOrder27 myOrders;
    @SerializedName("message")
    Message message;

    public MyOrder27 getMyOrders() {
        return myOrders;
    }

    public boolean isSuccess() {
        return success;
    }

    public Message getMessage() {
        return message;
    }
}