package com.example.discaount.views.activity;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.discaount.R;
import com.example.discaount.adapters.DrawerItemCustomAdapter;
import com.example.discaount.data.model.CartItem;
import com.example.discaount.data.model.CartItemDrug;
import com.example.discaount.medicine.MedicineActivity;
import com.example.discaount.utils.BaseActivity;
import com.example.discaount.viewmodels.DrugViewModel;
import com.example.discaount.viewmodels.ShopViewModel;
import com.example.discaount.views.HomeFragment;
import com.example.discaount.views.OfferFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import static com.example.discaount.utils.SharedPref.F_NAME;
import static com.example.discaount.utils.SharedPref.IMAGES;
import static com.example.discaount.utils.SharedPref.SHARED_PREF_NAME;
import static com.example.discaount.utils.SharedPref.TOKEN;
import static com.example.discaount.utils.SharedPref.mCtx;

public class MainActivity extends BaseActivity {
    NavController navController;
    DrawerItemCustomAdapter drawerItemCustomAdapter;
    ShopViewModel shopViewModel;
    DrugViewModel drugViewModel;
    private int cartQuantity = 0;
    private int cartDrugQuantity = 0;
    RelativeLayout header;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        View viewm = navigationView.getHeaderView(0);
        TextView t1 = viewm.findViewById(R.id.nameOfCu);
        ImageView img = viewm.findViewById(R.id.imageViewProfile);
        header = viewm.findViewById(R.id.header);
        sharedPref = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String fullName = sharedPref.getString(F_NAME, "name");
        final String image = sharedPref.getString(IMAGES, "image");
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        final String s = sharedPref.getString(TOKEN, "token");
        Log.d("TOKEN", "\n" + s);
        if (!sharedPref.equals("image")) {
            img.setImageURI(Uri.parse(image));
        }
        if (fullName.equals("name")) {
            header.setVisibility(View.GONE);
        } else {
            t1.setText("" + fullName);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                Fragment fragment;
                switch (id) {
                    case R.id.search:
                        try {
                            //
                            navController.navigate(R.id.homeFragment);
                            drawerLayout.closeDrawer(GravityCompat.START);
                        } catch (Exception e) {
                        }
                        break;
                    case R.id.basket:
                        Intent med = new Intent(MainActivity.this, MedicineActivity.class);
                        startActivity(med);
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;
                    case R.id.promo_code:
                        Intent intent1 = new Intent(MainActivity.this, TermDiscount.class);
                        startActivity(intent1);
                        break;
                    case R.id.orders:
                        Intent in = new Intent(MainActivity.this, PrivacyDiscount.class);
                        startActivity(in);
                        break;

                    case R.id.setting:
                        Intent intent = new Intent(MainActivity.this, Setting.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.support:
                        Intent intent21 = new Intent(MainActivity.this, ContactUs.class);
                        startActivity(intent21);
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });

        // NavigationUI.setupActionBarWithNavController(this, navController);
        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);
        shopViewModel.getCart().observe(this, new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                int quantity = 0;
                for (CartItem cartItem : cartItems) {
                    quantity += cartItem.getQuantity();
                }
                cartQuantity = quantity;
                invalidateOptionsMenu();
            }
        });
        drugViewModel = new ViewModelProvider(this).get(DrugViewModel.class);
        drugViewModel.getCart().observe(this, new Observer<List<CartItemDrug>>() {
            @Override
            public void onChanged(List<CartItemDrug> cartItemDrugs) {
                int quantity = 0;
                for (CartItemDrug cartItem : cartItemDrugs) {
                    quantity += cartItem.getQuantity();
                }
                cartDrugQuantity = quantity;
                invalidateOptionsMenu();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragMgr = getSupportFragmentManager();
        FragmentTransaction fragTrans = fragMgr.beginTransaction();
        fragTrans.replace(R.id.nav_host_fragment, fragment);
        fragTrans.commit();
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.cartFragment);
        final MenuItem menuItem1 = menu.findItem(R.id.searchFragment);

        View actionView = menuItem.getActionView();
        View actionView2 = menuItem1.getActionView();
        TextView cartBadgeTextView = actionView.findViewById(R.id.cart_badge_text_view);
        cartBadgeTextView.setText(String.valueOf(cartDrugQuantity + cartQuantity));

        if (cartQuantity == 0) {
            cartBadgeTextView.setVisibility(View.GONE);
            //cartBadgeTextView.setVisibility(cartDrugQuantity == 0 ? View.GONE : View.VISIBLE);
        }
        cartBadgeTextView.setVisibility(View.VISIBLE);

        if (cartDrugQuantity == 0) {
            //cartBadgeTextView.setVisibility(cartQuantity == 0 ? View.GONE : View.VISIBLE);
            cartBadgeTextView.setVisibility(View.GONE);
        }
        cartBadgeTextView.setVisibility(View.VISIBLE);


        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        actionView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem1);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        } else {
            super.onBackPressed();
        }
    }
}