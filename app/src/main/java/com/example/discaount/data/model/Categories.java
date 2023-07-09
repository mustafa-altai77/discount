package com.example.discaount.data.model;

import android.annotation.SuppressLint;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;
import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Categories {
    private String id;
    private String name;
    @SerializedName("icon")
    private String imageUrl;
    @SerializedName("departments")
    List<SubCategory> subCategories;

    public Categories(String id, String name, String imageUrl ,List<SubCategory> subCategories){
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.subCategories = subCategories;
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
    public String getImageUrl() {
        return imageUrl;
    }
    public List<SubCategory> getSubCategories() {
        return subCategories;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    @Override
    public String toString() {
        return "SubCategory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}'; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categories categories = (Categories) o;
        return
                getId().equals(categories.getId()) &&
                        getName().equals(categories.getName()) &&
                        getImageUrl().equals(categories.getImageUrl());
    }
    public static DiffUtil.ItemCallback<Categories> itemCallback = new DiffUtil.ItemCallback<Categories>() {
        @Override
        public boolean areItemsTheSame(@NonNull Categories oldItem, @NonNull Categories newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
        @SuppressLint("DiffUtilEquals")

        @Override
        public boolean areContentsTheSame(@NonNull Categories oldItem, @NonNull Categories newItem) {
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
    @BindingAdapter("android:categoryIcon")
    public static void loadImage(ImageView imageView, String imageUrl){
        Glide.with(imageView).load("https://shayoub.com/discount/public/icon/Categories/"+imageUrl)
                .fitCenter()
                .into(imageView);
    }
}
