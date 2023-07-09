package com.example.discaount.data.model;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class Companies {
    int id;
    String name;
    String description;
    String logo;
    int active;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLogo() {
        return logo;
    }

    public int getActive() {
        return active;
    }
    public String toString() {
        return "SubCategory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + logo + '\'' +
                '}'; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Companies companies = (Companies) o;
        return
                getId() == (companies.getId()) &&
                        getName().equals(companies.getName()) &&
                        getLogo().equals(companies.getLogo());
    }
    public static DiffUtil.ItemCallback<Companies> itemCallback = new DiffUtil.ItemCallback<Companies>() {
        @Override
        public boolean areItemsTheSame(@NonNull Companies oldItem, @NonNull Companies newItem) {
            return oldItem.getId()==(newItem.getId());
        }
        @SuppressLint("DiffUtilEquals")

        @Override
        public boolean areContentsTheSame(@NonNull Companies oldItem, @NonNull Companies newItem) {
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
    @BindingAdapter("android:CompanyImage")
    public static void loadImage(CircleImageView imageView, String imageUrl){
        Glide.with(imageView).load("https://shayoub.com/discount/public/icon/companies/"+imageUrl)
                .fitCenter()
                .into(imageView);
    }
}
