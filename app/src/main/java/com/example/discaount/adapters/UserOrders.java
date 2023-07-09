package com.example.discaount.adapters;

import androidx.annotation.StringRes;

import com.example.discaount.data.model.MyOrder;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserOrders {
    @SerializedName("data")
    List<MyOrder> myOrders;
}
