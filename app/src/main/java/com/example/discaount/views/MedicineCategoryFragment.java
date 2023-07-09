package com.example.discaount.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;


import com.example.discaount.R;
import com.example.discaount.adapters.MedicineAdapter;
import com.example.discaount.data.model.Medicine;
import com.example.discaount.databinding.MedicineFragmentBinding;
import com.example.discaount.utils.SharedPreferencesLocalStorage;
import com.example.discaount.viewmodels.ShopViewModel;
import com.example.discaount.viewmodels.MedicineViewModel;

import java.util.List;

public class MedicineCategoryFragment extends Fragment implements MedicineAdapter.MedicineInterface {

    MedicineFragmentBinding medicineFragmentBinding;
    MedicineViewModel medicineViewModel;
    private NavController navController;
    private ProgressBar progressBar;
    private MedicineAdapter medicineAdapter;
    Toolbar toolbar;
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setupToolbar();
        medicineFragmentBinding = MedicineFragmentBinding.inflate(inflater, container, false);
        return medicineFragmentBinding.getRoot();
    }
    private void setupToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        ((AppCompatActivity) getActivity()). getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()). getSupportActionBar().setHomeButtonEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(5f);
        }

//        SharedPreferencesLocalStorage localStorage = new SharedPreferencesLocalStorage(getContext());
//        Log.d("TOKEN", localStorage.getToken());

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (localStorage.isLogged()) {
//                    getActivity().onBackPressed();
//                } else {
//                    localStorage.logout();
////                    Intent intent = new Intent(LastOperations.this, SplashActivity.class);
////                    startActivity(intent);
////                    finish();
//                }
//            }
//        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        medicineAdapter = new MedicineAdapter(this);
        navController = Navigation.findNavController(view);
        medicineFragmentBinding.CategoryRecyclerView.setAdapter(medicineAdapter);
        medicineFragmentBinding.CategoryRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), 0));
       // homeFragmentBinding.progressBar.setVisibility(View.VISIBLE);

        medicineViewModel = new ViewModelProvider(requireActivity()).get(MedicineViewModel.class);
        medicineViewModel.getMedicines().observe(getViewLifecycleOwner(), new Observer<List<Medicine>>() {

            @Override
            public void onChanged(List<Medicine> medicines) {
                medicineFragmentBinding.progressBar.setVisibility(View.GONE);
                medicineAdapter.submitList(medicines);
                medicineAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onItemClick(Medicine medicine) {
        medicineViewModel.setMedicine(medicine);
        navController.navigate(R.id.action_homeFragment_to_drugFragment);
    }
}