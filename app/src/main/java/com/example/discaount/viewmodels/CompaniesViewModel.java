package com.example.discaount.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.discaount.data.model.Companies;
import com.example.discaount.data.model.Medicine;
import com.example.discaount.repositories.CompaniesRepo;
import com.example.discaount.repositories.MedicineRepo;

import java.util.List;

public class CompaniesViewModel extends ViewModel {
    CompaniesRepo companiesRepo = new CompaniesRepo();
    MutableLiveData<Companies> mutableSubCategory = new MutableLiveData<Companies>();

    public LiveData<List<Companies>> getCompanies() {
        return companiesRepo.getCompany();
    }


    public void setMedicine(Companies companies) {
        mutableSubCategory.setValue(companies);

    }

    public LiveData<Companies> getCompany() {
        return mutableSubCategory;
    }
}