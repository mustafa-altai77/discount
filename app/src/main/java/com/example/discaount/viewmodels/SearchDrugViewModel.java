package com.example.discaount.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.discaount.data.model.CartItemDrug;
import com.example.discaount.data.model.Drug;
import com.example.discaount.repositories.CartDrugRepo;
import com.example.discaount.repositories.DrugRepo;
import com.example.discaount.repositories.SearchDrugRepo;

import java.util.List;

public class SearchDrugViewModel extends ViewModel {

    SearchDrugRepo drugRepo = new SearchDrugRepo();
    CartDrugRepo cartRepo = new CartDrugRepo();

    MutableLiveData<Drug> mutableProduct = new MutableLiveData<>();

    public LiveData<List<Drug>> getDrugs(String name) {
        return drugRepo.getDrugs(name);
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
       cartRepo.changeQuantity(cartItem, quantity);
    }

    public LiveData<Double> getTotalPrice() {
        return cartRepo.getTotalPrice();
    }

    public void resetCart() {
        cartRepo.initCart();
    }
}