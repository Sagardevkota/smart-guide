package com.example.smarttravelguide;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class HomeFragment extends Fragment {

    private ImageView ivDestination, ivHotel,ivAdventure,ivRestaurant;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ivDestination = view.findViewById(R.id.ivDestination);
        ivHotel = view.findViewById(R.id.ivHotel);
        ivAdventure = view.findViewById(R.id.ivAdventure);
        ivRestaurant = view.findViewById(R.id.ivRestaurant);
        setOnClickListenersOnImageViews();

        return view;
    }

    private void setOnClickListenersOnImageViews() {
        ivDestination.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,new DestinationFragment())
                    .addToBackStack("DESTINATION_FRAGMENT")
                    .commit();
        });

        ivHotel.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,new HotelFragment())
                    .addToBackStack("HOTEL_FRAGMENT")
                    .commit();
        });

        ivAdventure.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,new AdventureFragment())
                    .addToBackStack("ADVENTURE_FRAGMENT")
                    .commit();
        });
        ivRestaurant.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,new RestaurantFragment())
                    .addToBackStack("ADVENTURE_FRAGMENT")
                    .commit();
        });
    }

}