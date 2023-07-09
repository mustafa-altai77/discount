package com.example.discaount.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageSliderOffResponse {
    @SerializedName("data")
    List<ImageSliderOffer> sliderOffers;
    private String message;
    private Boolean success;

    public List<ImageSliderOffer> getSliderOffers() {
        return sliderOffers;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }
}
