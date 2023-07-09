package com.example.discaount.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.discaount.data.model.Medicine;
import com.example.discaount.data.model.MedicineResponse;
import com.example.discaount.data.network.ApiClient;
import com.example.discaount.data.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicineRepo {
    private MutableLiveData<List<Medicine>> mutableSubCategoryList;
    public LiveData<List<Medicine>> getMedicine() {
        if (mutableSubCategoryList == null) {
            mutableSubCategoryList = new MutableLiveData<>();
            loadSubCategories();
        }
        return mutableSubCategoryList;
    }
    private void loadSubCategories() {
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<MedicineResponse> call = apiService.getMedicine();
        call.enqueue(new Callback<MedicineResponse>() {
            @Override
            public void onResponse(Call<MedicineResponse> call, Response<MedicineResponse> response) {
                List<Medicine> medicines;
                medicines = response.body().getMedicine();
                mutableSubCategoryList.setValue(medicines);
            }
            @Override
            public void onFailure(Call<MedicineResponse> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
            }
        });
    }
}
