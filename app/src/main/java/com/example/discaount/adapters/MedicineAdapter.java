package com.example.discaount.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discaount.R;
import com.example.discaount.data.model.Medicine;
import com.example.discaount.databinding.CardSubRowBinding;

public class MedicineAdapter extends ListAdapter<Medicine, MedicineAdapter.SubCategoryHolder> {
    MedicineInterface medicineInterface;
    private NavController navController;

    public MedicineAdapter(MedicineInterface medicineInterface) {
        super(Medicine.itemCallback);
        this.medicineInterface = medicineInterface;
    }
    @NonNull
    @Override
    public SubCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CardSubRowBinding cardSubRowBinding = CardSubRowBinding
                .inflate(layoutInflater,parent,false);
        cardSubRowBinding.setMedicineInterface(medicineInterface);
        return new SubCategoryHolder(cardSubRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryHolder holder, int position) {
        Medicine medicine = getItem(position);
        holder.cardSubRowBinding.setMedicine(medicine);
        holder.cardSubRowBinding.medicineCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController = Navigation.findNavController(v);
                Medicine medicine = getItem(position);
                String id = medicine.getId();
                Bundle bundle = new Bundle();
                bundle.putString("section_id", id);
                navController.navigate(R.id.action_homeFragment_to_drugFragment, bundle
                );

            }
        });
    }
    public class SubCategoryHolder extends RecyclerView.ViewHolder {
        CardSubRowBinding cardSubRowBinding;
        public SubCategoryHolder(@NonNull CardSubRowBinding binding) {
            super(binding.getRoot());
            this.cardSubRowBinding = binding;
        }
    }

    public interface MedicineInterface {
        void onItemClick(Medicine medicine);
    }
}
