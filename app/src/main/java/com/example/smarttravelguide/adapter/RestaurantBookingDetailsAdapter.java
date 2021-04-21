package com.example.smarttravelguide.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smarttravelguide.R;
import com.example.smarttravelguide.api.STGAPI;
import com.example.smarttravelguide.model.RestaurantDto;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RestaurantBookingDetailsAdapter extends RecyclerView.Adapter<RestaurantBookingDetailsAdapter.MyViewHolder> {

    private final List<RestaurantDto> restaurantDtoList;
    private final Context context;
    private static final String TAG = "RESTAURANT_BOOKING_DETAILS_ADAPTER";

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

        holder.buCancel.setOnClickListener(v -> {

            new MaterialAlertDialogBuilder(context)
                    .setMessage("Do you want to cancel the room "+restaurantDto.getName())
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .setPositiveButton("Yes", (dialog, which) -> {

                        STGAPI.getApiService()
                                .cancelRestaurant(restaurantDto.getId())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(jsonResponse -> {
                                    if (jsonResponse.getStatus().equalsIgnoreCase("200 OK")){

                                        new MaterialAlertDialogBuilder(context)
                                                .setMessage(jsonResponse.getMessage())
                                                .setCancelable(false)
                                                .setPositiveButton("OK", (dialog1, which1) -> {
                                                    dialog1.dismiss();
                                                    removeItem(position);
                                                })
                                                .show();

                                    }

                                },throwable -> Log.e(TAG, "onBindViewHolder: "+throwable.getMessage() ));
                    }).show();


        });

    }

    private void removeItem(int position){
        restaurantDtoList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return restaurantDtoList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvRestaurantName, tvDate, tvPartyCount, tvTime;
        private final ImageView ivRestaurantPicture;
        private final Button buCancel;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvRestaurantName = itemView.findViewById(R.id.tvRestaurantName);

            tvDate = itemView.findViewById(R.id.tvDate);

            tvTime = itemView.findViewById(R.id.tvTime);

            tvPartyCount = itemView.findViewById(R.id.tvPartyCount);
            ivRestaurantPicture = itemView.findViewById(R.id.ivRestaurantPicture);
            buCancel = itemView.findViewById(R.id.buCancel);

        }
    }
}
