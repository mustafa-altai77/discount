package com.example.discaount.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.discaount.data.model.SubCategory;
import com.example.discaount.repositories.SubCategoryRepo;

import java.util.List;

public class SubCategoryViewModel extends ViewModel {
    SubCategoryRepo categoriesRepo = new SubCategoryRepo();
    MutableLiveData<SubCategory> mutableCategory = new MutableLiveData<>();
    public LiveData<List<SubCategory>> getCategories(String id) {

        return categoriesRepo.getCategory(id);
    }

    public void setSubCategory(SubCategory subCategory) {
        mutableCategory.setValue(subCategory);

    }

    public LiveData<SubCategory> getCategory() {
        return mutableCategory;
    }
}
