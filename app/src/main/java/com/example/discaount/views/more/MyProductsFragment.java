package com.example.discaount.views.more;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.discaount.R;
import com.example.discaount.adapters.DrugListAdapter;
import com.example.discaount.adapters.MyOrderAdapter;
import com.example.discaount.data.model.Drug;
import com.example.discaount.data.model.MyOrder;
import com.example.discaount.databinding.DrugFragmentBinding;
import com.example.discaount.databinding.MyProductsFragmentBinding;
import com.example.discaount.utils.SharedPref;
import com.example.discaount.viewmodels.CompaniesViewModel;
import com.example.discaount.viewmodels.DrugViewModel;
import com.example.discaount.viewmodels.MedicineViewModel;
import com.example.discaount.viewmodels.MyOrderViewModel;
import com.example.discaount.views.activity.LoginActivity;

import java.util.List;

import static com.example.discaount.utils.SharedPref.TOKEN;
import static com.example.discaount.utils.SharedPref.mCtx;
import static com.example.discaount.utils.SharedPreferencesLocalStorage.SHARED_PREF_NAME;

public class MyProductsFragment extends Fragment {
  MyOrderAdapter myOrderAdapter;
  MyProductsFragmentBinding productsFragmentBinding;
  MyOrderViewModel myOrderViewModel;

  public static MyProductsFragment newInstance() {
        return new MyProductsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        productsFragmentBinding = MyProductsFragmentBinding.inflate(inflater, container, false);
        return productsFragmentBinding.getRoot();

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productsFragmentBinding.progressBar.setVisibility(View.VISIBLE);
        myOrderAdapter = new MyOrderAdapter();
        SharedPreferences sharedPreference = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        productsFragmentBinding.productRecyclerView.setAdapter(myOrderAdapter);

        myOrderViewModel = new ViewModelProvider(requireActivity()).get(MyOrderViewModel.class);
        productsFragmentBinding.productRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));
        if (!SharedPref.getInstance(getContext()).isLoggedIn()) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
            return;
        }
        final String token = sharedPreference.getString(TOKEN,"token");

        myOrderViewModel.getOrders(token).observe(getViewLifecycleOwner(), new Observer<List<MyOrder>>() {
            @Override
            public void onChanged(List<MyOrder> myOrders) {
                productsFragmentBinding.progressBar.setVisibility(View.GONE);
                myOrderAdapter.submitList(myOrders);
                myOrderAdapter.notifyDataSetChanged();
            }
        });
    }
}