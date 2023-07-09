package com.example.discaount.data.model;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class Offer {
    int id;
    String name;
    String image;
    String description;
    String strDate;
    String endDate;
    int price;
    int amount;
    int product_id;
    int drug_id;
    Drug drug;
    Product product;
    int active;


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getDrug_id() {
        return drug_id;
    }

    public int getActive() {
        return active;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return
                getId()==offer.getId() &&
                        getName().equals(offer.getName());
    }
    public static void setItemCallback(DiffUtil.ItemCallback<Offer> itemCallback) {
        Offer.itemCallback = itemCallback;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static DiffUtil.ItemCallback<Offer> getItemCallback() {
        return itemCallback;
    }

    public static DiffUtil.ItemCallback<Offer> itemCallback = new DiffUtil.ItemCallback<Offer>() {
        @Override
        public boolean areItemsTheSame(@NonNull Offer oldItem, @NonNull Offer newItem) {
            return oldItem.getId()==(newItem.getId());
        }
        @SuppressLint("DiffUtilEquals")

        @Override
        public boolean areContentsTheSame(@NonNull Offer oldItem, @NonNull Offer newItem) {
            return oldItem.equals(newItem);
        }
    };
}

