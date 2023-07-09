package com.example.discaount.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchRespons {
    @SerializedName("data")
    List<Product> products;
    private Message message;
    private Boolean success;


    public List<Product> getProducts() {
        return products;
    }

    public Message getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }


}
