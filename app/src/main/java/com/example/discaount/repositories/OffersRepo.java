package com.example.discaount.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.discaount.data.model.Categories;
import com.example.discaount.data.model.DepartmentResponse;
import com.example.discaount.data.model.Offer;
import com.example.discaount.data.model.OfferResponse;
import com.example.discaount.data.network.ApiClient;
import com.example.discaount.data.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OffersRepo {
    private static MutableLiveData<List<Offer>> mutableCategoryList;
    public LiveData<List<Offer>> getOffers() {
//        if (mutableCategoryList == null) {
            mutableCategoryList = new MutableLiveData<>();
            loadCategories();
//        }
        return mutableCategoryList;
    }
    private void loadCategories() {
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<OfferResponse> call = apiService.getOffer();
        call.enqueue(new Callback<OfferResponse>() {
            @Override
            public void onResponse(Call<OfferResponse> call, Response<OfferResponse> response) {
                List<Offer> offers;
                offers = response.body().getOffer();
                    mutableCategoryList.setValue(offers);
            }
            @Override
            public void onFailure(Call<OfferResponse> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
            }
        });
    }
}
