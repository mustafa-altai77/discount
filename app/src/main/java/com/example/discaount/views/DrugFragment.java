package com.example.discaount.views;

import android.nfc.Tag;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.discaount.R;
import com.example.discaount.adapters.DrugListAdapter;
import com.example.discaount.data.model.Drug;
import com.example.discaount.databinding.DrugFragmentBinding;
import com.example.discaount.utils.AlertAdded;
import com.example.discaount.viewmodels.CompaniesViewModel;
import com.example.discaount.viewmodels.CompanyDrugViewModel;
import com.example.discaount.viewmodels.DrugViewModel;
import com.example.discaount.viewmodels.MedicineViewModel;
import com.example.discaount.views.activity.MainActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class DrugFragment extends Fragment implements DrugListAdapter.DrugInterface {
    DrugViewModel mViewModel;
    DrugListAdapter drugListAdapter;
    private NavController navController;
    private DrugFragmentBinding drugFragmentBinding;
    MedicineViewModel medicineViewModel;
    CompaniesViewModel companiesViewModel;
    String section_id,section;
  int quantity =1;
    Toolbar toolbar;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        drugFragmentBinding = DrugFragmentBinding.inflate(inflater, container, false);
        section="";
        if(getArguments() != null)
            section= getArguments().getString("section_id");

//        company_id="";
//        if(getArguments() != null)
//            company_id=getArguments().getString("company_id");
            return drugFragmentBinding.getRoot();

    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        drugFragmentBinding.progressBar.setVisibility(View.VISIBLE);

        drugListAdapter = new DrugListAdapter(this);
        drugFragmentBinding.drugRecyclerView.setAdapter(drugListAdapter);
      //  ((MainActivity) getActivity()).getDelegate().setSupportActionBar(toolbar);

        medicineViewModel = new ViewModelProvider(requireActivity()).get(MedicineViewModel.class);

        mViewModel = new ViewModelProvider(requireActivity()).get(DrugViewModel.class);

        drugFragmentBinding.drugRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));
           try {

        //section = String.valueOf(medicineViewModel.getSubCategory().getValue().getId());

        mViewModel.getDrugs(String.valueOf(section)).observe(getViewLifecycleOwner(), new Observer<List<Drug>>() {
            @Override
            public void onChanged(List<Drug> drugs) {
                drugFragmentBinding.progressBar.setVisibility(View.GONE);
                drugListAdapter.submitList(drugs);
                drugListAdapter.notifyDataSetChanged();
            }
        });

               section_id = medicineViewModel.getSubCategory().getValue().getId();
               Log.d("FUFUFUFU",section_id);

               mViewModel.getDrugs(section_id).observe(getViewLifecycleOwner(), new Observer<List<Drug>>() {
                   @Override
                   public void onChanged(List<Drug> drugs) {
                       drugFragmentBinding.progressBar.setVisibility(View.GONE);
                       drugListAdapter.submitList(drugs);
                       drugListAdapter.notifyDataSetChanged();
                   }
               });
           } catch (Exception e) {
               e.printStackTrace();
           }
        navController = Navigation.findNavController(view);
    }

    public void addItem(Drug drug) {
        boolean isAdded = mViewModel.addItemToCart(drug,quantity);

        if (isAdded) {
//            Snackbar.make(requireView(), product.getName() + " added to cart.", Snackbar.LENGTH_LONG)
//                    .setAction("Checkout", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            navController.navigate(R.id.action_shopFragment_to_cartFragment);
//                        }
//                    })
//                    .show();
            AlertAdded alertAdded = new AlertAdded(getActivity());
            alertAdded.showSuccessDialogAdd(drug.getDrugs_name());
//        } else {
//            Snackbar.make(requireView(), "Already have the max quantity in cart.", Snackbar.LENGTH_LONG)
//                    .show();
        }
        else  {
            Toast.makeText(getActivity(), "not add", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(Drug drug) {
        mViewModel.setDrug(drug);
        navController.navigate(R.id.action_drugFragment_to_drugDetailsFragment);
    }
}