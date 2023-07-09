package com.example.discaount.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.discaount.R;
import com.example.discaount.adapters.ShopListAdapter;
import com.example.discaount.adapters.MedicineAdapter;
import com.example.discaount.data.model.Drug;
import com.example.discaount.data.model.Product;
import com.example.discaount.databinding.FragmentProductBinding;
import com.example.discaount.repositories.ShopRepo;
import com.example.discaount.utils.AlertAdded;
import com.example.discaount.utils.LoadingDialog;
import com.example.discaount.viewmodels.ShopViewModel;
import com.example.discaount.viewmodels.MedicineViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ProductFragment extends Fragment implements ShopListAdapter.ShopInterface {
    int quantity=1;
    private static final String TAG = "ShopFragment";
    FragmentProductBinding fragmentProductBinding;
    private ShopListAdapter shopListAdapter;
    private ShopViewModel shopViewModel;
    String id;
    private NavController navController;
    public ProductFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceSbtate) {
        // Inflate the layout for this fragment
        fragmentProductBinding = FragmentProductBinding.inflate(inflater, container, false);
        fragmentProductBinding.progressBar.setVisibility(View.VISIBLE);

        id="";
        if(getArguments() != null)
         id=getArguments().getString("userId");
        return fragmentProductBinding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shopListAdapter = new ShopListAdapter(this);
        fragmentProductBinding.productRecyclerView.setAdapter(shopListAdapter);
        fragmentProductBinding.productRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));
        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);
        shopViewModel.getProducts(String.valueOf(id)).observe(getViewLifecycleOwner(), new Observer<List<Product>>() {

            @Override
            public void onChanged(List<Product> products) {
                fragmentProductBinding.progressBar.setVisibility(View.GONE);
                shopListAdapter.submitList(products);
                shopListAdapter.notifyDataSetChanged();
            }
        });
        navController = Navigation.findNavController(view);
    }

    @Override
    public void addItem(Product product) {
        boolean isAdded = shopViewModel.addItemToCart(product,quantity);
        if (isAdded) {

            AlertAdded alertAdded = new AlertAdded(getActivity());
            alertAdded.showSuccessDialogAdd(product.getName());
        }
        else  {
          //  Toast.makeText(getActivity(), "not add", Toast.LENGTH_SHORT).show();
        }
    }
    @Nullable
    @Override
    public void onItemClick(Product product) {
        shopViewModel.setProduct(product);
        navController.navigate(R.id.action_shopFragment_to_productDetailFragment);
    }
}