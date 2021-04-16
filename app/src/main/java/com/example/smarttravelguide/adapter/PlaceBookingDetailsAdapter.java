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
import com.example.smarttravelguide.model.BookedPlaceDto;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlaceBookingDetailsAdapter extends RecyclerView.Adapter<PlaceBookingDetailsAdapter.MyViewHolder> {

    private final List<BookedPlaceDto> roomDetails;
    private final Context context;

    public PlaceBookingDetailsAdapter(List<BookedPlaceDto> bookedPlaceDtoList, Context context){
        this.roomDetails = bookedPlaceDtoList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_booked_place_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        final BookedPlaceDto bookedRoomDto = roomDetails.get(position);
        holder.tvPlaceName.setText(bookedRoomDto.getPlaceName());
        holder.tvPackage.setText("Package Type: "+bookedRoomDto.getPackageType());
        holder.tvDate.setText("Date: "+bookedRoomDto.getDate());
        holder.tvTotalPrice.setText("Total Price: Rs. "+bookedRoomDto.getExpense());
        holder.tvQuantity.setText("Quantity: "+bookedRoomDto.getQuantity());

        Glide.with(context)
                .load(bookedRoomDto.getPicturePath())
                .into(holder.ivPlacePicture);

    }

    @Override
    public int getItemCount() {
        return roomDetails.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvPlaceName,tvPackage,tvDate,
                tvTotalPrice,tvQuantity;
        private final ImageView ivPlacePicture;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tvPlaceName = itemView.findViewById(R.id.tvPlaceName);
            tvPackage = itemView.findViewById(R.id.tvPackageType);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            ivPlacePicture = itemView.findViewById(R.id.ivPlacePicture);

        }
    }
}
