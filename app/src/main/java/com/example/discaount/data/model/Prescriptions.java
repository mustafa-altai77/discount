package com.example.discaount.data.model;

import com.google.gson.annotations.SerializedName;

public class Prescriptions {
    String images;
    @SerializedName("recommendation")
    Boolean advice;

    public Prescriptions(String images, Boolean advice) {
        this.images = images;
        this.advice = advice;
    }

    public void setAdvice(Boolean advice) {
        this.advice = advice;
    }

    public String getImages() {
        return images;
    }

    public Boolean getAdvice() {
        return advice;
    }

    public void setImages(String images) {
        this.images = images;
    }
}