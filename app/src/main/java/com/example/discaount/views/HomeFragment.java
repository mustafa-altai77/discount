package com.example.discaount.views;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.discaount.R;
import com.example.discaount.adapters.CategoryAdapter;
import com.example.discaount.adapters.MedicineAdapter;
import com.example.discaount.adapters.MySliderAddsAdapter;
import com.example.discaount.adapters.MySliderOffersAdapter;
import com.example.discaount.adapters.OfferAdapter;
import com.example.discaount.data.model.Categories;
import com.example.discaount.data.model.ImageResponse;
import com.example.discaount.data.model.ImageSliderOffResponse;
import com.example.discaount.data.model.ImageSliderOffer;
import com.example.discaount.data.model.Medicine;
import com.example.discaount.data.model.Offer;
import com.example.discaount.data.model.OfferResponse;
import com.example.discaount.data.network.ApiClient;
import com.example.discaount.data.network.ApiInterface;
import com.example.discaount.databinding.HomeFragmentBinding;
import com.example.discaount.medicine.MedicineActivity;
import com.example.discaount.utils.LoadingDialog;
import com.example.discaount.utils.SharedPref;
import com.example.discaount.viewmodels.CategoriesViewModel;
import com.example.discaount.viewmodels.MedicineViewModel;
import com.example.discaount.views.activity.LoginActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.discaount.utils.SharedPref.TOKEN;
import static com.example.discaount.utils.SharedPref.mCtx;
import static com.example.discaount.utils.SharedPreferencesLocalStorage.SHARED_PREF_NAME;
import static com.example.discaount.views.CartFragment.getPathFromCameraData;


public class HomeFragment extends Fragment implements MedicineAdapter.MedicineInterface, CategoryAdapter.CategoryInterface, OfferAdapter.OfferInterface {
    HomeFragmentBinding homeFragmentBinding;
    MedicineViewModel medicineViewModel;
    SharedPreferences sharedPreference;
    GridLayoutManager gridLayoutManager;
    CategoriesViewModel categoriesViewModel;
    OfferAdapter offerAdapter;
    LoadingDialog loadingDialog;
    private NavController navController;
    private MedicineAdapter medicineAdapter;
    private CategoryAdapter categoryAdapter;
    File imageFile;
    String path;
    String imagePath = null;
    String TAG = "HomeFragment";
    List<ImageSliderOffer> mySliderLists;
    MySliderAddsAdapter adapter;
    MySliderOffersAdapter offersAdapter;
    ImageSliderOffResponse ImageSliderOffResponse;
    private static int currentPage = 0;
    private int NUM_PAGES;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        homeFragmentBinding = HomeFragmentBinding.inflate(inflater, container, false);
        sharedPreference = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoadingDialog();


        homeFragmentBinding.cameraShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navController.navigate(R.id.action_homeFragment_to_cartFragment);
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


        homeFragmentBinding.bannerViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setupCurrentIndicator(position);

            }
        });

        homeFragmentBinding.bannerViewPagerOffer.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //   setupCurrentIndicator(position);
            }
        });
        //NUM_PAGES =onBordingLists.size();

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                } else {
                    currentPage++;
                }
                try {
                    homeFragmentBinding.bannerViewPager.setCurrentItem(currentPage, true);
                    // homeFragmentBinding.bannerViewPagerOffer.setCurrentItem(currentPage, true);
                } catch (Exception e) {
                    Log.d(TAG, e + "");
                }
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 6000, 6000);


        return homeFragmentBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO Change
        getAddData();
        Log.d(TAG, NUM_PAGES + "");
        medicineAdapter = new MedicineAdapter(this);
        categoryAdapter = new CategoryAdapter(this);
        offerAdapter = new OfferAdapter(this);
        homeFragmentBinding.medicineRecyclerView.setAdapter(medicineAdapter);
        homeFragmentBinding.categoryRecyclerView.setAdapter(categoryAdapter);
        //pagerAdapter = new ImageViewPagerAdapter(getActivity(),images);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        homeFragmentBinding.categoryRecyclerView.setLayoutManager(gridLayoutManager);
//        homeFragmentBinding.progressBar.setVisibility(View.VISIBLE);
        homeFragmentBinding.reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MedicineActivity.class);
                getActivity().startActivity(intent);
            }
        });
        homeFragmentBinding.more.setOnClickListener(new View.OnClickListener() {
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

                } else {
                    try {


                        navController.navigate(R.id.action_homeFragment_to_MCategoriesFragment);
                    } catch (Exception exception) {
                        Log.d(TAG, exception + "");
                    }
                }
            }
        });


        medicineViewModel = new ViewModelProvider(requireActivity()).get(MedicineViewModel.class);
        categoriesViewModel = new ViewModelProvider(requireActivity()).get(CategoriesViewModel.class);
        medicineViewModel.getMedicines().observe(getViewLifecycleOwner(), new Observer<List<Medicine>>() {

            @Override
            public void onChanged(List<Medicine> medicineResponse) {
                loadingDialog.dismissDialog();
//                homeFragmentBinding.progressBar.setVisibility(View.GONE);
                medicineAdapter.submitList(medicineResponse);
                medicineAdapter.notifyDataSetChanged();
            }
        });
        //Toast.makeText(getContext(), token+"", Toast.LENGTH_SHORT).show();
        categoriesViewModel.getCategories().observe(getViewLifecycleOwner(), new Observer<List<Categories>>() {
            @Override
            public void onChanged(List<Categories> categories) {
                //homeFragmentBinding.progressBar2.setVisibility(View.GONE);
                categoryAdapter.submitList(categories);
                categoryAdapter.notifyDataSetChanged();
            }
        });
        navController = Navigation.findNavController(view);
    }

    public void onItemClick(Categories categories) {
        categoriesViewModel.setCategory(categories);
        Bundle bundle = new Bundle();
        bundle.putString("itemPas", categories.getName());
        bundle.putString("imagePass", categories.getImageUrl());
        navController.navigate(R.id.action_homeFragment_to_subCategoryFragment2, bundle);
    }

    @Override
    public void addItem(Offer offer) {

    }

    @Override
    public void onItemClick(Offer offer) {
        navController.navigate(R.id.action_offerFragment_to_offerDetailsFragment);
    }

