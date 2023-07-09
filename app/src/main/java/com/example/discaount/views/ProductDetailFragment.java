package com.example.discaount.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.discaount.R;
import com.example.discaount.adapters.ShopListAdapter;
import com.example.discaount.data.model.Product;
import com.example.discaount.databinding.FragmentProductDetailBinding;
import com.example.discaount.utils.AlertAdded;
import com.example.discaount.viewmodels.ShopViewModel;
import com.google.android.material.snackbar.Snackbar;

public class ProductDetailFragment extends Fragment implements ShopListAdapter.ShopInterface {

    int quantity = 1;
    FragmentProductDetailBinding fragmentProductDetailBinding;
    ShopViewModel shopViewModel;
    private NavController navController;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentProductDetailBinding = FragmentProductDetailBinding.inflate(inflater, container, false);
        return fragmentProductDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);
        fragmentProductDetailBinding.setShopViewModel(shopViewModel);
        navController = Navigation.findNavController(view);
        fragmentProductDetailBinding.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopViewModel.addItemToCart(shopViewModel.getProduct().getValue(),quantity);
                navController.navigate(R.id.action_productDetailFragment_to_cartFragment);
            }
        });
        fragmentProductDetailBinding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    shopViewModel.addItemToCart(shopViewModel.getProduct().getValue(),quantity);
                    AlertAdded alertAdded = new AlertAdded(getActivity());
                    alertAdded.showSuccessDialogAdd(shopViewModel.getProduct().getValue().getName());

            }
        });
        fragmentProductDetailBinding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = quantity + 1;

                fragmentProductDetailBinding.amount.setText(String.valueOf(quantity));
            }
        });

        fragmentProductDetailBinding.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = quantity - 1;
                if (quantity <= 0) {
                    quantity = 1;
               }
                fragmentProductDetailBinding.amount.setText(String.valueOf(quantity));
                shopViewModel.removeOne(shopViewModel.getProduct().getValue());
            }
        });
    }

    @Override
    public void addItem(Product product) {
        boolean isAdded = shopViewModel.addItemToCart(product,quantity);
        if (isAdded) {
            Snackbar.make(requireView(), product.getName() + " added to cart.", Snackbar.LENGTH_LONG)
                    .setAction("Checkout", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            navController.navigate(R.id.action_shopFragment_to_cartFragment);
                        }
                    })
                    .show();
           // Toast.makeText(getContext(), "added", Toast.LENGTH_SHORT).show();
        } else {
            Snackbar.make(requireView(), "Already have the max quantity in cart.", Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onItemClick(Product product) {
    }
}