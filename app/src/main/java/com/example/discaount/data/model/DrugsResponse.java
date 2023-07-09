package com.example.discaount.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrugsResponse {
    @SerializedName("data")
    List<Drug> drugs;
    private Message message;
    private Boolean success;
    public List<Drug> getDrugs(){
        return drugs;
    }
    public void setDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }
}
