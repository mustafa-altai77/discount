package com.example.discaount.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.discaount.data.model.CartItem;
import com.example.discaount.data.model.Product;
import com.example.discaount.repositories.CartRepo;
import com.example.discaount.repositories.SearchShopRepo;
import com.example.discaount.repositories.ShopRepo;

import java.util.List;


public class SearchShopViewModel extends ViewModel {

    SearchShopRepo shopRepo = new SearchShopRepo();
    CartRepo cartRepo = new CartRepo();

    MutableLiveData<Product> mutableProduct = new MutableLiveData<>();

    public LiveData<List<Product>> getProducts(String name) {
        return shopRepo.getProducts(name);
    }
    public void setProduct(Product product) {
        mutableProduct.setValue(product);
    }


    public LiveData<Product> getProduct() {
        return mutableProduct;
    }

    public LiveData<List<CartItem>> getCart() {
        return cartRepo.getCart();
    }

    public boolean addItemToCart(Product product,int quantity) {
        return cartRepo.addItemToCart(product,quantity);
    }

    public void removeItemFromCart(CartItem cartItem) {
        cartRepo.removeItemFromCart(cartItem);
    }

    public void changeQuantity(CartItem cartItem, int quantity) {
        cartRepo.changeQuantity(cartItem, quantity);
    }

    public LiveData<Double> getTotalPrice() {
        return cartRepo.getTotalPrice();
    }

    public void resetCart() {
        cartRepo.initCart();
    }
}
