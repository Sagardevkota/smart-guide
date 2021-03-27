package com.example.smarttravelguide;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;


public class HomeFragment extends Fragment {

    private ImageView ivDestination, ivHotel,ivAdventure,ivRestaurant;
    private BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        ivDestination = view.findViewById(R.id.ivDestination);
        ivHotel = view.findViewById(R.id.ivHotel);
        ivAdventure = view.findViewById(R.id.ivAdventure);
        ivRestaurant = view.findViewById(R.id.ivRestaurant);
        setOnClickListenersOnImageViews();

        return view;
    }

    private void setOnClickListenersOnImageViews() {
        ivDestination.setOnClickListener(v -> {
            hideBottomNaigationView();
            Bundle bundle = new Bundle();
            DestinationFragment destinationFragment = new DestinationFragment();
            bundle.putString("type","destination");
            destinationFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,destinationFragment)
                    .addToBackStack("DESTINATION_FRAGMENT")
                    .commit();
        });

        ivHotel.setOnClickListener(v -> {
            hideBottomNaigationView();
            Bundle bundle = new Bundle();
            HotelFragment hotelFragment = new HotelFragment();
            bundle.putString("type","hotel");
            hotelFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,hotelFragment)
                    .addToBackStack("HOTEL_FRAGMENT")
                    .commit();
        });

        ivAdventure.setOnClickListener(v -> {
            hideBottomNaigationView();
            Bundle bundle = new Bundle();
            DestinationFragment destinationFragment = new DestinationFragment();
            bundle.putString("type","adventure");
            destinationFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,destinationFragment)
                    .addToBackStack("ADVENTURE_FRAGMENT")
                    .commit();
        });
        ivRestaurant.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            RestaurantFragment restaurantFragment = new RestaurantFragment();
            bundle.putString("type","restaurant");
            restaurantFragment.setArguments(bundle);
            hideBottomNaigationView();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,restaurantFragment)
                    .addToBackStack("ADVENTURE_FRAGMENT")
                    .commit();
        });
    }

    private void hideBottomNaigationView(){
        bottomNavigationView.setVisibility(View.GONE);
         ((AppCompatActivity) getActivity()).getSupportActionBar().setShowHideAnimationEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

    }


    }

