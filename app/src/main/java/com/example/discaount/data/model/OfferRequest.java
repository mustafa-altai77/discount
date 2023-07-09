package com.example.discaount.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferRequest {
    String phone;
    String note;
    String address;
    @SerializedName("long")
    @Expose
    Double longt;
    Double lat;
    int offer_id;
    int amount;

    public OfferRequest(String phone, String note, String address, Double longt, Double lat, int offer_id, int amount) {
        this.phone = phone;
        this.note = note;
        this.address = address;
        this.longt = longt;
        this.lat = lat;
        this.offer_id = offer_id;
        this.amount = amount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongt() {
        return longt;
    }

    public void setLongt(Double longt) {
        this.longt = longt;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public int getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(int offer_id) {
        this.offer_id = offer_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
