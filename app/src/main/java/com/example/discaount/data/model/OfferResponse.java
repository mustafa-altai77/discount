package com.example.discaount.data.model;

import android.annotation.SuppressLint;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;
import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class OfferResponse {
    @SerializedName("data")
    List<Offer> offers;

    private Message message;
    private Boolean success;
    public List<Offer> getOffer() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public List<Offer> getOffers() {
        return offers;
    }


    public Message getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public static void setItemCallback(DiffUtil.ItemCallback<OfferResponse> itemCallback) {
        OfferResponse.itemCallback = itemCallback;
    }

    public static DiffUtil.ItemCallback<OfferResponse> itemCallback = new DiffUtil.ItemCallback<OfferResponse>() {
        @Override
        public boolean areItemsTheSame(@NonNull OfferResponse oldItem, @NonNull OfferResponse newItem) {
            return oldItem.getOffer().equals(newItem.getOffer());
        }
        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull OfferResponse oldItem, @NonNull OfferResponse newItem) {
            return oldItem.getOffer().equals(newItem);
        }
    };
    @BindingAdapter("android:categoryImage")
    public static void loadImage(ImageView imageView, String imageUrl){
        Glide.with(imageView).load("http://primarykeysd.com/PharmacyApp/VoyagerTesting/public/storage/"+imageUrl)
                .fitCenter().into(imageView);
    }
}
