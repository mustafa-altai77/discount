package com.example.discaount.data.model;
import android.annotation.SuppressLint;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;
import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ProductResponse  {
    @SerializedName("data")
   List<Product> products;
    private Message message;
    private Boolean success;
    public List<Product> getProductas(){
        return products;
    }
    public static DiffUtil.ItemCallback<ProductResponse> itemCallback = new DiffUtil.ItemCallback<ProductResponse>() {
        @Override
        public boolean areItemsTheSame(@NonNull ProductResponse oldItem, @NonNull ProductResponse newItem) {
            return oldItem.getProductas().equals(newItem.getProductas());
        }
        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull ProductResponse oldItem, @NonNull ProductResponse newItem) {
            return oldItem.getProductas().equals(newItem);
        }
    };
    @BindingAdapter("android:categoryImage")
    public static void loadImage(ImageView imageView, String imageUrl){
        Glide.with(imageView).load("http://primarykeysd.com/PharmacyApp/VoyagerTesting/public/storage/"+imageUrl)
                .fitCenter()
                .into(imageView);
    }
}
