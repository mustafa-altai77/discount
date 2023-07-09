package com.example.discaount.repositories;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.discaount.data.model.Categories;
import com.example.discaount.data.model.DepartmentResponse;
import com.example.discaount.data.model.SubCategory;
import com.example.discaount.data.network.ApiClient;
import com.example.discaount.data.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryRepo {
    private MutableLiveData<List<SubCategory>> mutableCategoryList;
    public LiveData<List<SubCategory>> getCategory(String id) {
//        if (mutableCategoryList == null) {
            mutableCategoryList = new MutableLiveData<>();
            loadCategories(id);
//        }
        return mutableCategoryList;
    }
    private void loadCategories(String id) {
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<DepartmentResponse> call = apiService.getSub(id);
        call.enqueue(new Callback<DepartmentResponse>() {
            @Override
            public void onResponse(Call<DepartmentResponse> call, Response<DepartmentResponse> response) {
//                List<SubCategory> subCategories = new ArrayList<>();
                List<Categories> categories = response.body().getCategories();
//                for (int i=0;i<categories.size();i++) {
////                         subCategories = categories.get(i).getSubCategories();
//                    Log.d("TAG", "Response = " + categories);
//
//
//                }
//                mutableCategoryList.setValue(subCategories);
                /*
                m[cat.id].setVa(cat.get(i).subCategories())

                 */
                //  }
            }
            @Override
            public void onFailure(Call<DepartmentResponse> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
            }
        });
    }
}
