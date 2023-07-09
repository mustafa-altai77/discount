package com.example.discaount.repositories;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.discaount.data.model.Product;
import com.example.discaount.data.model.ProductResponse;
import com.example.discaount.data.network.ApiClient;
import com.example.discaount.data.network.ApiInterface;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ShopRepo {
    private static  MutableLiveData<List<Product>> mutableProductList;

    public LiveData<List<Product>> getProducts(String id) {
//        if (mutableProductList == null) {
            mutableProductList = new MutableLiveData<>();
            loadProducts(id);

//       }
        return mutableProductList;
    }
    private void loadProducts(String id) {
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<ProductResponse> call = apiService.getProducts(id);
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {


                List<Product> products;
                products = response.body().getProductas();

                mutableProductList.setValue(products);
            }
            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                //mutableProductList.postValue(null);
                Log.d("TAG", "Response = " + t.toString());
            }
        });
    }
}
