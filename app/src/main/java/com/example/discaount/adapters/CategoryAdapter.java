package com.example.discaount.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discaount.data.model.Categories;
import com.example.discaount.data.model.Medicine;
import com.example.discaount.databinding.CategoryItemBinding;
import com.example.discaount.viewmodels.SubCategoryViewModel;

public class CategoryAdapter extends ListAdapter<Categories,CategoryAdapter.CategoryHolder> {
    CategoryInterface categoryInterface;
    public CategoryAdapter(CategoryInterface categoryInterface) {
        super(Categories.itemCallback);
        this.categoryInterface = categoryInterface;
    }
    @NonNull
    @Override
    public CategoryAdapter.CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CategoryItemBinding categoryItemBinding = CategoryItemBinding
                .inflate(layoutInflater, parent, false);
        categoryItemBinding.setCategoryInterface(categoryInterface);
        return new CategoryHolder(categoryItemBinding);
    }
    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryHolder holder, int position) {
        Categories categories = getItem(position);
        holder.categoryItemBinding.setCategory(categories);

    }
    public class CategoryHolder extends RecyclerView.ViewHolder {
        CategoryItemBinding categoryItemBinding;
        public CategoryHolder(@NonNull CategoryItemBinding binding) {
            super(binding.getRoot());
            this.categoryItemBinding = binding;
        }
    }

    public interface CategoryInterface{
        void onItemClick(Categories categories);
    }
}
