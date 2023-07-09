package com.example.discaount.data.model;

import com.google.gson.annotations.SerializedName;

public class HollResponseOrder {
    String note;
    String address;
    @SerializedName("long")
    double longt;
    double lat;
    String phone;
    String user_id;
    String updated_at;
    String created_at;
    String id;

    public String getNote() {
        return note;
    }

    public String getAddress() {
        return address;
    }

    public double getLongt() {
        return longt;
    }

    public double getLat() {
        return lat;
    }

    public String getPhone() {
        return phone;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getId() {
        return id;
    }
}
