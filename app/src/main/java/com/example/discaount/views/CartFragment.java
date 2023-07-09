package com.example.discaount.views;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.discaount.R;
import com.example.discaount.adapters.CartDrugAdapter;
import com.example.discaount.adapters.CartListAdapter;
import com.example.discaount.data.model.CartItem;
import com.example.discaount.data.model.CartItemDrug;
import com.example.discaount.data.model.ImageResponse;
import com.example.discaount.data.model.OrderRequest;
import com.example.discaount.data.model.OrderResponse;
import com.example.discaount.data.model.Prescriptions;
import com.example.discaount.data.model.ProductResponse;
import com.example.discaount.data.network.ApiClient;
import com.example.discaount.data.network.ApiInterface;
import com.example.discaount.databinding.FragmentCartBinding;
import com.example.discaount.utils.Alert;
import com.example.discaount.utils.LoadingDialog;
import com.example.discaount.utils.SharedPref;
import com.example.discaount.viewmodels.DrugViewModel;
import com.example.discaount.viewmodels.ImageViewModel;
import com.example.discaount.viewmodels.ShopViewModel;
import com.example.discaount.views.activity.LoginActivity;
import com.example.discaount.views.activity.MainActivity;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.discaount.utils.SharedPref.F_NAME;
import static com.example.discaount.utils.SharedPref.TOKEN;
import static com.example.discaount.utils.SharedPref.mCtx;
import static com.example.discaount.utils.SharedPreferencesLocalStorage.SHARED_PREF_NAME;

public class CartFragment extends Fragment implements CartListAdapter.CartInterface, CartDrugAdapter.CartDrugInterface {

    private static final String TAG = "CartFragment";
    LoadingDialog loadingDialog;
    ShopViewModel shopViewModel;
    DrugViewModel drugViewModel;
    File imageFile;
    String path=null;
    String imagePath = null;
    FragmentCartBinding fragmentCartBinding;
    NavController navController;
     double totalD ,totalP;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        loadingDialog = new LoadingDialog(getActivity());
        fragmentCartBinding = FragmentCartBinding.inflate(inflater, container, false);
        imagePath = "";
        if (getArguments() != null) {
            imagePath = getArguments().getString("path");
            path = getArguments().getString("path2");
            fragmentCartBinding.imageAttached.setVisibility(View.VISIBLE);
            fragmentCartBinding.imageAttached.setImageBitmap(BitmapFactory.decodeFile(path));
        }
        else {
        }

        return fragmentCartBinding.getRoot();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 7 && resultCode == Activity.RESULT_OK) {
            path = getPathFromCameraData(data, this.getActivity());
            Log.d("PICTURE", "Path: " + path);
            if (path != null) {
                fragmentCartBinding.imageAttached.setVisibility(View.VISIBLE);
                fragmentCartBinding.imageAttached.setImageBitmap(BitmapFactory.decodeFile(path));
                fragmentCartBinding.placeOrderButton.setVisibility(View.VISIBLE);
                fragmentCartBinding.allUnder.setVisibility(View.VISIBLE);
                imageFile = new File(path);
                File file = saveBitmapToFile(imageFile);
                uploadImage(file);
            }
        }
    }

    public File saveBitmapToFile(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        navController = Navigation.findNavController(view);

        final CartListAdapter cartListAdapter = new CartListAdapter(this);
        final CartDrugAdapter cartDrugAdapter = new CartDrugAdapter(this);
        fragmentCartBinding.cartRecyclerView.setAdapter(cartListAdapter);
        fragmentCartBinding.cartRecyclerView2.setAdapter(cartDrugAdapter);
        fragmentCartBinding.cartRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        fragmentCartBinding.cartRecyclerView2.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);
        drugViewModel = new ViewModelProvider(requireActivity()).get(DrugViewModel.class);



        fragmentCartBinding.paymentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "" + getResources().getString(R.string.soon), Toast.LENGTH_SHORT).show();
            }
        });

        fragmentCartBinding.uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SharedPref.getInstance(getContext()).isLoggedIn()) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage("" + getResources().getString(R.string.should));
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "" + getResources().getString(R.string.sign_in),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(getContext(), LoginActivity.class));
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
                    //*********************************************************************
                } else if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION},
                            7);

                } else {
                    pickImage();
                }

            }
        });
        if (drugViewModel.getCart().getValue().isEmpty() && shopViewModel.getCart().getValue().isEmpty() && path==null) {
            fragmentCartBinding.relCartEmpty.setVisibility(View.VISIBLE);
            fragmentCartBinding.allUnder.setVisibility(View.GONE);
        } else {
            fragmentCartBinding.relCartEmpty.setVisibility(View.GONE);
            fragmentCartBinding.allUnder.setVisibility(View.VISIBLE);
        }
