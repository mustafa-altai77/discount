package com.example.discaount.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discaount.data.model.Drug;
import com.example.discaount.data.model.Product;
import com.example.discaount.databinding.DrugRowBinding;
import com.example.discaount.databinding.ProductRowBinding;
public class DrugListAdapter extends ListAdapter<Drug, DrugListAdapter.ShopViewHolder> {
    DrugInterface drugInterface;
    public DrugListAdapter(DrugInterface drugInterface) {
        super(Drug.itemCallback);
        this.drugInterface = drugInterface;
    }
    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        DrugRowBinding drugRowBinding = DrugRowBinding.inflate(layoutInflater, parent, false);
        drugRowBinding.setDrugInterface(drugInterface);
        return new ShopViewHolder(drugRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        Drug drug = getItem(position);
        holder.drugRowBinding.setDrug(drug);
       holder.drugRowBinding.executePendingBindings();

       if (drug.getStatus()==1){
           holder.drugRowBinding.statusDrug.setVisibility(View.GONE);
       }
       else
           holder.drugRowBinding.statusDrug.setVisibility(View.VISIBLE);

    }

    class ShopViewHolder extends RecyclerView.ViewHolder {

        DrugRowBinding drugRowBinding;

        public ShopViewHolder(DrugRowBinding binding) {
            super(binding.getRoot());
            this.drugRowBinding = binding;
        }
    }
    public interface DrugInterface {
        void addItem(Drug drug);
        void onItemClick(Drug drug);
    }
}
