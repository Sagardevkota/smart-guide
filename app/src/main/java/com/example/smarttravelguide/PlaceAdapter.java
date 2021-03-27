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
import com.example.smarttravelguide.model.Place;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.MyViewHolder> {

    private final Context context;
    private final  List<Place> placeList;

    public PlaceAdapter(Context context, List<Place> placeList){
        this.context =context;
        this.placeList = placeList;
    }


    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
         return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_place_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        final Place place = placeList.get(position);
        holder.tvPlaceName.setText(place.getPlaceName());
        holder.tvLocation.setText(place.getLocation());
        holder.tvExpense.setText("Rs. "+place.getExpense());
        holder.tvTime.setText(place.getTime()+" hr");

        Glide.with(context)
                .load(place.getPicturePath())
                .centerCrop()
                .into(holder.ivPicturePath);

        Bundle bundle = new Bundle();
        bundle.putParcelable("place",place);
        DestinationDetailsFragment destinationDetailsFragment = new DestinationDetailsFragment();
        destinationDetailsFragment.setArguments(bundle);
        holder.cvLayout.setOnClickListener(v -> {
            HomeActivity activity = (HomeActivity) context;
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("PLACE_DETAILS_FRAGMENT")
                    .replace(R.id.fragment_container,destinationDetailsFragment)
                    .commit();
        });


    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvPlaceName,tvLocation,tvTime,tvExpense;
        private final ImageView ivPicturePath;
        private CardView cvLayout;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvPlaceName = itemView.findViewById(R.id.tvPlaceName);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvExpense = itemView.findViewById(R.id.tvPrice);
            ivPicturePath = itemView.findViewById(R.id.ivPlacePicture);
            cvLayout = itemView.findViewById(R.id.cvLayout);

        }
    }
}
