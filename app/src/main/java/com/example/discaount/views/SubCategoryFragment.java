package com.example.discaount.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.discaount.adapters.SubCategoryAdapter;
import com.example.discaount.data.model.SubCategory;
import com.example.discaount.R;
import com.example.discaount.databinding.FragmentSubCategoryBinding;
import com.example.discaount.viewmodels.CategoriesViewModel;
import com.example.discaount.viewmodels.ShopViewModel;
import com.example.discaount.viewmodels.MedicineViewModel;
import com.example.discaount.viewmodels.SubCategoryViewModel;

import java.util.List;

public class SubCategoryFragment extends Fragment implements SubCategoryAdapter.SubCategoryInterface {
    FragmentSubCategoryBinding fragmentSubCategoryBinding;
    private NavController navController;
    private CategoriesViewModel categoriesViewModel;
    SubCategoryAdapter subCategoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        categoriesViewModel = new ViewModelProvider(requireActivity()).get(CategoriesViewModel.class);
        // Inflate the layout for this fragment
        fragmentSubCategoryBinding = FragmentSubCategoryBinding.inflate(inflater, container, false);

        return fragmentSubCategoryBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subCategoryAdapter = new SubCategoryAdapter(this);
        fragmentSubCategoryBinding.subCategoryRecyclerView.setAdapter(subCategoryAdapter);
        fragmentSubCategoryBinding.subCategoryRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        fragmentSubCategoryBinding.subCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        categoriesViewModel = new ViewModelProvider(requireActivity()).get(CategoriesViewModel.class);

        List<SubCategory> subCategories = categoriesViewModel.getCategory().getValue().getSubCategories();
        subCategoryAdapter.submitList(subCategories);
        subCategoryAdapter.notifyDataSetChanged();
        navController = Navigation.findNavController(view);
        if (getArguments() != null) {
            String itemPas=getArguments().getString("itemPas");
            String imagePass=getArguments().getString("imagePass");
            fragmentSubCategoryBinding.nameOfCategory.setText(""+itemPas);
            Glide.with(fragmentSubCategoryBinding.imagePassed).load("https://shayoub.com/discount/public/icon/Categories/"+imagePass)
                    .fitCenter()
                    .into(fragmentSubCategoryBinding.imagePassed);
        }
        else {
           // Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(SubCategory subCategory) {
        // subCategory.getId();
        navController.navigate(R.id.action_subCategoryFragment2_to_shopFragment);
    }
}