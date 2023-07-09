package com.example.discaount.adapters;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discaount.R;
import com.example.discaount.data.model.CartItem;
import com.example.discaount.databinding.CartDrugRowBinding;
import com.example.discaount.data.model.CartItemDrug;
import com.example.discaount.views.activity.LoginActivity;

public class CartDrugAdapter extends ListAdapter<CartItemDrug, CartDrugAdapter.CartVH> {
    private CartDrugInterface cartInterface;

    public CartDrugAdapter(CartDrugInterface cartInterface) {
        super(CartItemDrug.itemCallback);
        this.cartInterface = cartInterface;
    }

    @NonNull
    @Override
    public CartDrugAdapter.CartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CartDrugRowBinding cartRowBinding = CartDrugRowBinding.inflate(layoutInflater, parent, false);
        return new CartVH(cartRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartDrugAdapter.CartVH holder, int position) {
        holder.cartRowBinding.setCartItem(getItem(position));
        holder.cartRowBinding.executePendingBindings();
    }

    class CartVH extends RecyclerView.ViewHolder {

        CartDrugRowBinding cartRowBinding;

        public CartVH(@NonNull CartDrugRowBinding cartRowBinding) {
            super(cartRowBinding.getRoot());
            this.cartRowBinding = cartRowBinding;

            cartRowBinding.deleteDrugButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                    builder1.setMessage("" + v.getResources().getString(R.string.remove_cart) +
                            " " + getItem(getAdapterPosition()).getDrug().getDrugs_name() +
                            " " + v.getResources().getString(R.string.cart));
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "" + v.getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    cartInterface.deleteItem(getItem(getAdapterPosition()));
                                }
                            });
                    builder1.setNegativeButton(
                            "" + v.getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                }
            });
            cartRowBinding.cartPlusImgDrug.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {


                        int quantity = getItem(getAdapterPosition()).getQuantity() + 1;
                        if (quantity == getItem(getAdapterPosition()).getQuantity()) {
                            return;
                        }
                        CartItemDrug newCartItem = getItem(getAdapterPosition());
                        newCartItem.setQuantity(newCartItem.getQuantity() + quantity);
                        cartInterface.changeQuantity(getItem(getAdapterPosition()), quantity);
                        cartRowBinding.cartDrugQuantityTv.setText(String.valueOf(quantity));
//                    cartInterface.changeQuantity(getAdapterPosition());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            cartRowBinding.cartMinusImgDrug.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {


                        int quantity = getItem(getAdapterPosition()).getQuantity() - 1;
                        if (quantity <= 0) {
                            quantity = getItem(getAdapterPosition()).getQuantity();
                        }
                        if (quantity == getItem(getAdapterPosition()).getQuantity()) {
                            return;
                        }
//                    Log.d("asns", "onClick: "+getAdapterPosition());

                        cartInterface.changeQuantity(getItem(getAdapterPosition()), quantity);
                        cartRowBinding.cartDrugQuantityTv.setText(String.valueOf(quantity));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public interface CartDrugInterface {
        void deleteItem(CartItemDrug cartItem);

        void changeQuantity(CartItemDrug cartItem, int quantity);
    }
}