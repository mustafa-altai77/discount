package com.example.discaount.views;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.discaount.R;
import com.example.discaount.adapters.DrugListAdapter;
import com.example.discaount.adapters.OfferAdapter;
import com.example.discaount.data.model.Drug;
import com.example.discaount.data.model.Offer;
import com.example.discaount.databinding.FragmentProductDetailBinding;
import com.example.discaount.databinding.OfferFragmentBinding;
import com.example.discaount.databinding.OfferItemBinding;
import com.example.discaount.viewmodels.CompaniesViewModel;
import com.example.discaount.viewmodels.DrugViewModel;
import com.example.discaount.viewmodels.MedicineViewModel;
import com.example.discaount.viewmodels.OfferViewModel;

import java.util.List;

public class OfferFragment extends Fragment implements OfferAdapter.OfferInterface {
     OfferFragmentBinding offerFragmentBinding;
    private NavController navController;
     OfferViewModel offerViewModel;
     OfferAdapter offerAdapter;

    public static OfferFragment newInstance() {
        return new OfferFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        offerFragmentBinding = OfferFragmentBinding.inflate(inflater, container, false);
        return offerFragmentBinding.getRoot();    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        offerFragmentBinding.progressBar.setVisibility(View.VISIBLE);
        offerAdapter = new OfferAdapter(this);
        offerFragmentBinding.offerRecyclerView.setAdapter(offerAdapter);
        offerViewModel = new ViewModelProvider(requireActivity()).get(OfferViewModel.class);

        offerFragmentBinding.offerRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));
        offerViewModel.getOffers().observe(getViewLifecycleOwner(), new Observer<List<Offer>>() {
            @Override
            public void onChanged(List<Offer> offers) {
                offerFragmentBinding.progressBar.setVisibility(View.GONE);
                offerAdapter.submitList(offers);
                offerAdapter.notifyDataSetChanged();
            }
        });
        navController = Navigation.findNavController(view);
    }

    @Override
    public void addItem(Offer offer) {

    }

    @Override
    public void onItemClick(Offer offer) {
        offerViewModel.setOffer(offer);
        navController.navigate(R.id.action_offerFragment_to_offerDetailsFragment);
    }

}