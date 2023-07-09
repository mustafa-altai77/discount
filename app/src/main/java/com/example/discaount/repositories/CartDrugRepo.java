package com.example.discaount.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.discaount.data.model.CartItem;
import com.example.discaount.data.model.CartItemDrug;
import com.example.discaount.data.model.Drug;
import com.example.discaount.data.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartDrugRepo {
    int quantity;
    private MutableLiveData<List<CartItemDrug>> mutableCart = new MutableLiveData<>();
    private MutableLiveData<Double> mutableTotalPrice = new MutableLiveData<>();

    public LiveData<List<CartItemDrug>> getCart() {
        if (mutableCart.getValue() == null) {
            initCart();
        }
        return mutableCart;
    }
    public void initCart() {
        mutableCart.setValue(new ArrayList<CartItemDrug>());
       calculateCartTotal();
    }
    public boolean addItemToCart(Drug drug,int quantity) {
        if (mutableCart.getValue() == null) {
            initCart();
        }
        List<CartItemDrug> cartItemList = new ArrayList<>(mutableCart.getValue());
        for (CartItemDrug cartItem: cartItemList) {
            if (cartItem.getDrug().getId()==(drug.getId())) {
                if (cartItem.getQuantity() == drug.getCount()) {
                    return false;
                }

                int index = cartItemList.indexOf(cartItem);
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItemList.set(index, cartItem);
                if (cartItem.getQuantity()==0){
                    cartItem.setQuantity(1);
                }
                mutableCart.setValue(cartItemList);
                calculateCartTotal();
                return true;
            }
        }
        CartItemDrug cartItem = new CartItemDrug(drug,quantity);
        cartItemList.add(cartItem);
        mutableCart.setValue(cartItemList);
        calculateCartTotal();
        return true;
    }

    public void removeItemFromCart(CartItemDrug cartItem) {
        if (mutableCart.getValue() == null) {
            return;
        }
        List<CartItemDrug> cartItemList = new ArrayList<>(mutableCart.getValue());
        cartItemList.remove(cartItem);
            mutableCart.setValue(cartItemList);
        calculateCartTotal();
    }
    public  void changeQuantity(CartItemDrug cartItem, int quantity) {
        if (mutableCart.getValue() == null) return;
        List<CartItemDrug> cartItemList = new ArrayList<>(mutableCart.getValue());
        CartItemDrug updatedItem = new CartItemDrug(cartItem.getDrug(), quantity);
        cartItemList.set(cartItemList.indexOf(cartItem), updatedItem);

        mutableCart.setValue(cartItemList);
        calculateCartTotal();
    }
    private void calculateCartTotal() {
        if (mutableCart.getValue() == null) return;
        double total = 0.0;
        List<CartItemDrug> cartItemList = mutableCart.getValue();
        for (CartItemDrug cartItem: cartItemList) {
            total += cartItem.getDrug().getPrice() * cartItem.getQuantity();
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
    public boolean removeOne(Drug drug) {
        if (mutableCart.getValue() == null) {
            initCart();
        }
        List<CartItemDrug> cartItemList = new ArrayList<>(mutableCart.getValue());
        for (CartItemDrug cartItem: cartItemList) {
            if (cartItem.getDrug().getId()==(drug.getId())) {
                if (cartItem.getQuantity() == drug.getCount()) {
                    return false;
                }

                int index = cartItemList.indexOf(cartItem);
                cartItem.setQuantity(cartItem.getQuantity() -quantity);
                if (cartItem.getQuantity()<quantity){
                    cartItem.setQuantity(1);
                }
                cartItemList.set(index, cartItem);

                mutableCart.setValue(cartItemList);
                calculateCartTotal();
                return true;
            }
        }
        CartItemDrug cartItem = new CartItemDrug(drug,quantity);
        cartItemList.add(cartItem);
        mutableCart.setValue(cartItemList);
        calculateCartTotal();
        return true;
    }

}
