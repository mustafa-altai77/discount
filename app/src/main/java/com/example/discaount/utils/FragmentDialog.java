package com.example.discaount.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.discaount.R;
import com.example.discaount.viewmodels.DrugViewModel;
import com.example.discaount.viewmodels.ShopViewModel;
import com.example.discaount.views.HomeFragment;


public class FragmentDialog extends DialogFragment {
    TextView t1, t2;
    Button button;
    NavController navController;
    ShopViewModel shopViewModel;
    DrugViewModel drugViewModel;

    @Override

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);
        drugViewModel = new ViewModelProvider(requireActivity()).get(DrugViewModel.class);
        navController = Navigation.findNavController(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_show_dialog, container, false);
        t1 = view.findViewById(R.id.textTitle);
        // t1.setText(title);
        t2 = view.findViewById(R.id.textMessage);
        //t2.setText(message);
        button = view.findViewById(R.id.buttonAction);
        button.setText(getActivity().getResources().getString(R.string.okey));
        ImageView imageView = view.findViewById(R.id.imageMessage);
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim);
        imageView.setAnimation(animation);
        imageView.setImageResource(R.drawable.ic_delivery);

        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopViewModel.resetCart();
                drugViewModel.resetCart();
                navController.navigate(R.id.action_show_dialog_fragment_to_homeFragment);

            }
        });
        return view;
    }
}
