package com.example.discaount.views.more;

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
import com.example.discaount.adapters.CompanyAdapter;
import com.example.discaount.adapters.MedicineAdapter2;
import com.example.discaount.data.model.Companies;
import com.example.discaount.data.model.Medicine;
import com.example.discaount.databinding.ManufatouresFragmentBinding;
import com.example.discaount.databinding.MedicineFragment2Binding;
import com.example.discaount.viewmodels.CompaniesViewModel;
import com.example.discaount.viewmodels.MedicineViewModel;

import java.util.List;

public class ManufatouresFragment extends Fragment implements CompanyAdapter.CompanyInterface{
     ManufatouresFragmentBinding fragmentBinding;
     CompaniesViewModel companiesViewModel;
     CompanyAdapter companyAdapter;
    private NavController navController;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentBinding = ManufatouresFragmentBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        companyAdapter = new CompanyAdapter(this);
        fragmentBinding.CategoryRecyclerView.setAdapter(companyAdapter);

        companiesViewModel = new ViewModelProvider(requireActivity()).get(CompaniesViewModel.class);

        fragmentBinding.CategoryRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), 0));
        fragmentBinding.progressBar.setVisibility(View.VISIBLE);
        companiesViewModel.getCompanies().observe(getViewLifecycleOwner(), new Observer<List<Companies>>() {

            @Override
            public void onChanged(List<Companies> companies) {
                fragmentBinding.progressBar.setVisibility(View.GONE);
                companyAdapter.submitList(companies);
                companyAdapter.notifyDataSetChanged();
            }
        });
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onItemClick(Companies companies) {
        companiesViewModel.setMedicine(companies);
        navController.navigate(R.id.action_MCategoriesFragment_to_companuDrugFragment);
    }
}