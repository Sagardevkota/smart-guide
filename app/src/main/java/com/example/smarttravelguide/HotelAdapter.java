package com.example.smarttravelguide;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smarttravelguide.model.Hotel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.MyViewHolder> {
    private final List<Hotel> hotelList;
    private final Context context;

    HotelAdapter(Context context, List<Hotel> hotelList){
        this.hotelList = hotelList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_hotel_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        final Hotel hotel = hotelList.get(position);
        holder.tvHotelName.setText(hotel.getHotelName());
        holder.tvLocation.setText(hotel.getLocation());
        holder.tvPrice.setText("NPR. "+hotel.getPrice());
        if (hotel.isBreakFastIncluded())
            holder.tvBreakFastIncluded.setVisibility(View.VISIBLE);



        Glide.with(context)
                .load(hotel.getPicturePath())
                .centerCrop()
                .into(holder.ivHotelPicture);

        Bundle bundle = new Bundle();
        bundle.putParcelable("hotel",hotel);
        HotelDetailsFragment hotelDetailsFragment = new HotelDetailsFragment();
        hotelDetailsFragment.setArguments(bundle);
        holder.cvHotelLayout.setOnClickListener(v -> {
           HomeActivity activity = (HomeActivity) context;
           activity.getSupportFragmentManager()
                   .beginTransaction()
                   .addToBackStack("HOTEL_DETAILS_FRAGMENT")
                   .replace(R.id.fragment_container,hotelDetailsFragment)
                   .commit();
        });




    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvHotelName,tvPrice,tvLocation,tvBreakFastIncluded;
        private final ImageView ivHotelPicture;
        private final CardView cvHotelLayout;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvHotelName = itemView.findViewById(R.id.tvHotelName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            ivHotelPicture = itemView.findViewById(R.id.ivHotelPicture);
            tvBreakFastIncluded = itemView.findViewById(R.id.tvBreakFastIncluded);
            cvHotelLayout = itemView.findViewById(R.id.cvHotelLayout);


        }
    }

}
