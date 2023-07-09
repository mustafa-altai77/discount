package com.example.discaount.data.model;

import android.annotation.SuppressLint;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

public class CartItemDrug {
    private Drug drug;
    private int quantity;

    public CartItemDrug(Drug drug, int quantity) {
        this.drug = drug;
        this.quantity = quantity;
    }

    public static DiffUtil.ItemCallback<CartItemDrug> getItemCallback() {
        return itemCallback;
    }
    public static void setItemCallback(DiffUtil.ItemCallback<CartItemDrug> itemCallback) {
        CartItemDrug.itemCallback = itemCallback;
    }
    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    @Override
    public String toString(){
        return "CartItem{" +
                "drug =" + drug +
                ", quantity=" + quantity +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemDrug cartItem = (CartItemDrug) o;
        return getQuantity() == cartItem.getQuantity() &&
                getDrug().equals(cartItem.getDrug()) ;
    }
//    @BindingAdapter("android:setVal")
//    public static void getSelectedSpinnerValue(Spinner spinner, int quantity){
//        spinner.setSelection(quantity - 1, true);
//    }
    public static DiffUtil.ItemCallback<CartItemDrug> itemCallback = new DiffUtil.ItemCallback<CartItemDrug>() {
        @Override
        public boolean areItemsTheSame(@NonNull CartItemDrug oldItem, @NonNull CartItemDrug newItem) {
            return oldItem.getQuantity() == newItem.getQuantity();
        }
        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull CartItemDrug oldItem, @NonNull CartItemDrug newItem) {
            return oldItem.equals(newItem); }
    };
}
