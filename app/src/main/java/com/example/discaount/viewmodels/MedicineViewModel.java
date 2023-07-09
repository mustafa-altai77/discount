package com.example.discaount.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.discaount.data.model.Medicine;
import com.example.discaount.repositories.MedicineRepo;

import java.util.List;

public class MedicineViewModel extends ViewModel {
    MedicineRepo medicineRepo = new MedicineRepo();
    MutableLiveData<Medicine> mutableSubCategory = new MutableLiveData<Medicine>();

    public LiveData<List<Medicine>> getMedicines() {
        return medicineRepo.getMedicine();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void setMedicine(Medicine medicine) {
        mutableSubCategory.setValue(medicine);

    }

    public LiveData<Medicine> getSubCategory() {
        return mutableSubCategory;
    }
}