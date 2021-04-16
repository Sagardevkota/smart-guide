package com.example.smarttravelguide;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smarttravelguide.adapter.RoomAdapter;
import com.example.smarttravelguide.api.STGAPI;
import com.example.smarttravelguide.model.Hotel;
import com.example.smarttravelguide.model.Room;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class HotelDetailsFragment extends Fragment {

    private static final String TAG = "HOTEL_DETAILS_FRAGMENT";
    private TextView tvHotelName,tvLocation,tvDescription,tvBreakFastIncluded;
    private ImageView ivHotelImage;
    private RecyclerView rvRooms;
    private final List<Room> roomList = new ArrayList<>();
    private RoomAdapter roomAdapter;
    private ImageView ivBack;
    private TextView tvOpenInMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hotel_details, container, false);
        ivBack = view.findViewById(R.id.ivBack);
        tvOpenInMap = view.findViewById(R.id.tvShowInMap);




        ivBack.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

        setViews(view);
        return  view;
    }

    private void setViews(View view) {
        tvHotelName = view.findViewById(R.id.tvHotelName);
        tvLocation = view.findViewById(R.id.tvLocation);
        tvDescription = view.findViewById(R.id.tvDesc);
        ivHotelImage = view.findViewById(R.id.ivHotelPicture);
        tvBreakFastIncluded = view.findViewById(R.id.tvBreakFastIncluded);
        rvRooms = view.findViewById(R.id.rvRooms);
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

            tvOpenInMap.setOnClickListener(v -> {

                Intent intent = new Intent(getContext(),MapActivity.class);
                intent.putExtra("latitude",hotel.getLatitude());
                intent.putExtra("longitude",hotel.getLongitude());
                intent.putExtra("name",hotel.getHotelName());
                startActivity(intent);
            });
            
            getRoomDetails(hotel.getHotelId());
        }
    }

    private void getRoomDetails(int hotelId) {
        roomAdapter = new RoomAdapter(getActivity(),roomList);
        rvRooms.setAdapter(roomAdapter);
        rvRooms.setLayoutManager(new LinearLayoutManager(getContext()));


        roomList.clear();
        STGAPI.getApiService()
                .getRooms(hotelId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rooms -> {
                    roomList.addAll(rooms);
                    roomAdapter.notifyItemRangeInserted(0,rooms.size());
                },throwable -> Log.e(TAG, "getRoomDetails: "+throwable.getMessage() ));
    }
}