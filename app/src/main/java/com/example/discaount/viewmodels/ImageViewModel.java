package com.example.discaount.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.discaount.data.model.Drug;
import com.example.discaount.data.model.Medicine;
import com.example.discaount.data.model.Prescriptions;

import java.util.List;

public class ImageViewModel extends ViewModel {
    MutableLiveData<Prescriptions> prescriptionsMutableLiveData = new MutableLiveData<Prescriptions>();

    public MutableLiveData<Prescriptions> getPrescriptions() {
        return prescriptionsMutableLiveData;
    }
    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void setPrescription(Prescriptions prescriptions) {
        prescriptionsMutableLiveData.setValue(prescriptions);

    }

    public MutableLiveData<Prescriptions> getPrescription() {
        return prescriptionsMutableLiveData;
    }
}
