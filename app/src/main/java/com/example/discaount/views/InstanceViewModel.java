package com.example.discaount.views;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.discaount.data.model.CartItemDrug;
import com.example.discaount.data.model.Drug;
import com.example.discaount.repositories.CartDrugRepo;
import com.example.discaount.repositories.DrugRepo;
import com.example.discaount.repositories.InstanceRepo;

import java.util.List;

public class InstanceViewModel extends ViewModel {
    InstanceRepo drugRepo = new InstanceRepo();
    CartDrugRepo cartRepo = new CartDrugRepo();

    MutableLiveData<Drug> mutableProduct = new MutableLiveData<>();

    public LiveData<List<Drug>> getDrugs(String id) {
        return drugRepo.getDrugs(id);
    }
    public void setDrug(Drug drug) {
        mutableProduct.setValue(drug);
    }

    public LiveData<Drug> getDrug() {
        return mutableProduct;
    }

    public LiveData<List<CartItemDrug>> getCart() {
        return cartRepo.getCart();
    }

    public boolean addItemToCart(Drug drug,int quantity) {
        return cartRepo.addItemToCart(drug,quantity);
    }

    public void removeItemFromCart(CartItemDrug cartItem) {
        cartRepo.removeItemFromCart(cartItem);
    }

    public void changeQuantity(CartItemDrug cartItem, int quantity) {
//        cartRepo.(cartItem, quantity);
    }

    public LiveData<Double> getTotalPrice() {
        return cartRepo.getTotalPrice();
    }

    public void resetCart() {
        cartRepo.initCart();
    }
}