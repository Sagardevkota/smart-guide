package com.example.smarttravelguide;

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

import com.example.smarttravelguide.api.STGAPI;
import com.example.smarttravelguide.model.Hotel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class HotelFragment extends Fragment {


    private static final String TAG = "HOTEL_FRAGMENT";
    private HotelAdapter hotelAdapter;
    private final List<Hotel> hotelList = new ArrayList<>();
    private RecyclerView rvHotel;
    private ImageView ivBack;
    private TextView tvTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hotel, container, false);
        rvHotel = view.findViewById(R.id.rvHotel);
        ivBack = view.findViewById(R.id.ivBack);
        tvTitle = view.findViewById(R.id.tvTitle);
        String type = getArguments().getString("type", "");
        tvTitle.setText(type.toUpperCase(Locale.ROOT));
        ivBack.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        hotelList.clear();
        hotelAdapter = new HotelAdapter(getContext(),hotelList);
        rvHotel.setAdapter(hotelAdapter);
        rvHotel.setLayoutManager(new LinearLayoutManager(getContext()));
        STGAPI.getApiService()
                .getHotels()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(hotels -> {
                    hotelList.addAll(hotels);
                    hotelAdapter.notifyItemRangeInserted(0,hotels.size());
                },throwable -> Log.e(TAG, "initRecyclerView: "+throwable.getMessage() ));



    }
}