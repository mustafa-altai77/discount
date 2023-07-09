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
import com.example.discaount.data.model.SubCategory;
import com.example.discaount.databinding.CardSubRowBinding;
import com.example.discaount.databinding.MMedicineRowBinding;

public class MedicineAdapter2 extends ListAdapter<Medicine, MedicineAdapter2.SubCategoryHolder> {
    MedicineInterface medicineInterface;
    private NavController navController;

    public MedicineAdapter2(MedicineInterface medicineInterface) {
        super(Medicine.itemCallback);
        this.medicineInterface = medicineInterface;
    }
    @NonNull
    @Override
    public SubCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MMedicineRowBinding mMedicineRowBinding = MMedicineRowBinding
                .inflate(layoutInflater,parent,false);
        mMedicineRowBinding.setMedicineInterface(medicineInterface);
        return new SubCategoryHolder(mMedicineRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryHolder holder, int position) {
        Medicine medicine = getItem(position);
        holder.mMedicineRowBinding.setMedicine(medicine);
        holder.mMedicineRowBinding.subCategoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController = Navigation.findNavController(v);
                Medicine medicine = getItem(position);
                String id = medicine.getId();
                Bundle bundle = new Bundle();
                bundle.putString("section_id", id);
                navController.navigate(R.id.action_MCategoriesFragment_to_drugFragment, bundle
                );

            }
        });

    }
    public class SubCategoryHolder extends RecyclerView.ViewHolder {
        MMedicineRowBinding mMedicineRowBinding;
        public SubCategoryHolder(@NonNull MMedicineRowBinding binding) {
            super(binding.getRoot());
            this.mMedicineRowBinding = binding;
        }
    }

    public interface MedicineInterface {
        void onItemClick(Medicine medicine);

    }
}
