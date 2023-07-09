package com.example.discaount.views.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.discaount.R;
import com.example.discaount.data.model.OfferRequest;
import com.example.discaount.data.model.OrderRequest;
import com.example.discaount.data.model.OrderResponse;
import com.example.discaount.data.network.ApiClient;
import com.example.discaount.data.network.ApiInterface;
import com.example.discaount.databinding.FragmentOfferDetailsBinding;
import com.example.discaount.utils.LoadingDialog;
import com.example.discaount.utils.SharedPref;
import com.example.discaount.viewmodels.OfferViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.discaount.utils.SharedPref.PHONE;
import static com.example.discaount.utils.SharedPref.TOKEN;
import static com.example.discaount.utils.SharedPref.mCtx;
import static com.example.discaount.utils.SharedPreferencesLocalStorage.SHARED_PREF_NAME;

public class OfferDetailsFragment extends Fragment {
    OfferViewModel mViewModel;
    LoadingDialog loadingDialog;
    int amount = 1;
    Location location;
    double Latitude =0.0;
    double Longitude =0.0;
    NavController navController;

    FragmentOfferDetailsBinding fragmentOfferDetailsBinding;
    public static OfferDetailsFragment newInstance() {
        return new OfferDetailsFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentOfferDetailsBinding = FragmentOfferDetailsBinding.inflate(inflater, container, false);
        loadingDialog = new LoadingDialog(getActivity());
        return fragmentOfferDetailsBinding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(OfferViewModel.class);
        fragmentOfferDetailsBinding.setOfferViewModel(mViewModel);
        fragmentOfferDetailsBinding.amount.setText(String.valueOf(amount));
        navController = Navigation.findNavController(view);
        fragmentOfferDetailsBinding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = amount + 1;
                fragmentOfferDetailsBinding.amount.setText(String.valueOf(amount));
            }
        });

        fragmentOfferDetailsBinding.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = amount - 1;
                if (amount<=0){
                    amount = 1;
                }
                fragmentOfferDetailsBinding.amount.setText(String.valueOf(amount));
            }
        });

        fragmentOfferDetailsBinding.sendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SharedPref.getInstance(getContext()).isLoggedIn()) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage("" + getResources().getString(R.string.should_option));
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "" + getResources().getString(R.string.sign_in),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(getContext(), LoginActivity.class));
                                    //  Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                }
                            });

                    builder1.setNegativeButton(
                            "" + getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();


                }
                else {
                Bundle bundle = new Bundle();
                int id =mViewModel.getOffer().getValue().getId();
                bundle.putInt("id", id);
                bundle.putInt("which", 2);
                bundle.putInt("amount", amount);
                navController.navigate(R.id.action_offerDetailsFragment_to_orderFragment, bundle);
                }
    }
        });
    }
}