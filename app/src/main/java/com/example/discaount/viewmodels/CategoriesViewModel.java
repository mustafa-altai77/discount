package com.example.discaount.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.discaount.data.model.Categories;
import com.example.discaount.repositories.CategoriesRepo;

import java.util.List;

public class CategoriesViewModel extends ViewModel {
    CategoriesRepo categoriesRepo = new CategoriesRepo();
    MutableLiveData<Categories> mutableCategory = new MutableLiveData<>();
    public LiveData<List<Categories>> getCategories() {

        return categoriesRepo.getCategory();
    }

    public void setCategory(Categories categories) {
        mutableCategory.setValue(categories);

    }
    public LiveData<Categories> getCategory() {
        return mutableCategory;
    }
}
