package com.example.discaount.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.discaount.data.model.Product;
import com.example.discaount.data.model.ProductResponse;
import com.example.discaount.data.model.SearchRespons;
import com.example.discaount.data.network.ApiClient;
import com.example.discaount.data.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchShopRepo {
    private static  MutableLiveData<List<Product>> mutableProductList;

    public LiveData<List<Product>> getProducts(String name) {
//        if (mutableProductList == null) {
            mutableProductList = new MutableLiveData<>();
            loadProducts(name);

//       }
        return mutableProductList;
    }
    private void loadProducts(String name) {
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<SearchRespons> call = apiService.searchProducts(name);
        call.enqueue(new Callback<SearchRespons>() {
            @Override
            public void onResponse(Call<SearchRespons> call, Response<SearchRespons> response) {
                List<Product> products;
                products = response.body().getProducts();
                mutableProductList.setValue(products);
            }
            @Override
            public void onFailure(Call<SearchRespons> call, Throwable t) {
                //mutableProductList.postValue(null);
                Log.d("TAG", "Response = " + t.toString());
            }
        });
    }
}
