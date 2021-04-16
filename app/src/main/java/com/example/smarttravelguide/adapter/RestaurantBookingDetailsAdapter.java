package com.example.smarttravelguide.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smarttravelguide.R;
import com.example.smarttravelguide.model.RestaurantDto;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RestaurantBookingDetailsAdapter extends RecyclerView.Adapter<RestaurantBookingDetailsAdapter.MyViewHolder> {

    private final List<RestaurantDto> restaurantDtoList;
    private final Context context;

    public RestaurantBookingDetailsAdapter(List<RestaurantDto> bookedPlaceDtoList, Context context) {
        this.restaurantDtoList = bookedPlaceDtoList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_booked_restaurant_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        final RestaurantDto restaurantDto = restaurantDtoList.get(position);
        holder.tvRestaurantName.setText(restaurantDto.getName());
        holder.tvTime.setText("Time: " + restaurantDto.getTime());
        holder.tvDate.setText("Date: " + restaurantDto.getDate());

        holder.tvPartyCount.setText("Quantity: " + restaurantDto.getPartyCount());

        Glide.with(context)
                .load(restaurantDto.getPicturePath())
                .into(holder.ivRestaurantPicture);

    }

    @Override
    public int getItemCount() {
        return restaurantDtoList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvRestaurantName, tvDate, tvPartyCount, tvTime;
        private final ImageView ivRestaurantPicture;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvRestaurantName = itemView.findViewById(R.id.tvRestaurantName);

            tvDate = itemView.findViewById(R.id.tvDate);

            tvTime = itemView.findViewById(R.id.tvTime);

            tvPartyCount = itemView.findViewById(R.id.tvPartyCount);
            ivRestaurantPicture = itemView.findViewById(R.id.ivRestaurantPicture);

        }
    }
}
