package com.example.discaount.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.discaount.data.model.MyOrder;
import com.example.discaount.databinding.OrderItemsBinding;

public class MyOrderAdapter extends ListAdapter<MyOrder, MyOrderAdapter.SubCategoryHolder> {
    //  MyOrderInterface myOrderInterface;
    public MyOrderAdapter() {
        super(MyOrder.itemCallback);
        //this.myOrderInterface = myOrderInterface;
    }
    @NonNull
    @Override
    public SubCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        OrderItemsBinding orderItemsBinding = OrderItemsBinding
                .inflate(layoutInflater,parent,false);
        //orderItemsBinding.setSubCategoryInterface(myOrderInterface);
        return new SubCategoryHolder(orderItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryHolder holder, int position) {
        MyOrder myOrder = getItem(position);
       if (!myOrder.getStatus_id().equals("انتظار"))
       {
           holder.cardSubRowBinding.relState.setBackgroundColor(Color.parseColor("#757575"));
       }
        holder.cardSubRowBinding.setMyOrder(myOrder);
    }
    public class SubCategoryHolder extends RecyclerView.ViewHolder {
        OrderItemsBinding cardSubRowBinding;
        public SubCategoryHolder(@NonNull OrderItemsBinding binding) {
            super(binding.getRoot());
            this.cardSubRowBinding = binding;
        }
    }

//    public interface MyOrderInterface {
//        void onItemClick(MyOrder myOrder);
//
//    }
}