package com.example.discaount.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResponse {
    private Boolean success;
    @SerializedName("data")
    List<MyOrder> myOrders;
    private Message message;
    public List<MyOrder> myOrders(){
        return myOrders;
    }
    public void setMyOrders(List<MyOrder> myOrders) {
        this.myOrders = myOrders;
    }

    public List<MyOrder> getMyOrders() {
        return myOrders;
    }

    public Message getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }
}