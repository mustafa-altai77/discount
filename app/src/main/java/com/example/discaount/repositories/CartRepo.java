package com.example.discaount.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.discaount.data.model.CartItem;
import com.example.discaount.data.model.CartItemDrug;
import com.example.discaount.data.model.Drug;
import com.example.discaount.data.model.Product;
import java.util.ArrayList;
import java.util.List;

public class CartRepo {
    int quantity;
    private MutableLiveData<List<CartItem>> mutableCart = new MutableLiveData<>();
    private MutableLiveData<Double> mutableTotalPrice = new MutableLiveData<>();

    public LiveData<List<CartItem>> getCart() {
        if (mutableCart.getValue() == null) {
            initCart();
        }
        return mutableCart;
    }
    public void initCart() {
        mutableCart.setValue(new ArrayList<CartItem>());
        calculateCartTotal();
    }
    public boolean addItemToCart(Product product,int quantity) {
        if (mutableCart.getValue() == null) {
            initCart();
        }
        List<CartItem> cartItemList = new ArrayList<>(mutableCart.getValue());
        for (CartItem cartItem: cartItemList) {
            if (cartItem.getProduct().getId().equals(product.getId())) {
                if (cartItem.getQuantity() == 100) {
                    return false;
                }

                int index = cartItemList.indexOf(cartItem);
                cartItem.setQuantity(cartItem.getQuantity()+quantity);
                cartItemList.set(index, cartItem);
                if (cartItem.getQuantity()==0){
                    cartItem.setQuantity(1);
                }
                mutableCart.setValue(cartItemList);
                calculateCartTotal();
                return true;
            }
        }
        CartItem cartItem = new CartItem(product, quantity);
        cartItemList.add(cartItem);
        mutableCart.setValue(cartItemList);
        calculateCartTotal();
        return true;
    }

    public void removeItemFromCart(CartItem cartItem) {
        if (mutableCart.getValue() == null) {
            return;
        }
        List<CartItem> cartItemList = new ArrayList<>(mutableCart.getValue());
        cartItemList.remove(cartItem);
        mutableCart.setValue(cartItemList);
        calculateCartTotal();
    }
    public  void changeQuantity(CartItem cartItem, int quantity) {
        if (mutableCart.getValue() == null) return;

        List<CartItem> cartItemList = new ArrayList<>(mutableCart.getValue());
        CartItem updatedItem = new CartItem(cartItem.getProduct(), quantity);
        cartItemList.set(cartItemList.indexOf(cartItem), updatedItem);
        mutableCart.setValue(cartItemList);
        calculateCartTotal();
    }
    private void calculateCartTotal() {
        if (mutableCart.getValue() == null) return;
        double total = 0.0;
        List<CartItem> cartItemList = mutableCart.getValue();
        for (CartItem cartItem: cartItemList) {
            total += cartItem.getProduct().getPrice() * cartItem.getQuantity();
//            total += cartItem.getDrug().getPrice() * cartItem.getQuantity();

        }
        mutableTotalPrice.setValue(total);
    }
    public LiveData<Double> getTotalPrice() {
        if (mutableTotalPrice.getValue() == null) {
            mutableTotalPrice.setValue(0.0);
        }
        return mutableTotalPrice;
    }

    public boolean removeOne(Product product) {
        if (mutableCart.getValue() == null) {
            initCart();
        }
        List<CartItem> cartItemList = new ArrayList<>(mutableCart.getValue());
        for (CartItem cartItem: cartItemList) {
            if (cartItem.getProduct().getId()==(product.getId())) {
                if (cartItem.getQuantity() == 100) {
                    return false;
                }

                int index = cartItemList.indexOf(cartItem);
                cartItem.setQuantity(cartItem.getQuantity() - quantity);
                cartItemList.set(index, cartItem);
                if (cartItem.getQuantity()< quantity){
                    cartItem.setQuantity(1);
                }
                mutableCart.setValue(cartItemList);
                calculateCartTotal();
                return true;
            }
        }
        CartItem cartItem = new CartItem(product , quantity);
        cartItemList.add(cartItem);
        mutableCart.setValue(cartItemList);
        calculateCartTotal();
        return true;
    }
}
