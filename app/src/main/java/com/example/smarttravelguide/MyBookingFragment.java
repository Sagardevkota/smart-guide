package com.example.smarttravelguide;

import android.app.AlertDialog.Builder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.TestLooperManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.KhaltiCheckOut;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.checkout.helper.PaymentPreference;
import com.khalti.utils.Constant;
import com.khalti.widget.ButtonStyle;
import com.khalti.widget.KhaltiButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MyBookingFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_booking, container, false);
        hideBottomNaigationView();
        TextView tvRoom = view.findViewById(R.id.tvRoom);
        TextView tvDestination = view.findViewById(R.id.tvDestination);
        TextView tvAdventure = view.findViewById(R.id.tvAdventure);
        TextView tvRestaurant = view.findViewById(R.id.tvRestaurant);
        KhaltiButton khaltiButton = (KhaltiButton) view.findViewById(R.id.khalti_button);
        khaltiButton.setButtonStyle(ButtonStyle.KHALTI);
        tvRoom.setOnClickListener(this);
        tvDestination.setOnClickListener(this);
        tvAdventure.setOnClickListener(this);
        tvRestaurant.setOnClickListener(this);



        return view;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvRoom:
                Toast.makeText(getContext(), "Room", Toast.LENGTH_SHORT).show();
                changeFragment("Room");
                break;
            case R.id.tvDestination:
                changeFragment("Destination");
                break;
            case R.id.tvAdventure:
                changeFragment("Adventure");
                break;
            case R.id.tvRestaurant:
                changeFragment("Restaurant");
                break;

        }
    }

    private void changeFragment(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("booking",type);
        MyBookingDetails myBookingDetails = new MyBookingDetails();
        myBookingDetails.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("MY_BOOKING_FRAGMENT")
                .replace(R.id.fragment_container,myBookingDetails)
                .commit();

    }

    private void hideBottomNaigationView(){
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);


    }
}