package com.example.discaount.data.model;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MedicineResponse {
    @SerializedName("data")
    List<Medicine> medicines;
    private Message message;
    private Boolean success;
    public List<Medicine> getMedicine() {
        return medicines;
    }
    public static DiffUtil.ItemCallback<MedicineResponse> itemCallback = new DiffUtil.ItemCallback<MedicineResponse>() {
        @Override
        public boolean areItemsTheSame(@NonNull MedicineResponse oldItem, @NonNull MedicineResponse newItem) {
            return oldItem.getMedicine().equals(newItem.getMedicine());
        }
        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull MedicineResponse oldItem, @NonNull MedicineResponse newItem) {
            return oldItem.getMedicine().equals(newItem);
        }
    };

}
