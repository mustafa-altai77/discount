package com.example.discaount.views.more;

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
import com.example.discaount.R;
import com.example.discaount.adapters.MedicineAdapter;
import com.example.discaount.adapters.MedicineAdapter2;
import com.example.discaount.data.model.Medicine;
import com.example.discaount.databinding.MedicineFragment2Binding;
import com.example.discaount.databinding.MedicineFragmentBinding;
import com.example.discaount.viewmodels.MedicineViewModel;

import java.util.List;

public class MedicineFragment extends Fragment implements MedicineAdapter2.MedicineInterface {
     MedicineFragment2Binding medicineFragment2Binding;
     MedicineAdapter2 medicineAdapter2;
     MedicineViewModel medicineViewModel;
    private NavController navController;
    public static MedicineFragment newInstance() {
        return new MedicineFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        medicineFragment2Binding = MedicineFragment2Binding.inflate(inflater, container, false);
        return medicineFragment2Binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        medicineAdapter2 = new MedicineAdapter2(this);
        navController = Navigation.findNavController(view);
        medicineFragment2Binding.CategoryRecyclerView.setAdapter(medicineAdapter2);
        medicineFragment2Binding.CategoryRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), 0));
        // homeFragmentBinding.progressBar.setVisibility(View.VISIBLE);

        medicineViewModel = new ViewModelProvider(requireActivity()).get(MedicineViewModel.class);
        medicineViewModel.getMedicines().observe(getViewLifecycleOwner(), new Observer<List<Medicine>>() {

            @Override
            public void onChanged(List<Medicine> medicines) {
                medicineFragment2Binding.progressBar.setVisibility(View.GONE);
                medicineAdapter2.submitList(medicines);
                medicineAdapter2.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(Medicine medicine) {
        medicineViewModel.setMedicine(medicine);
        navController.navigate(R.id.action_MCategoriesFragment_to_drugFragment);
    }
}