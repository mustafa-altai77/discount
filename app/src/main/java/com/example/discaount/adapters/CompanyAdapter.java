package com.example.discaount.adapters;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discaount.R;
import com.example.discaount.data.model.Categories;
import com.example.discaount.data.model.Companies;
import com.example.discaount.data.model.Medicine;
import com.example.discaount.databinding.CategoryItemBinding;
import com.example.discaount.databinding.CompanyItemBinding;

public class CompanyAdapter extends ListAdapter<Companies, CompanyAdapter.CategoryHolder> {
    CompanyInterface companyInterface;
    private NavController navController;
    String TAG = "CompanyAdapter";

    public CompanyAdapter(CompanyInterface companyInterface) {
        super(Companies.itemCallback);
        this.companyInterface = companyInterface;
    }

    @NonNull
    @Override
    public CompanyAdapter.CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CompanyItemBinding companyItemBinding = CompanyItemBinding
                .inflate(layoutInflater, parent, false);
        companyItemBinding.setCompanyInterface(companyInterface);
        return new CategoryHolder(companyItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyAdapter.CategoryHolder holder, int position) {
        Companies companies = getItem(position);
        holder.companyItemBinding.setCompany(companies);
//        holder.companyItemBinding.company.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navController = Navigation.findNavController(v);
//                Companies companies = getItem(position);
//                String id = String.valueOf(companies.getId());
//
//                try {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("company_id", id);
//                    navController.navigate(R.id.action_MCategoriesFragment_to_companuDrugFragment, bundle);
//                } catch (Exception e) {
//                    Log.d(TAG, e + "\n"+id);
//                }
//            }
//        });
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {
        CompanyItemBinding companyItemBinding;

        public CategoryHolder(@NonNull CompanyItemBinding binding) {
            super(binding.getRoot());
            this.companyItemBinding = binding;
        }
    }

    public interface CompanyInterface {
        void onItemClick(Companies companies);
    }
}
