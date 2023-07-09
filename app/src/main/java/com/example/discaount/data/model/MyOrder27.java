package com.example.discaount.data.model;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.SerializedName;

public class MyOrder27 {
    String note;
    String address;
    @SerializedName("long")
    double langu;
    double lat;
    int user_id;
    String phone;
    String updated_at;
    String created_at;
    int id;

    public String getNote() {
        return note;
    }

    public String getAddress() {
        return address;
    }

    public double getLangu() {
        return langu;
    }

    public double getLat() {
        return lat;
    }

    public String getPhone() {
        return phone;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public static DiffUtil.ItemCallback<MyOrder27> getItemCallback() {
        return itemCallback;
    }

    public static DiffUtil.ItemCallback<MyOrder27> itemCallback = new DiffUtil.ItemCallback<MyOrder27>() {
        @Override
        public boolean areItemsTheSame(@NonNull MyOrder27 oldItem, @NonNull MyOrder27 newItem) {
            return oldItem.getId() == (newItem.getId());
        }

        @SuppressLint("DiffUtilEquals")

        @Override
        public boolean areContentsTheSame(@NonNull MyOrder27 oldItem, @NonNull MyOrder27 newItem) {
            return oldItem.equals(newItem);
        }
    };
}
