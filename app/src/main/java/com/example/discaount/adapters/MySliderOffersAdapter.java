package com.example.discaount.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.discaount.R;
import com.example.discaount.data.model.ImageSliderOffer;

import java.util.List;

public class MySliderOffersAdapter extends RecyclerView.Adapter<MySliderOffersAdapter.ViewHolder> {
    private List<ImageSliderOffer> mySliderLists;
    private LayoutInflater mInflater;
    private ViewPager2 viewPager;
    Context context;
    private NavController navController;

    public MySliderOffersAdapter(Context context, List<ImageSliderOffer> mySliderLists, ViewPager2 viewPager) {
        this.mInflater = LayoutInflater.from(context);
        this.mySliderLists = mySliderLists;
        this.viewPager = viewPager;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.slider_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ImageSliderOffer ob = mySliderLists.get(position);
        Glide.with(context).load("https://shayoub.com/discount/public/icon/Adds/" + ob.getPath()).into(holder.banner_slider);
        holder.banner_slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_homeFragment_to_offerFragment);
            }
        });
//

    }

    @Override
    public int getItemCount() {
        return mySliderLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView banner_slider;

        //RelativeLayout relativeLayout;
        // Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            banner_slider = itemView.findViewById(R.id.banner_slider);
            // relativeLayout = itemView.findViewById(R.id.container);

        }
    }
}
