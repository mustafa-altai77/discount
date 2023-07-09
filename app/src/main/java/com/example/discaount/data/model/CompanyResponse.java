package com.example.discaount.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CompanyResponse {
    @SerializedName("data")
    List<Companies> companies;
    private Message message;
    private Boolean success;

    public List<Companies> getCompanies() {
        return companies;
    }

    public Message getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }
}
