package com.example.discaount.views;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.discaount.R;
import com.example.discaount.adapters.DrugListAdapter;
import com.example.discaount.adapters.ShopListAdapter;
import com.example.discaount.data.model.Drug;
import com.example.discaount.data.model.Product;
import com.example.discaount.data.model.ProductResponse;
import com.example.discaount.data.model.SearchRespons;
import com.example.discaount.data.network.ApiClient;
import com.example.discaount.data.network.ApiInterface;
import com.example.discaount.databinding.FragmentProductBinding;
import com.example.discaount.databinding.FragmentSearchBinding;
import com.example.discaount.utils.AlertAdded;
import com.example.discaount.viewmodels.DrugViewModel;
import com.example.discaount.viewmodels.SearchDrugViewModel;
import com.example.discaount.viewmodels.SearchShopViewModel;
import com.example.discaount.viewmodels.ShopViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements ShopListAdapter.ShopInterface, DrugListAdapter.DrugInterface {
    FragmentSearchBinding fragmentSearchBinding;
    SearchDrugViewModel mViewModel;
    SearchShopViewModel shopViewModel;
    private ShopViewModel viewModel;
    private DrugViewModel drugViewModel;
    DrugListAdapter drugListAdapter;
    ShopListAdapter shopListAdapter;
    private NavController navController;
    String name;
    int witch = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false);

        fragmentSearchBinding.progressBar.setVisibility(View.VISIBLE);
        return fragmentSearchBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // loadingDialog.startLoadingDialog();
        drugListAdapter = new DrugListAdapter(this);
        shopListAdapter = new ShopListAdapter(this);
        fragmentSearchBinding.searchDrugRecyclerView.setAdapter(drugListAdapter);
        fragmentSearchBinding.searchRecyclerView.setAdapter(shopListAdapter);
        mViewModel = new ViewModelProvider(requireActivity()).get(SearchDrugViewModel.class);
        shopViewModel = new ViewModelProvider(requireActivity()).get(SearchShopViewModel.class);
        drugViewModel = new ViewModelProvider(requireActivity()).get(DrugViewModel.class);
        viewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);
        navController = Navigation.findNavController(view);
        //((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        fragmentSearchBinding.radioSearch.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioLanguageButton = getActivity().findViewById(checkedId);
                if (radioLanguageButton != null && checkedId > -1) {

                    if (radioLanguageButton.getText().equals(getActivity().getResources().getString(R.string.product))) {
                        shopListAdapter.notifyDataSetChanged();
                        drugListAdapter.notifyDataSetChanged();
                        fragmentSearchBinding.searchRecyclerView.setVisibility(View.VISIBLE);
                        fragmentSearchBinding.searchDrugRecyclerView.setVisibility(View.GONE);
                        witch = 0;
                        search(witch, name);
                    } else {
                        shopListAdapter.notifyDataSetChanged();
                        drugListAdapter.notifyDataSetChanged();
                        fragmentSearchBinding.searchRecyclerView.setVisibility(View.GONE);
                        fragmentSearchBinding.searchDrugRecyclerView.setVisibility(View.VISIBLE);
                        witch = 1;
                        search(witch, name);
                    }
                }
            }
        });


        fragmentSearchBinding.edtIvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                search(witch, name);
                shopListAdapter.notifyDataSetChanged();
                drugListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                shopListAdapter.notifyDataSetChanged();
                name = fragmentSearchBinding.edtIvSearch.getText().toString();
                search(witch, name);
            }

            @Override
            public void afterTextChanged(Editable s) {
                name = fragmentSearchBinding.edtIvSearch.getText().toString();
                if (name.isEmpty()) {
                    shopListAdapter.notifyDataSetChanged();
                }
                search(witch, name);

                return;
            }
        });

        fragmentSearchBinding.searchRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));

        fragmentSearchBinding.progressBar.setVisibility(View.GONE);

    }


    private void search(int i, String name) {
        shopListAdapter.notifyDataSetChanged();
        drugListAdapter.notifyDataSetChanged();
        if (i == 0) {

            // Toast.makeText(getContext(), i + "", Toast.LENGTH_SHORT).show();
            shopViewModel.getProducts(name).observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
                @Override
                public void onChanged(List<Product> products) {
                    fragmentSearchBinding.progressBar.setVisibility(View.GONE);
                    shopListAdapter.submitList(products);
                    drugListAdapter.notifyDataSetChanged();
                }
            });
        } else {

            //   Toast.makeText(getContext(), i + "", Toast.LENGTH_SHORT).show();

            mViewModel.getDrugs(name).observe(getViewLifecycleOwner(), new Observer<List<Drug>>() {
                @Override
                public void onChanged(List<Drug> drugs) {
                    fragmentSearchBinding.progressBar.setVisibility(View.GONE);
                    drugListAdapter.submitList(drugs);
                    drugListAdapter.notifyDataSetChanged();
                }
            });
        }
    }


    @Override
    public void addItem(Product product) {
        boolean isAdded = viewModel.addItemToCart(product, 1);
        if (isAdded) {
            AlertAdded alertAdded = new AlertAdded(getActivity());
            alertAdded.showSuccessDialogAdd(product.getName());
        }
    }

    @Override
    public void onItemClick(Product product) {
        viewModel.setProduct(product);
        navController.navigate(R.id.action_searchFragment_to_productDetailFragment);
    }
    @Override
    public void addItem(Drug drug) {
        boolean isAdded = drugViewModel.addItemToCart(drug, 1);
        if (isAdded) {
            AlertAdded alertAdded = new AlertAdded(getActivity());
            alertAdded.showSuccessDialogAdd(drug.getDrugs_name());
        }
    }
    @Override
    public void onItemClick(Drug drug) {
        drugViewModel.setDrug(drug);
        navController.navigate(R.id.action_searchFragment_to_drugDetailsFragment);
    }
}