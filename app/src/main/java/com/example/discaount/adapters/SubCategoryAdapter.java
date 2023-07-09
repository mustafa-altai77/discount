package com.example.discaount.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discaount.R;
import com.example.discaount.data.model.SubCategory;
import com.example.discaount.databinding.SubCategoryRowBinding;
import com.example.discaount.viewmodels.ShopViewModel;
import com.example.discaount.viewmodels.SubCategoryViewModel;
import com.example.discaount.views.SubCategoryFragment;

import java.util.Map;


public class SubCategoryAdapter extends ListAdapter<SubCategory, SubCategoryAdapter.VerticalHolder> {
    private NavController navController;
    Context context;
    SubCategoryViewModel subCategoryViewModel;
    SubCategoryAdapter.SubCategoryInterface subCategoryInterface;
    public SubCategoryAdapter(SubCategoryFragment subCategoryInterface){
        super(SubCategory.itemCallback);
        this.subCategoryInterface = subCategoryInterface;
    }

    @NonNull
    @Override
    public VerticalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        SubCategoryRowBinding subCategoryRowBinding = SubCategoryRowBinding
                .inflate(layoutInflater,parent,false);
        return new SubCategoryAdapter.VerticalHolder(subCategoryRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalHolder holder, int position) {
        SubCategory subCategory = getItem(position);
        holder.subCategoryRowBinding.setSubCategory(subCategory);
        holder.subCategoryRowBinding.subCategoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController = Navigation.findNavController(v);
                SubCategory subCategory = getItem(position);
                String id = subCategory.getId();
                Bundle bundle = new Bundle();
                bundle.putString("userId", id);
                navController.navigate(R.id.action_subCategoryFragment2_to_shopFragment, bundle
                );

            }
        });
        //holder.subCategoryRowBinding.subCategoryName.setText(subCategory.getName());

    }
    public static class VerticalHolder extends RecyclerView.ViewHolder {
        SubCategoryRowBinding  subCategoryRowBinding;
        public VerticalHolder(@NonNull SubCategoryRowBinding binding) {
            super(binding.getRoot());
            this.subCategoryRowBinding = binding;
        }

    }
    public interface SubCategoryInterface {
        void onItemClick(SubCategory subCategory);
    }
}
