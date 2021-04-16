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
import com.example.smarttravelguide.model.BookedRoomDto;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RoomBookingDetailsAdapter extends RecyclerView.Adapter<RoomBookingDetailsAdapter.MyViewHolder> {

    private final List<BookedRoomDto> roomDetails;
    private final Context context;

    public RoomBookingDetailsAdapter(List<BookedRoomDto> roomDetails, Context context){
        this.roomDetails = roomDetails;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_booking_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        final BookedRoomDto bookedRoomDto = roomDetails.get(position);
        holder.tvHotelName.setText(bookedRoomDto.getHotelName());
        holder.tvPackage.setText("Room Type: "+bookedRoomDto.getRoomType());
        holder.tvRoomsBooked.setText("Rooms Booked: "+bookedRoomDto.getRoomsBooked());
        holder.tvCheckInDate.setText("Check In Date: "+bookedRoomDto.getCheckInDate());
        holder.tvCheckOutDate.setText("Check Out Date: "+bookedRoomDto.getCheckOutDate());
        holder.tvTotalPrice.setText("Total Price: Rs. "+bookedRoomDto.getTotalPrice());
        holder.tvPaymentMethod.setText("Payment Method: "+bookedRoomDto.getPaymentMethod());

        Glide.with(context)
                .load(bookedRoomDto.getPicturePath())
                .into(holder.ivHotelPicture);

    }

    @Override
    public int getItemCount() {
        return roomDetails.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvHotelName,tvPackage,tvCheckInDate,
                tvCheckOutDate,tvTotalPrice,tvPaymentMethod,tvRoomsBooked;
        private final ImageView ivHotelPicture;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvHotelName = itemView.findViewById(R.id.tvHotelName);
            tvPackage = itemView.findViewById(R.id.tvPackageType);
            tvCheckInDate = itemView.findViewById(R.id.tvCheckInDate);
            tvCheckOutDate = itemView.findViewById(R.id.tvCheckOutDate);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
            tvPaymentMethod = itemView.findViewById(R.id.tvPaymentMethod);
            tvRoomsBooked = itemView.findViewById(R.id.tvRoomsBooked);
            ivHotelPicture = itemView.findViewById(R.id.ivHotelPicture);

        }
    }
}
