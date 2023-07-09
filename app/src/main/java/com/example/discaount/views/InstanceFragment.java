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
import android.widget.Toast;

import com.example.discaount.R;
import com.example.discaount.adapters.DrugListAdapter;
import com.example.discaount.data.model.Drug;
import com.example.discaount.databinding.DrugFragmentBinding;
import com.example.discaount.databinding.InstanceFragmentBinding;
import com.example.discaount.utils.AlertAdded;
import com.example.discaount.viewmodels.CompaniesViewModel;
import com.example.discaount.viewmodels.DrugViewModel;
import com.example.discaount.viewmodels.MedicineViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class InstanceFragment extends Fragment implements DrugListAdapter.DrugInterface {

    InstanceViewModel mViewModel;
    DrugViewModel drugViewModel;
    DrugListAdapter drugListAdapter;
    private NavController navController;
    private InstanceFragmentBinding instanceFragmentBinding;
    MedicineViewModel medicineViewModel;
    CompaniesViewModel companiesViewModel;
    String id;
    int quantity=1;
    public static InstanceFragment newInstance() {
        return new InstanceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        instanceFragmentBinding = InstanceFragmentBinding.inflate(inflater, container, false);
        drugViewModel = new ViewModelProvider(requireActivity()).get(DrugViewModel.class);

        id = String.valueOf(drugViewModel.getDrug().getValue().getId());

//        id="";
//        if(getArguments() != null)
//            id=getArguments().getString("userId");
        return instanceFragmentBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InstanceViewModel.class);
        // TODO: Use the ViewModel
    } public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        instanceFragmentBinding.progressBar.setVisibility(View.VISIBLE);
        drugListAdapter = new DrugListAdapter(this);
        instanceFragmentBinding.drugRecyclerView.setAdapter(drugListAdapter);

        medicineViewModel = new ViewModelProvider(requireActivity()).get(MedicineViewModel.class);
        companiesViewModel = new ViewModelProvider(requireActivity()).get(CompaniesViewModel.class);

        mViewModel = new ViewModelProvider(requireActivity()).get(InstanceViewModel.class);
       // Toast.makeText(getContext(), id+"    id   ", Toast.LENGTH_SHORT).show();
        instanceFragmentBinding.drugRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));
//        id = String.valueOf(companiesViewModel.getCompany().getValue().getId());
        mViewModel.getDrugs(String.valueOf(id)).observe(getViewLifecycleOwner(), new Observer<List<Drug>>() {
            @Override
            public void onChanged(List<Drug> drugs) {
                if (drugs.isEmpty()){
                   // instanceFragmentBinding.relativeNotFound.setVisibility(View.VISIBLE);

                }
                instanceFragmentBinding.progressBar.setVisibility(View.GONE);
                drugListAdapter.submitList(drugs);
                drugListAdapter.notifyDataSetChanged();
            }
        });
        navController = Navigation.findNavController(view);
    }

    public void addItem(Drug drug) {
        boolean isAdded = drugViewModel.addItemToCart(drug,quantity);
        if (isAdded) {
//            Snackbar.make(requireView(), drug.getDrugs_name() + "added to cart.", Snackbar.LENGTH_LONG)
//                    .setAction("Checkout", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            navController.navigate(R.id.action_instanceFragment_to_cartFragment);
//                        }
//                    })
//                    .show();
            AlertAdded alertAdded = new AlertAdded(getActivity());
            alertAdded.showSuccessDialogAdd(drug.getDrugs_name());
        } else {
            Snackbar.make(requireView(), "Already have the max quantity in cart.", Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onItemClick(Drug drug) {
        drugViewModel.setDrug(drug);
        navController.navigate(R.id.action_instanceFragment_to_drugDetailsFragment);
    }

}