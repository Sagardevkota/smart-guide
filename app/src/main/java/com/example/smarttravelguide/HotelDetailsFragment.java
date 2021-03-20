package com.example.smarttravelguide;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smarttravelguide.model.Hotel;


public class HotelDetailsFragment extends Fragment {

    private TextView tvHotelName,tvLocation,tvDescription,tvBreakFastIncluded;
    private ImageView ivHotelImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hotel_details, container, false);

        setViews(view);
        return  view;
    }

    private void setViews(View view) {
        tvHotelName = view.findViewById(R.id.tvHotelName);
        tvLocation = view.findViewById(R.id.tvLocation);
        tvDescription = view.findViewById(R.id.tvDesc);
        ivHotelImage = view.findViewById(R.id.ivHotelPicture);
        tvBreakFastIncluded = view.findViewById(R.id.tvBreakFastIncluded);
        Bundle bundle = getArguments();
        if (bundle!=null){
            Hotel hotel = bundle.getParcelable("hotel");
            tvHotelName.setText(hotel.getHotelName());
            tvLocation.setText(hotel.getLocation());
            tvDescription.setText(hotel.getDescription());
            Glide.with(getContext())
                    .load(hotel.getPicturePath())
                    .centerCrop()
                    .into(ivHotelImage);
            if (hotel.isBreakFastIncluded())
                tvBreakFastIncluded.setVisibility(View.VISIBLE);
            
            getRoomDetails(hotel.getHotelId());
        }
    }

    private void getRoomDetails(int hotelId) {
    }
}