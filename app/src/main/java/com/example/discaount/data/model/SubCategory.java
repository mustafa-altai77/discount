package com.example.discaount.data.model;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

public class SubCategory {
    private String id;
    @SerializedName("name")
    private String name;
    private String description;
    private String icon;
     Pivot pivot;

    public Pivot getPivot() {
        return pivot;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getIcon() {
        return icon;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public static void setItemCallback(DiffUtil.ItemCallback<SubCategory> itemCallback) {
        SubCategory.itemCallback = itemCallback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubCategory subCategory = (SubCategory) o;
        return
                getId().equals(subCategory.getId()) &&
                        getName().equals(subCategory.getName()) &&
                        getIcon().equals(subCategory.getIcon());
    }

    public static DiffUtil.ItemCallback<SubCategory> itemCallback = new DiffUtil.ItemCallback<SubCategory>() {
        @Override
        public boolean areItemsTheSame(@NonNull SubCategory oldItem, @NonNull SubCategory newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull SubCategory oldItem, @NonNull SubCategory newItem) {
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
    @BindingAdapter("android:subcategoryImage")
    public static void loadImage(ImageView imageView, String imageUrl){
        Glide.with(imageView).load("http://primarykeysd.com/PharmacyApp/VoyagerTesting/public/storage/"+imageUrl)
                .fitCenter()
                .into(imageView);
    }
}
