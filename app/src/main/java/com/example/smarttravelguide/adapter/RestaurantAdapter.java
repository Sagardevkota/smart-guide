package com.example.smarttravelguide.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smarttravelguide.R;
import com.example.smarttravelguide.RestaurantDetails;
import com.example.smarttravelguide.model.Restaurant;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {

    private final List<Restaurant> restaurants;
    private final Activity context;

    public RestaurantAdapter(List<Restaurant> restaurants, Activity context){
        this.restaurants = restaurants;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_restaurant_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        final Restaurant restaurant = restaurants.get(position);
        holder.tvRestaurantName.setText(restaurant.getName());
        holder.tvlocation.setText(restaurant.getLocation());

        Glide.with(context)
                .load(restaurant.getPicturePath())
                .into(holder.ivHotelPicture);

        holder.cvRestaurant.setOnClickListener(v -> {

            RestaurantDetails restaurantDetails = new RestaurantDetails();
            Bundle bundle = new Bundle();
            bundle.putParcelable("restaurant",restaurant);
            restaurantDetails.setArguments(bundle);
            ((AppCompatActivity)context).getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("FRAGMENT_HOTEL")
                    .replace(R.id.fragment_container,restaurantDetails)
                    .commit();
        });



    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvRestaurantName,tvlocation;
        private final ImageView ivHotelPicture;
        private final CardView cvRestaurant;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvRestaurantName = itemView.findViewById(R.id.tvRestaurantName);
            tvlocation = itemView.findViewById(R.id.tvLocation);
            ivHotelPicture = itemView.findViewById(R.id.ivRestaurantPicture);
            cvRestaurant = itemView.findViewById(R.id.cvHotelLayout);

        }
    }
}