//Todo edit **********************************
        drugViewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double dDouble) {
                totalD=dDouble;
                fragmentCartBinding.orderTotalD.setText(totalP+totalD +" " + getContext().getResources().getString(R.string.sd));
            }
        });
        shopViewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double dDouble) {
               totalP=dDouble;
                fragmentCartBinding.orderTotalD.setText(totalP+totalD +" " + getContext().getResources().getString(R.string.sd));

            }
        });
        fragmentCartBinding.orderTotalD.setText(totalP+totalD  + getContext().getResources().getString(R.string.sd));
        shopViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                if (cartItems.isEmpty()) {

                    fragmentCartBinding.productText.setVisibility(View.GONE);
                }
                cartListAdapter.submitList(cartItems);
                //  fragmentCartBinding.placeOrderButton.setEnabled(cartItems.size() > 0);
            }
        });

        drugViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<CartItemDrug>>() {
            @Override
            public void onChanged(List<CartItemDrug> cartItemDrugs) {
                if (cartItemDrugs.isEmpty()) {
                    fragmentCartBinding.drugText.setVisibility(View.GONE);
                }
                cartDrugAdapter.submitList(cartItemDrugs);


                //fragmentCartBinding.placeOrderButton.setEnabled(cartItemDrugs.size() > 0);
            }
        });

        fragmentCartBinding.placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String message =getResources().getString(R.string.cartEmpty);
                if (!SharedPref.getInstance(getContext()).isLoggedIn()) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage("" + getResources().getString(R.string.should));
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
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("path", imagePath);
                    bundle.putInt("which", 1);
                    navController.navigate(R.id.action_cartFragment_to_orderFragment, bundle);
                }

//                if (fragmentCartBinding.checkBox.isChecked()) {
//                    advice = true;
//                }

            }
        });
    }

    private void pickImage() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 7);
    }

    SharedPreferences sharedPreference = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

    private void uploadImage(File file) {
        final String token = sharedPreference.getString(TOKEN, "token");

        final RequestBody requestBody1 = RequestBody.create(MediaType.parse("images/*"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("images", file.getName(), requestBody1);
        Log.d(token, "token");
        loadingDialog.startLoadingDialog();
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<ImageResponse> call = apiService.upLoadImage(token, filePart);
        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {

                if (response.isSuccessful()) {
                    try {
                        loadingDialog.dismissDialog();
                        imagePath = response.body().getPath().get(0);
                        //   Toast.makeText(getContext(), imagePath, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.d("ERRORR", e + "");
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getContext(), "" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.d("MLML", t + "");
            }
        });
    }


    public static String getPathFromCameraData(Intent data, Context context) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }

    @Override
    public void deleteItem(CartItem cartItem) {
        shopViewModel.removeItemFromCart(cartItem);

    }

    @Override
    public void changeQuantity(CartItem cartItem, int quantity) {
        shopViewModel.changeQuantity(cartItem, quantity);
    }

    @Override
    public void deleteItem(CartItemDrug cartItemDrug) {
        drugViewModel.removeItemFromCart(cartItemDrug);
    }

    @Override
    public void changeQuantity(CartItemDrug cartItem, int quantity) {
        drugViewModel.changeQuantity(cartItem, quantity);
    }
}