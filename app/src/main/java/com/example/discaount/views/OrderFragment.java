package com.example.discaount.views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.discaount.R;
import com.example.discaount.data.model.OfferRequest;
import com.example.discaount.data.model.OrderRequest;
import com.example.discaount.data.model.OrderAllResponse;
import com.example.discaount.data.model.Prescriptions;
import com.example.discaount.data.network.ApiClient;
import com.example.discaount.data.network.ApiInterface;
import com.example.discaount.databinding.FragmentOrderBinding;
import com.example.discaount.utils.LoadingDialog;
import com.example.discaount.utils.LocationCustomer;
import com.example.discaount.utils.ToastMessage;
import com.example.discaount.utils.Vibration;
import com.example.discaount.viewmodels.DrugViewModel;
import com.example.discaount.viewmodels.ShopViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.discaount.utils.SharedPref.PHONE;
import static com.example.discaount.utils.SharedPref.TOKEN;
import static com.example.discaount.utils.SharedPref.mCtx;
import static com.example.discaount.utils.SharedPreferencesLocalStorage.SHARED_PREF_NAME;

public class OrderFragment extends Fragment {
    Animation shake;
    Vibration vibration;
    LoadingDialog loadingDialog;
    NavController navController;
    FragmentOrderBinding fragmentOrderBinding;
    ShopViewModel shopViewModel;
    DrugViewModel drugViewModel;
    double Latitude = 0.0;
    double Longitude = 0.0;
    Boolean advice;
    int which, id, amount;
    SharedPreferences sharedPreference;

    FusedLocationProviderClient client;
    String phoneShar, phoneEdit, comment, city, street, imagePath, LiveAdd, token, Address;

