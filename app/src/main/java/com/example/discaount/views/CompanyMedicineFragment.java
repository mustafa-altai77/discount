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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.discaount.R;
import com.example.discaount.adapters.DrugListAdapter;
import com.example.discaount.adapters.MedicineAdapter;
import com.example.discaount.data.model.Drug;
import com.example.discaount.data.model.Medicine;
import com.example.discaount.databinding.CompanyMedicineFragmentBinding;
import com.example.discaount.databinding.DrugFragmentBinding;
import com.example.discaount.utils.AlertAdded;
import com.example.discaount.viewmodels.CompaniesViewModel;
import com.example.discaount.viewmodels.CompanyDrugViewModel;
import com.example.discaount.viewmodels.DrugViewModel;
import com.example.discaount.viewmodels.MedicineViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class CompanyMedicineFragment extends Fragment implements DrugListAdapter.DrugInterface{
    private NavController navController;
    private ProgressBar progressBar;
    DrugListAdapter drugListAdapter;
    DrugViewModel drugViewModel;
    CompaniesViewModel companiesViewModel;
     CompanyDrugViewModel mViewModel;
    CompanyMedicineFragmentBinding companyMedicineFragmentBinding;
   String id;
   int quantity=1;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        companyMedicineFragmentBinding = CompanyMedicineFragmentBinding.inflate(inflater, container, false);
        id="";
        if(getArguments() != null)
            id=getArguments().getString("userId");
      //  Toast.makeText(getContext(), id+"", Toast.LENGTH_SHORT).show();

        return companyMedicineFragmentBinding.getRoot();


    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        companyMedicineFragmentBinding.progressBar.setVisibility(View.VISIBLE);
        drugListAdapter = new DrugListAdapter(this);
        companyMedicineFragmentBinding.drugRecyclerView.setAdapter(drugListAdapter);

        mViewModel = new ViewModelProvider(requireActivity()).get(CompanyDrugViewModel.class);

       // drugViewModel = new ViewModelProvider(requireActivity()).get(DrugViewModel.class);
       // Toast.makeText(getContext(), id+"    id   ", Toast.LENGTH_SHORT).show();

        companyMedicineFragmentBinding.drugRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));
        mViewModel.getDrugs(Integer.parseInt(id)).observe(getViewLifecycleOwner(), new Observer<List<Drug>>() {
            @Override
            public void onChanged(List<Drug> drugs) {
                companyMedicineFragmentBinding.progressBar.setVisibility(View.GONE);
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
        navController.navigate(R.id.action_companyMedicineFragment_to_drugDetailsFragment);
    }
}