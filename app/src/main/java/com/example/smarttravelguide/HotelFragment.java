package com.example.smarttravelguide;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smarttravelguide.model.Hotel;

import java.util.ArrayList;
import java.util.List;


public class HotelFragment extends Fragment {



    private HotelAdapter hotelAdapter;
    private final List<Hotel> hotelList = new ArrayList<>();
    private RecyclerView rvHotel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hotel, container, false);
        rvHotel = view.findViewById(R.id.rvHotel);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        hotelList.clear();
        hotelAdapter = new HotelAdapter(getContext(),hotelList);
        rvHotel.setAdapter(hotelAdapter);
        rvHotel.setLayoutManager(new LinearLayoutManager(getContext()));
        Hotel hotel1 = new Hotel(1,"Hotel Beijing Lu","Pokhara","5231","Hotel Beijing Lu","https://cf.bstatic.com/images/hotel/max1280x900/154/154086941.jpg",false);
        Hotel hotel2 = new Hotel(2,"Mountain Inn","Pokhara","5231"," Located in Pokhara, 11 km from Fewa Lake, Sarangkot Mountain Lodge provides accommodation with a restaurant, free private parking, a bar and a garden. Featuring family rooms, this property also provides guests with a terrace. The accommodation offers a 24-hour front desk, airport transfers, room service and free WiFi throughout the property.\n" +
                "\n" +
                "Guest rooms in the hotel are equipped with a flat-screen TV. At Sarangkot Mountain Lodge each room is fitted with air conditioning and a private bathroom.\n" +
                "\n" +
                "A continental breakfast is available daily at the accommodation.\n" +
                "\n" +
                "World Peace Pagoda is 17 km from Sarangkot Mountain Lodge, while Mahendra Cave is 9 km away. The nearest airport is Pokhara Airport, 5 km from the hotel.\n" +
                "\n" +
                "We speak your language! ","https://cf.bstatic.com/images/hotel/max1280x900/238/238917174.jpg",true);
        Hotel hotel3 = new Hotel(3,"Sarangkot Mountain Lodge","Pokhara","5231","Hotel Beijing Lu","https://cf.bstatic.com/images/hotel/max1280x900/227/227569781.jpg",false);

        hotelList.add(hotel1);
        hotelAdapter.notifyItemInserted(0);
        hotelList.add(hotel2);
        hotelAdapter.notifyItemInserted(1);
        hotelList.add(hotel3);
        hotelAdapter.notifyItemInserted(2);



    }
}