//    private void loadOffers() {
//        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
//        Call<OfferResponse> call = apiService.getOffer();
//        call.enqueue(new Callback<OfferResponse>() {
//            @Override
//            public void onResponse(Call<OfferResponse> call, Response<OfferResponse> response) {
//                List<Offer> offers;
//                offers = response.body().getOffer();
//
//            }
//
//            @Override
//            public void onFailure(Call<OfferResponse> call, Throwable t) {
//                Log.d("TAG", "Response = " + t.toString());
//            }
//        });
//    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 7 && resultCode == Activity.RESULT_OK) {
            path = getPathFromCameraData(data, this.getActivity());
            Log.d("PICTURE", "Path: " + path);
            //  Toast.makeText(getContext(), "" + path, Toast.LENGTH_SHORT).show();
            if (path != null) {
                imageFile = new File(path);
                File file = saveBitmapToFile(imageFile);
                uploadImage(file);
            }
        }
    }


    private void pickImage() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 7);
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
            final int REQUIRED_SIZE = 75;

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
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

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
                        Bundle bundle = new Bundle();
                        bundle.putString("path", imagePath);
                        bundle.putString("path2", path);
                        navController.navigate(R.id.action_homeFragment_to_cartFragment, bundle);

                        //   Toast.makeText(getContext(), imagePath, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.d("ERRORR", e + "");
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getContext(), "" + getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                loadingDialog.dismissDialog();
                Log.d("MLML", t + "");
            }
        });
    }

    @Override
    public void onItemClick(Medicine medicine) {

    }

    private void getAddData() {
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<ImageSliderOffResponse> call = apiService.getAddImage();
        call.enqueue(new Callback<ImageSliderOffResponse>() {
            @Override
            public void onResponse(Call<ImageSliderOffResponse> call, Response<ImageSliderOffResponse> response) {

                loadingDialog.dismissDialog();
                if (response.isSuccessful()) {
                    ImageSliderOffResponse = response.body();
                    mySliderLists = ImageSliderOffResponse.getSliderOffers();
                    adapter = new MySliderAddsAdapter(getContext(), mySliderLists, homeFragmentBinding.bannerViewPager);
                    homeFragmentBinding.bannerViewPager.setAdapter(adapter);
                    NUM_PAGES = adapter.getItemCount();
                    setupIndicator();
                    setupCurrentIndicator(0);
                    getOfferData();
                } else {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ImageSliderOffResponse> call, Throwable t) {
                loadingDialog.dismissDialog();

            }
        });
    }

    private void getOfferData() {
        ApiInterface apiService = ApiClient.getRetrofitClient().create(ApiInterface.class);
        Call<ImageSliderOffResponse> call = apiService.getOfferImage();
        call.enqueue(new Callback<ImageSliderOffResponse>() {
            @Override
            public void onResponse(Call<ImageSliderOffResponse> call, Response<ImageSliderOffResponse> response) {

                loadingDialog.dismissDialog();
                if (response.isSuccessful()) {
                    ImageSliderOffResponse = response.body();
                    mySliderLists = ImageSliderOffResponse.getSliderOffers();
                    offersAdapter = new MySliderOffersAdapter(getContext(), mySliderLists, homeFragmentBinding.bannerViewPagerOffer);
                    homeFragmentBinding.bannerViewPagerOffer.setAdapter(offersAdapter);

                } else {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ImageSliderOffResponse> call, Throwable t) {
                loadingDialog.dismissDialog();

            }
        });
    }

    private void setupIndicator() {
        ImageView[] indicator = new ImageView[adapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(4, 0, 4, 0);
        for (int i = 0; i < indicator.length; i++) {
            indicator[i] = new ImageView(getContext());
            indicator[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.indicator_inactive));
            indicator[i].setLayoutParams(layoutParams);
            homeFragmentBinding.layIndicator.addView(indicator[i]);
        }

    }

    private void setupCurrentIndicator(int index) {
        int itemcildcount = homeFragmentBinding.layIndicator.getChildCount();
        for (int i = 0; i < itemcildcount; i++) {
            ImageView imageView = (ImageView) homeFragmentBinding.layIndicator.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.indicator_inactive));
            }
        }


    }
}


