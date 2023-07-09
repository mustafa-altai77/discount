package com.example.discaount.data.model;

import android.annotation.SuppressLint;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;
import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

public class Medicine{
    private String id;
    private String name;
    @SerializedName("icon")
    private String imageUrl;

    public Medicine(String id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "SubCategory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicine medicine = (Medicine) o;
        return
                getId().equals(medicine.getId()) &&
                getName().equals(medicine.getName()) &&
                getImageUrl().equals(medicine.getImageUrl());
    }

    public static DiffUtil.ItemCallback<Medicine> itemCallback = new DiffUtil.ItemCallback<Medicine>() {
        @Override
        public boolean areItemsTheSame(@NonNull Medicine oldItem, @NonNull Medicine newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Medicine oldItem, @NonNull Medicine newItem) {
            return oldItem.equals(newItem);
        }
    };
//    @BindingAdapter("imageUrl")
//    public static void loadImage(ImageView imageView, String imageUrl) {
//        Glide.with(imageView)
//                .load(imageUrl)
//                .fitCenter()
//                .into(imageView);
//    }
    @BindingAdapter("android:medicineImage")
    public static void loadImage(ImageView imageView, String imageUrl){
        Glide.with(imageView).load("https://shayoub.com/discount/public/icon/Medicinesections/"+imageUrl)
                .fitCenter()
                .into(imageView);
    }
}
