package com.example.smarttravelguide;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smarttravelguide.adapter.PlaceBookingDetailsAdapter;
import com.example.smarttravelguide.adapter.RestaurantBookingDetailsAdapter;
import com.example.smarttravelguide.adapter.RoomBookingDetailsAdapter;
import com.example.smarttravelguide.api.STGAPI;
import com.example.smarttravelguide.model.BookedPlaceDto;
import com.example.smarttravelguide.model.BookedRoomDto;
import com.example.smarttravelguide.model.RestaurantDto;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MyBookingDetails extends Fragment {

    private static final String TAG = "MY_BOOKING_DETAILS_FRAGMENT";
    private TextView tvTitle ;
    private int userId;
    private final List<BookedRoomDto> bookedRoomDtoList = new ArrayList<>();
    private final List<BookedPlaceDto> bookedPlaceDtoList = new ArrayList<>();
    private final List<RestaurantDto> restaurantDtoList = new ArrayList<>();
    private RoomBookingDetailsAdapter roomBookingDetailsAdapter;
    private PlaceBookingDetailsAdapter placeBookingDetailsAdapter;
    private RestaurantBookingDetailsAdapter restaurantBookingDetailsAdapter;
    private RecyclerView rvBookingDetails;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_booking_details, container, false);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("smart-travel-guide", Context.MODE_PRIVATE);

        userId = Integer.parseInt(sharedPreferences.getString("id",""));
        hideBottomNaigationView();
        tvTitle = view.findViewById(R.id.tvTitle);
        ImageView ivBack = view.findViewById(R.id.ivBack);

        rvBookingDetails = view.findViewById(R.id.rvDetails);
        ivBack.setOnClickListener((v)->getActivity().getSupportFragmentManager().popBackStack());
        Bundle bundle = getArguments();
        String bookingType = bundle.getString("booking","");
        if (bookingType!=null)
        setUpView(bookingType);
        return view;
    }

    private void setUpView(String bookingType) {
        roomBookingDetailsAdapter = new RoomBookingDetailsAdapter(bookedRoomDtoList,getContext());
        placeBookingDetailsAdapter = new PlaceBookingDetailsAdapter(bookedPlaceDtoList,getContext());
        restaurantBookingDetailsAdapter = new RestaurantBookingDetailsAdapter(restaurantDtoList,getContext());

        rvBookingDetails.setLayoutManager(new LinearLayoutManager(getContext()));
        tvTitle.setText(bookingType);
        switch (bookingType){
            case "Room":
                rvBookingDetails.setAdapter(roomBookingDetailsAdapter);
                setRoomDetails();
                break;
            case "Destination":
                rvBookingDetails.setAdapter(placeBookingDetailsAdapter);
                setDestinationDetails();
                break;
            case "Adventure":
                rvBookingDetails.setAdapter(placeBookingDetailsAdapter);
                setAdventureDetails();
                break;
            case "Restaurant":
                rvBookingDetails.setAdapter(restaurantBookingDetailsAdapter);
                setRestaurantDetails();
                break;
        }
    }

    private void setRestaurantDetails() {

        STGAPI.getApiService()
                .getBookedRestaurants(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restaurantDtos -> {
                    restaurantDtoList.clear();
                    restaurantDtoList.addAll(restaurantDtos);
                    restaurantBookingDetailsAdapter.notifyItemRangeChanged(0,restaurantDtos.size());
                },throwable -> Log.e(TAG, "setRestaurantDetails: "+throwable.getMessage() ));

    }

    private void setAdventureDetails() {

        STGAPI.getApiService()
                .getBookedPlaces(userId,tvTitle.getText().toString().toLowerCase(Locale.ROOT))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(placeDtoList -> {
                    bookedPlaceDtoList.clear();
                    bookedPlaceDtoList.addAll(placeDtoList);
                    placeBookingDetailsAdapter.notifyItemRangeInserted(0,placeDtoList.size());
                },throwable -> Log.e(TAG, "setAdventureDetails: "+throwable.getMessage() ));

    }

    private void setDestinationDetails() {

        STGAPI.getApiService()
                .getBookedPlaces(userId,tvTitle.getText().toString().toLowerCase(Locale.ROOT))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(placeDtoList -> {
                    bookedPlaceDtoList.clear();
                    bookedPlaceDtoList.addAll(placeDtoList);
                    placeBookingDetailsAdapter.notifyItemRangeChanged(0,placeDtoList.size());

                },throwable -> Log.e(TAG, "setDestinationDetails: "+throwable.getMessage() ));

    }

    private void setRoomDetails() {

        STGAPI.getApiService()
                .getBookedRooms(userId)
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bookedRoomDtoList -> {
                    this.bookedRoomDtoList.clear();
                    this.bookedRoomDtoList.addAll(bookedRoomDtoList);
                    roomBookingDetailsAdapter.notifyItemRangeChanged(0,bookedRoomDtoList.size());

                },throwable -> Log.e(TAG, "setRoomDetails: "+throwable.getMessage() ));


    }

    private void hideBottomNaigationView(){
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.GONE);

    }
}