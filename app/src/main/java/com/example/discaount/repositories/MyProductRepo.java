package com.example.discaount.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.discaount.data.model.Drug;
import com.example.discaount.data.model.DrugsResponse;
import com.example.discaount.data.model.MyOrder;
import com.example.discaount.data.model.OrderResponse;
import com.example.discaount.data.network.ApiClient;
import com.example.discaount.data.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.discaount.utils.SharedPref.TOKEN;
import static com.example.discaount.utils.SharedPref.mCtx;
import static com.example.discaount.utils.SharedPreferencesLocalStorage.SHARED_PREF_NAME;

public class MyProductRepo {

    private static MutableLiveData<List<MyOrder>> mutableProductList;

    public LiveData<List<MyOrder>> getOrder(String token) {
//        if (mutableProductList == null) {
        mutableProductList = new MutableLiveData<>();
        loadOrder(token);
//       }
        return mutableProductList;
    }
    private void loadOrder(String token) {
        try {


            ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
            Call<OrderResponse> call = apiService.getMyOrder(token);
            call.enqueue(new Callback<OrderResponse>() {
                @Override
                public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                    Log.d("TAG", "Response = " + response.toString());
                    List<MyOrder> myOrders;
                    myOrders = response.body().myOrders();
                    mutableProductList.setValue(myOrders);
                }
                @Override
                public void onFailure(Call<OrderResponse> call, Throwable t) {
                    //mutableProductList.postValue(null);
                    Log.d("TAG", "Response = " + t.toString());
                }
            });
        }
        catch (Exception e){

        }
    }
}
