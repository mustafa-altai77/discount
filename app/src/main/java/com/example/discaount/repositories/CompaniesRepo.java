package com.example.discaount.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.discaount.data.model.Companies;
import com.example.discaount.data.model.CompanyResponse;
import com.example.discaount.data.model.Medicine;
import com.example.discaount.data.model.MedicineResponse;
import com.example.discaount.data.network.ApiClient;
import com.example.discaount.data.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompaniesRepo {
    private static MutableLiveData<List<Companies>> mutableSubCategoryList;
    public LiveData<List<Companies>> getCompany() {
//        if (mutableSubCategoryList == null) {
            mutableSubCategoryList = new MutableLiveData<>();
            loadSubCategories();
//        }
        return mutableSubCategoryList;
    }
    private void loadSubCategories() {
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<CompanyResponse> call = apiService.getCompany();
        call.enqueue(new Callback<CompanyResponse>() {
            @Override
            public void onResponse(Call<CompanyResponse> call, Response<CompanyResponse> response) {
                List<Companies> companies;
                companies = response.body().getCompanies();
                mutableSubCategoryList.setValue(companies);
            }
            @Override
            public void onFailure(Call<CompanyResponse> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
            }
        });
    }
}