    LocationCustomer getCurrontLocation;
    String[] parts = null;
    String part1, part2, part3;

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_order, container, false);
        fragmentOrderBinding = FragmentOrderBinding.inflate(inflater, container, false);
        if (getArguments() != null)
            imagePath = getArguments().getString("path");
        which = getArguments().getInt("which");
        id = getArguments().getInt("id");
        amount = getArguments().getInt("amount");

        return fragmentOrderBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getCurrontLocation = new LocationCustomer(getActivity());
        navController = Navigation.findNavController(view);
        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);
        drugViewModel = new ViewModelProvider(requireActivity()).get(DrugViewModel.class);
        sharedPreference = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        phoneShar = sharedPreference.getString(PHONE, "phone");
        token = sharedPreference.getString(TOKEN, "token");
        fragmentOrderBinding.phoneHoled.setText(phoneShar);
        loadingDialog = new LoadingDialog(getActivity());
        client = LocationServices.getFusedLocationProviderClient(getActivity());
        shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
        vibration = new Vibration(getContext());

        fragmentOrderBinding.btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                fragmentOrderBinding.customLocation.setVisibility(View.GONE);
                fragmentOrderBinding.liveLocation.setVisibility(View.VISIBLE);
                fragmentOrderBinding.btn.setBackgroundColor(Color.parseColor("#66af01"));
                fragmentOrderBinding.btn2.setBackgroundColor(Color.parseColor("#757575"));
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    String s = getCurrontLocation.getCurrontLocation();
                    //Toast.makeText(getContext(), ""+s, Toast.LENGTH_SHORT).show();
                    parts = s.split("#");
                    part1 = parts[0];
                    part2 = parts[1];
                    part3 = parts[2];
                    Latitude = Double.parseDouble(part1);
                    Longitude = Double.parseDouble(part2);
                    LiveAdd = part3;
                    if (part1.equals("0.0")) {
                        vibration.vibration();
                        fragmentOrderBinding.btn.setAnimation(shake);
                        fragmentOrderBinding.btn.setText("" + getResources().getString(R.string.try_agin));
                        fragmentOrderBinding.btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_go_back_arrow, 0, 0, 0);
                    } else {
                        fragmentOrderBinding.btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_location_on_24, 0, 0, 0);
                        fragmentOrderBinding.btn.setText("" + getResources().getString(R.string.get_location));
                        fragmentOrderBinding.liveAddress.setText("" + part3);
                    }
                } else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                }
            }
        });
        fragmentOrderBinding.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentOrderBinding.customLocation.setVisibility(View.VISIBLE);
                fragmentOrderBinding.liveLocation.setVisibility(View.GONE);
                fragmentOrderBinding.btn2.setBackgroundColor(Color.parseColor("#66af01"));
                fragmentOrderBinding.btn.setBackgroundColor(Color.parseColor("#757575"));
            }
        });
        //Toast.makeText(getContext(), imagePath+"     "+advice, Toast.LENGTH_SHORT).show();
        fragmentOrderBinding.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = String.valueOf(Longitude);
                if (s.equals("0.0") && fragmentOrderBinding.editTextCity.getText().toString().isEmpty()) {
                    ToastMessage message = new ToastMessage(getContext());
                    message.messageError("" + getResources().getString(R.string.choose_location), true);
                    return;
                }
                if (which == 1) {
                    sendOrder();

                } else {
                    sendOffer();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && (grantResults.length > 0) &&
                (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            String s = getCurrontLocation.getCurrontLocation();
            parts = s.split("#");
            part1 = parts[0];
            part2 = parts[1];
            part3 = parts[2];
            Latitude = Double.parseDouble(part1);
            Longitude = Double.parseDouble(part2);
            // fragmentOrderBinding.liveAddress.setText(""+part3);
            LiveAdd = part3;
            if (part1.equals("0.0")) {
                fragmentOrderBinding.btn.setText(getResources().getString(R.string.try_agin));
            } else {

                fragmentOrderBinding.liveAddress.setText("" + part3);
            }
        } else {
            //Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendOrder() {
        phoneEdit = fragmentOrderBinding.editTextPhone.getText().toString().trim();
        comment = fragmentOrderBinding.comment.getText().toString().trim();
        city = fragmentOrderBinding.editTextCity.getText().toString();
        street = fragmentOrderBinding.editTextStreet.getText().toString();
        final String token = sharedPreference.getString(TOKEN, "token");
        String FinalPhone, FinalComment;
        if (phoneEdit.length() <= 8 || phoneEdit.isEmpty()) {
            FinalPhone = phoneShar;
        } else {
            FinalPhone = phoneEdit;
        }
        if (comment.isEmpty()) {
            FinalComment = "NoComment";
        } else {
            FinalComment = comment;
        }
        Address = city + "-" + street + "-" + LiveAdd;


        if (imagePath.isEmpty()) {
            OrderRequest OrderRequest = new OrderRequest(shopViewModel.getCart().getValue(), drugViewModel.getCart().getValue()
                    , Longitude, Latitude, Address, FinalComment, FinalPhone);
            loadingDialog.startLoadingDialog();
            ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
            Call<OrderAllResponse> call = apiService.sendOrder(token, OrderRequest);
            call.enqueue(new Callback<OrderAllResponse>() {
                @Override
                public void onResponse(Call<OrderAllResponse> call, Response<OrderAllResponse> response) {
                    loadingDialog.dismissDialog();
                    if (response.isSuccessful()) {
                        navController.navigate(R.id.action_orderFragment_to_show_dialog_fragment);
                    } else {
                        Toast.makeText(getContext(), "" + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<OrderAllResponse> call, Throwable t) {
                    loadingDialog.dismissDialog();
                    Log.d("gggggg", "" + t.getMessage());
                }
            });
        } else {
            Prescriptions prescriptions = new Prescriptions(imagePath, false);
            OrderRequest orderRequest = new OrderRequest(shopViewModel.getCart().getValue(), drugViewModel.getCart().getValue(),
                    prescriptions, Longitude, Latitude, Address, FinalComment, FinalPhone);
            loadingDialog.startLoadingDialog();
            ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
            Call<OrderAllResponse> call = apiService.sendOrder(token, orderRequest);
            call.enqueue(new Callback<OrderAllResponse>() {
                @Override
                public void onResponse(Call<OrderAllResponse> call, Response<OrderAllResponse> response) {
                    loadingDialog.dismissDialog();
                    if (response.isSuccessful()) {
                        navController.navigate(R.id.action_orderFragment_to_show_dialog_fragment);
                    } else {
                        Toast.makeText(getContext(), "" + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<OrderAllResponse> call, Throwable t) {
                    loadingDialog.dismissDialog();
                    Log.d("gggggg", "" + t.getMessage());
                }
            });
        }


    }

    private void sendOffer() {
        try {
            phoneEdit = fragmentOrderBinding.editTextPhone.getText().toString();
            comment = fragmentOrderBinding.comment.getText().toString();
            city = fragmentOrderBinding.editTextCity.getText().toString();
            street = fragmentOrderBinding.editTextStreet.getText().toString();
            loadingDialog.startLoadingDialog();
            String FinalPhone, FinalComment;
            if (phoneEdit.length() <= 8 || phoneEdit.isEmpty()) {
                FinalPhone = phoneShar;
            } else {
                FinalPhone = phoneEdit;
            }
            if (comment.isEmpty()) {
                FinalComment = "NoComment";
            } else {
                FinalComment = comment;
            }
            Address = city + "-" + street + "-" + LiveAdd;
            OfferRequest offerRequest = new OfferRequest(FinalPhone, FinalComment, Address, Longitude, Latitude, id, amount);
            final String token = sharedPreference.getString(TOKEN, "token");
            ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
            Call<OfferRequest> call = apiService.sendOffer(token, offerRequest);
            call.enqueue(new Callback<OfferRequest>() {
                @Override
                public void onResponse(Call<OfferRequest> call, Response<OfferRequest> response) {
                    loadingDialog.dismissDialog();
                    if (response.isSuccessful()) {
                        navController.navigate(R.id.action_orderFragment_to_show_dialog_fragment);
                    } else {
                        Toast.makeText(getContext(), "" + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<OfferRequest> call, Throwable t) {
                    loadingDialog.dismissDialog();
                    Toast.makeText(getContext(), "" + t, Toast.LENGTH_SHORT).show();
                    Log.d("FAA", t + "");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "" + getResources().getString(R.string.check_location), Toast.LENGTH_SHORT).show();
        }
    }
}