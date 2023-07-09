package com.example.discaount.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.discaount.data.model.Offer;
import com.example.discaount.databinding.OfferItemBinding;

public class OfferAdapter extends ListAdapter<Offer, OfferAdapter.OfferViewHolder> {
    OfferInterface offerInterface;
    public OfferAdapter(OfferInterface offerInterface) {
        super(Offer.itemCallback);
        this.offerInterface = offerInterface;
    }
    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        OfferItemBinding offerItemBinding = OfferItemBinding.inflate(layoutInflater, parent, false);
        offerItemBinding.setShopInterface(offerInterface);
        return new OfferViewHolder(offerItemBinding);
    }
    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
        Offer offer = getItem(position);
        holder.offerItemBinding.setOffer(offer);
        holder.offerItemBinding.executePendingBindings();
    }
    class OfferViewHolder extends RecyclerView.ViewHolder {

        OfferItemBinding offerItemBinding;
        public OfferViewHolder(OfferItemBinding binding) {
            super(binding.getRoot());
            this.offerItemBinding = binding;
        }
    }

    public interface OfferInterface {
        void addItem(Offer offer);
        void onItemClick(Offer offer);
    }
}
