package com.example.discaount.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.discaount.data.model.Categories;
import com.example.discaount.data.model.Offer;
import com.example.discaount.repositories.CategoriesRepo;
import com.example.discaount.repositories.OffersRepo;

import java.util.List;

public class OfferViewModel extends ViewModel {
    OffersRepo categoriesRepo = new OffersRepo();
    MutableLiveData<Offer> mutableCategory = new MutableLiveData<>();
    public LiveData<List<Offer>> getOffers() {

        return categoriesRepo.getOffers();
    }

    public void setOffer(Offer offer) {
        mutableCategory.setValue(offer);

    }

    public LiveData<Offer> getOffer() {
        return mutableCategory;
    }

}