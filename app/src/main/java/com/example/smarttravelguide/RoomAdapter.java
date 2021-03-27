package com.example.smarttravelguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarttravelguide.model.Room;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.MyViewHolder> {

    private final Context context;
    private final List<Room> roomList;

    RoomAdapter(Context context, List<Room> roomList){
        this.context = context;
        this.roomList = roomList;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_room_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        final Room room = roomList.get(position);
        holder.tvRoomType.setText(room.getRoomType());
        holder.tvSleeps.setText(""+room.getSleepCount());
        holder.tvPrice.setText(room.getPrice());
        holder.tvTotalRooms.setText(room.getTotalRooms());

    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

     static class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvRoomType,tvSleeps,tvTotalRooms,tvPrice;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvRoomType = itemView.findViewById(R.id.tvRoomType);
            tvSleeps = itemView.findViewById(R.id.tvSleeps);
            tvTotalRooms = itemView.findViewById(R.id.tvRooms);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
