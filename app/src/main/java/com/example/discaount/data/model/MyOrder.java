package com.example.discaount.data.model;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.SerializedName;

public class MyOrder {
    int id;
    @SerializedName("username")
    String name;
    int drugs_count;
    String status_id;
    int prescriptions_count;
    int products_count;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDrugs_count() {
        return drugs_count;
    }

    public String getStatus_id() {
        return status_id;
    }

    public int getPrescriptions_count() {
        return prescriptions_count;
    }

    public int getProducts_count() {
        return products_count;
    }

    public static DiffUtil.ItemCallback<MyOrder> getItemCallback() {
        return itemCallback;
    }

    public static DiffUtil.ItemCallback<MyOrder> itemCallback = new DiffUtil.ItemCallback<MyOrder>() {
        @Override
        public boolean areItemsTheSame(@NonNull MyOrder oldItem, @NonNull MyOrder newItem) {
            return oldItem.getId() == (newItem.getId());
        }

        @SuppressLint("DiffUtilEquals")

        @Override
        public boolean areContentsTheSame(@NonNull MyOrder oldItem, @NonNull MyOrder newItem) {
            return oldItem.equals(newItem);
        }
    };
}
