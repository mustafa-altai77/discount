package com.example.discaount.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.discaount.R;
import com.example.discaount.adapters.DrugListAdapter;
import com.example.discaount.data.model.Drug;
import com.example.discaount.databinding.DrugFragmentBinding;
import com.example.discaount.databinding.FragmentCompanuDrugBinding;
import com.example.discaount.utils.AlertAdded;
import com.example.discaount.viewmodels.CompaniesViewModel;
import com.example.discaount.viewmodels.CompanyDrugViewModel;
import com.example.discaount.viewmodels.DrugViewModel;
import com.example.discaount.viewmodels.MedicineViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class CompanuDrugFragment extends Fragment implements DrugListAdapter.DrugInterface{
    DrugViewModel mViewModel;
    DrugListAdapter drugListAdapter;
    private NavController navController;
    private FragmentCompanuDrugBinding drugFragmentBinding;

    CompanyDrugViewModel drugViewModel;
    CompaniesViewModel companiesViewModel;
    int company_id ;
    int quantity =1;

    Toolbar toolbar;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        drugFragmentBinding = FragmentCompanuDrugBinding.inflate(inflater, container, false);


        return drugFragmentBinding.getRoot();

    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        drugFragmentBinding.progressBar.setVisibility(View.VISIBLE);

        drugListAdapter = new DrugListAdapter(this);
        drugFragmentBinding.drugRecyclerView.setAdapter(drugListAdapter);
        //  ((MainActivity) getActivity()).getDelegate().setSupportActionBar(toolbar);

        companiesViewModel = new ViewModelProvider(requireActivity()).get(CompaniesViewModel.class);
        drugViewModel = new ViewModelProvider(requireActivity()).get(CompanyDrugViewModel.class);
        mViewModel = new ViewModelProvider(requireActivity()).get(DrugViewModel.class);
        company_id = companiesViewModel.getCompany().getValue().getId();
       // drugFragmentBinding.drugRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));

        drugViewModel.getDrugs(company_id).observe(getViewLifecycleOwner(), new Observer<List<Drug>>() {
            @Override
            public void onChanged(List<Drug> drugs) {
                drugFragmentBinding.progressBar.setVisibility(View.GONE);
                drugListAdapter.submitList(drugs);
                drugListAdapter.notifyDataSetChanged();
            }
        });

        navController = Navigation.findNavController(view);
    }

    public void addItem(Drug drug) {
        boolean isAdded = mViewModel.addItemToCart(drug,quantity);
        if (isAdded) {
            AlertAdded alertAdded = new AlertAdded(getActivity());
            alertAdded.showSuccessDialogAdd(mViewModel.getDrug().getValue().getDrugs_name());
        }
    }

    @Override
    public void onItemClick(Drug drug) {
        mViewModel.setDrug(drug);
        navController.navigate(R.id.action_companuDrugFragment_to_drugDetailsFragment);
    }
}