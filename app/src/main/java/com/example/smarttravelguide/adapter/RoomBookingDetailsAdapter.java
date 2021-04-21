package com.example.smarttravelguide.adapter;

import android.content.Context;
import android.content.DialogInterface;
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
import com.example.smarttravelguide.model.BookedRoomDto;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RoomBookingDetailsAdapter extends RecyclerView.Adapter<RoomBookingDetailsAdapter.MyViewHolder> {

    private final List<BookedRoomDto> roomDetails;
    private final Context context;
    private static final String TAG = "ROOM_BOOKING_DETAILS_ADAPTER";

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

        holder.buCancel.setOnClickListener(v -> {

            new MaterialAlertDialogBuilder(context)
                    .setMessage("Do you want to cancel the room "+bookedRoomDto.getRoomType())
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .setPositiveButton("Yes", (dialog, which) -> {

                        STGAPI.getApiService()
                                .cancelRoom(bookedRoomDto.getId())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(jsonResponse -> {
                                    if (jsonResponse.getStatus().equalsIgnoreCase("200 OK")){

                                        new MaterialAlertDialogBuilder(context)
                                                .setMessage("Restaurant Canceled Successfully!!!")
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

    @Override
    public int getItemCount() {
        return roomDetails.size();
    }

    private void removeItem(int position){
        roomDetails.remove(position);
        notifyItemRemoved(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvHotelName,tvPackage,tvCheckInDate,
                tvCheckOutDate,tvTotalPrice,tvPaymentMethod,tvRoomsBooked;
        private final ImageView ivHotelPicture;
        private final Button buCancel;

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
            buCancel = itemView.findViewById(R.id.buCancel);

        }
    }
}
