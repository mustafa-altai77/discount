package com.example.discaount.data.model;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Drug {
    private int id;
    private String drugs_name;
    private String Pharmaceuticalform;
    private String description;
    private int status;
    private double price;
    @SerializedName("icons")
    @Expose
    private String imageUrl;
    private int count;
    private String Reasonuse;
    private String sideeffects;
    private String Dosing;
    private String activesubstance;
    private String Interactions;
    private String InstancesUrl;
    private String InstanceCount;
    private String company_name;
    String Pharmaceuticalform_name;
    String company_logo;



    public String getCompany_logo() {
        return company_logo;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getPharmaceuticalform_name() {
        return Pharmaceuticalform_name;
    }

    public void setPharmaceuticalform_name(String pharmaceuticalform_name) {
        Pharmaceuticalform_name = pharmaceuticalform_name;

    }

    public static void setItemCallback(DiffUtil.ItemCallback<Drug> itemCallback) {
        Drug.itemCallback = itemCallback;
    }

    public int getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDrugs_name() {
        return drugs_name;
    }

    public void setDrugs_name(String drugs_name) {
        this.drugs_name = drugs_name;
    }

    public String getPharmaceuticalform() {
        return Pharmaceuticalform;
    }

    public void setPharmaceuticalform(String pharmaceuticalform) {
        Pharmaceuticalform = pharmaceuticalform;
    }

    public String getDescription() {
        return description;
    }

    public String getCompany_name() {
        return company_name;
    }

    public static DiffUtil.ItemCallback<Drug> getItemCallback() {
        return itemCallback;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return "Drug{" +
                "id=" + id +
                ", drugs_name='" + drugs_name + '\'' +
                ", Pharmaceuticalform='" + Pharmaceuticalform + '\'' +
                ", price =" + price +
                ", ImageView='" + imageUrl + '\'' +
                ", count=" + count +
                ", Reasonuse='" + Reasonuse + '\'' +
                ", sideeffects='" + sideeffects + '\'' +
                ", Dosing='" + Dosing + '\'' +
                ", activesubstance='" + activesubstance + '\'' +
                ", Interactions='" + Interactions + '\'' +
                ", InstancesUrl='" + InstancesUrl + '\'' +
                ", InstanceCount='" + InstanceCount + '\'' +
                '}';
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }


    public void setCount(int count) {
        this.count = count;
    }

    public String getReasonuse() {
        return Reasonuse;
    }

    public void setReasonuse(String reasonuse) {
        Reasonuse = reasonuse;
    }

    public String getSideeffects() {
        return sideeffects;
    }

    public void setSideeffects(String sideeffects) {
        this.sideeffects = sideeffects;
    }

    public String getDosing() {
        return Dosing;
    }

    public void setDosing(String dosing) {
        Dosing = dosing;
    }

    public String getActivesubstance() {
        return activesubstance;
    }

    public void setActivesubstance(String activesubstance) {
        this.activesubstance = activesubstance;
    }

    public String getInteractions() {
        return Interactions;
    }

    public void setInteractions(String interactions) {
        Interactions = interactions;
    }

    public String getInstancesUrl() {
        return InstancesUrl;
    }

    public void setInstancesUrl(String instancesUrl) {
        InstancesUrl = instancesUrl;
    }

    public String getInstanceCount() {
        return InstanceCount;
    }


    public void setInstanceCount(String instanceCount) {
        InstanceCount = instanceCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drug drug = (Drug) o;
        return
                getId() == (drug.getId()) &&
                        getDrugs_name().equals(drug.getDrugs_name()) &&
                        getImageUrl().equals(drug.getImageUrl());
    }

    public static DiffUtil.ItemCallback<Drug> itemCallback = new DiffUtil.ItemCallback<Drug>() {
        @Override
        public boolean areItemsTheSame(@NonNull Drug oldItem, @NonNull Drug newItem) {
            return oldItem.getId() == (newItem.getId());
        }

        @SuppressLint("DiffUtilEquals")

        @Override
        public boolean areContentsTheSame(@NonNull Drug oldItem, @NonNull Drug newItem) {
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
    @BindingAdapter("android:drugImage")
    public static void loadImage(ImageView imageView, String imageUrl) {
        Glide.with(imageView).load("https://shayoub.com/discount/public/icon/Drugs/" + imageUrl)
                .fitCenter()
                .into(imageView);
    }

    @BindingAdapter("android:companyImage")
    public static void loadIcon(ImageView imageView, String company_logo) {
        Glide.with(imageView).load("https://shayoub.com/discount/public/icon/companies/" + company_logo)
                .fitCenter()
                .into(imageView);
    }
}
