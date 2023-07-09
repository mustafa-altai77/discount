package com.example.discaount.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.discaount.data.model.Drug;
import com.example.discaount.data.model.DrugsResponse;
import com.example.discaount.data.network.ApiClient;
import com.example.discaount.data.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchDrugRepo {
    private static MutableLiveData<List<Drug>> mutableProductList;

    public LiveData<List<Drug>> getDrugs(String name) {
//        if (mutableProductList == null) {
        mutableProductList = new MutableLiveData<>();

        loadDrugs(name);

//       }
        return mutableProductList;
    }

    private void loadDrugs(String name) {
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<DrugsResponse> call = apiService.searchDrugs(name);
        call.enqueue(new Callback<DrugsResponse>() {
            @Override
            public void onResponse(Call<DrugsResponse> call, Response<DrugsResponse> response) {
                try {
                    List<Drug> drugs;
                    drugs = response.body().getDrugs();
                    mutableProductList.setValue(drugs);

                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<DrugsResponse> call, Throwable t) {
                //mutableProductList.postValue(null);
                Log.d("TAG", "Response = " + t.toString());
            }
        });
    }
}
