package com.example.discaount.data.model;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DepartmentResponse {
    @SerializedName("data")
   List<Categories> categories;
    private String message;
    private Boolean success;


    public List<Categories> getCategories() {
        return categories;

    }


    public static DiffUtil.ItemCallback<DepartmentResponse> itemCallback = new DiffUtil.ItemCallback<DepartmentResponse>() {
        @Override
        public boolean areItemsTheSame(@NonNull DepartmentResponse oldItem, @NonNull DepartmentResponse newItem) {
            return oldItem.getCategories().equals(newItem.getCategories());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull DepartmentResponse oldItem, @NonNull DepartmentResponse newItem) {
            return oldItem.getCategories().equals(newItem);
        }
    };

}
