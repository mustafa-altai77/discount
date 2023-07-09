package com.example.discaount.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.discaount.data.model.Categories;
import com.example.discaount.data.model.DepartmentResponse;
import com.example.discaount.data.network.ApiClient;
import com.example.discaount.data.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesRepo {
    private MutableLiveData<List<Categories>> mutableCategoryList;
    public LiveData<List<Categories>> getCategory() {
        if (mutableCategoryList == null) {
            mutableCategoryList = new MutableLiveData<>();
            loadCategories();
        }
        return mutableCategoryList;
    }
    private void loadCategories() {
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<DepartmentResponse> call = apiService.getCategories();
        call.enqueue(new Callback<DepartmentResponse>() {
            @Override
            public void onResponse(Call<DepartmentResponse> call, Response<DepartmentResponse> response) {
                List<Categories> categories = response.body().getCategories();
                    mutableCategoryList.setValue(categories);
              //  }
            }
            @Override
            public void onFailure(Call<DepartmentResponse> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
            }
        });
    }
}
