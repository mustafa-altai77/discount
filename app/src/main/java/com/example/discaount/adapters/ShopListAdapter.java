package com.example.discaount.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

;import com.example.discaount.data.model.Product;
import com.example.discaount.databinding.ProductRowBinding;

public class ShopListAdapter extends ListAdapter<Product, ShopListAdapter.ShopViewHolder> {
    ShopInterface shopInterface;
    Context context;
    public ShopListAdapter(ShopInterface shopInterface) {
        super(Product.itemCallback);
        this.shopInterface = shopInterface;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ProductRowBinding shopRowBinding = ProductRowBinding.inflate(layoutInflater, parent, false);
        shopRowBinding.setShopInterface(shopInterface);
        return new ShopViewHolder(shopRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        Product product = getItem(position);
        holder.shopRowBinding.setProduct(product);
        holder.shopRowBinding.executePendingBindings();
    }
    class ShopViewHolder extends RecyclerView.ViewHolder {
        ProductRowBinding shopRowBinding;
        public ShopViewHolder(ProductRowBinding binding) {
            super(binding.getRoot());
            this.shopRowBinding = binding;
        }
    }
    public interface ShopInterface {
        void addItem(Product product);
        void onItemClick(Product product);
    }
}
