package com.example.discaount.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.discaount.data.model.MyOrder;
import com.example.discaount.data.model.Offer;
import com.example.discaount.repositories.MyProductRepo;

import java.util.List;

public class MyOrderViewModel extends ViewModel {
    MyProductRepo myProductRepo = new MyProductRepo();
    MutableLiveData<MyOrder> mutableCategory = new MutableLiveData<>();
    public LiveData<List<MyOrder>> getOrders(String token) {

        return myProductRepo.getOrder(token);
    }

    public void setOrder(MyOrder myOrder) {
        mutableCategory.setValue(myOrder);

    }

    public LiveData<MyOrder> getOrder() {
        return mutableCategory;
    }

}