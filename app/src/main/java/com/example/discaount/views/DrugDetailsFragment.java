package com.example.discaount.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.discaount.R;
import com.example.discaount.adapters.CartDrugAdapter;
import com.example.discaount.adapters.DrugListAdapter;
import com.example.discaount.data.model.CartItem;
import com.example.discaount.data.model.CartItemDrug;
import com.example.discaount.data.model.Drug;
import com.example.discaount.data.model.SubCategory;
import com.example.discaount.databinding.DrugDetailsFragmentBinding;
import com.example.discaount.databinding.FragmentProductDetailBinding;
import com.example.discaount.utils.AlertAdded;
import com.example.discaount.utils.BaseActivity;
import com.example.discaount.utils.MySharePreference;
import com.example.discaount.viewmodels.CompanyDrugViewModel;
import com.example.discaount.viewmodels.DrugViewModel;
import com.example.discaount.viewmodels.ShopViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static com.example.discaount.utils.SharedPref.LANG;
import static com.example.discaount.utils.SharedPref.SHARED_PREF_NAME;
import static com.example.discaount.utils.SharedPref.mCtx;

public class DrugDetailsFragment extends Fragment implements CartDrugAdapter.CartDrugInterface {


    int quantity = 1;
    DrugViewModel mViewModel;
    private NavController navController;
    DrugDetailsFragmentBinding drugDetailsFragmentBinding;

    public static DrugDetailsFragment newInstance() {
        return new DrugDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        drugDetailsFragmentBinding = DrugDetailsFragmentBinding.inflate(inflater, container, false);
        return drugDetailsFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(DrugViewModel.class);
        drugDetailsFragmentBinding.setDrugViewModel(mViewModel);
        navController = Navigation.findNavController(view);
        drugDetailsFragmentBinding.cartPlusImgDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quantity = quantity + 1;
                drugDetailsFragmentBinding.cartDrugQuantityTv.setText(String.valueOf(quantity));


            }
        });
        drugDetailsFragmentBinding.cartMinusImgDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = quantity - 1;
                if (quantity <= 0) {
                    quantity = 1;
                }
                mViewModel.removeOne(mViewModel.getDrug().getValue());
                drugDetailsFragmentBinding.cartDrugQuantityTv.setText(String.valueOf(quantity));
            }
        });
        drugDetailsFragmentBinding.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.addItemToCart(mViewModel.getDrug().getValue(), quantity);
                navController.navigate(R.id.action_drugDetailsFragment_to_cartFragment);
            }
        });
        drugDetailsFragmentBinding.infoCheaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getDrug().getValue().getId();
               // Toast.makeText(getContext(), mViewModel.getDrug().getValue().getId() + "", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.action_drugDetailsFragment_to_instanceFragment);
            }
        });

        drugDetailsFragmentBinding.relIndication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resone = mViewModel.getDrug().getValue().getReasonuse().toString();
                Bundle bundle = new Bundle();
                bundle.putString("drug", resone);
                navController.navigate(R.id.action_drugDetailsFragment_to_textFragment, bundle
                );
            }
        });

        drugDetailsFragmentBinding.relSideEffect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String efect = mViewModel.getDrug().getValue().getSideeffects().toString();
                Bundle bundle = new Bundle();
                bundle.putString("drug", efect);
                navController.navigate(R.id.action_drugDetailsFragment_to_textFragment, bundle
                );
            }
        });
        drugDetailsFragmentBinding.relDose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dose = mViewModel.getDrug().getValue().getDosing().toString();
                Bundle bundle = new Bundle();
                bundle.putString("drug", dose);
                navController.navigate(R.id.action_drugDetailsFragment_to_textFragment, bundle);
            }
        });

        drugDetailsFragmentBinding.relActiveIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String active = mViewModel.getDrug().getValue().getActivesubstance().toString();
                Bundle bundle = new Bundle();
                bundle.putString("drug", active);
                navController.navigate(R.id.action_drugDetailsFragment_to_textFragment, bundle
                );
            }
        });
        drugDetailsFragmentBinding.relInteractions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String action = mViewModel.getDrug().getValue().getInteractions().toString();
                Bundle bundle = new Bundle();
                bundle.putString("drug", action);
                navController.navigate(R.id.action_drugDetailsFragment_to_textFragment, bundle
                );
            }
        });

        drugDetailsFragmentBinding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.addItemToCart(mViewModel.getDrug().getValue(), quantity);
                AlertAdded alertAdded = new AlertAdded(getActivity());
                alertAdded.showSuccessDialogAdd(mViewModel.getDrug().getValue().getDrugs_name());
            }
        });


        final MySharePreference mySharePreference = MySharePreference.getInstance(getActivity().getBaseContext());
        final String lang = mySharePreference.getLanguage();
        if (lang.equals("ar")) {
            drugDetailsFragmentBinding.immg1.setRotation(180);
            drugDetailsFragmentBinding.immg2.setRotation(180);
            drugDetailsFragmentBinding.immg3.setRotation(180);
            drugDetailsFragmentBinding.immg4.setRotation(180);
            drugDetailsFragmentBinding.immg5.setRotation(180);
        }
    }

    @Override
    public void deleteItem(CartItemDrug cartItem) {

    }

    @Override
    public void changeQuantity(CartItemDrug cartItem, int quantity) {
        mViewModel.changeQuantity(cartItem, quantity);
    }
}