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

import com.example.smarttravelguide.adapter.RestaurantAdapter;
import com.example.smarttravelguide.api.STGAPI;
import com.example.smarttravelguide.model.Restaurant;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class RestaurantFragment extends Fragment {

    private static final String TAG = "RESTAURANT_FRAGMENT";
    private RecyclerView rvRestaurant;

    private RestaurantAdapter restaurantAdapter;
    private final List<Restaurant> restaurantList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        hideBottomNaigationView();
        ImageView ivBack = view.findViewById(R.id.ivBack);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        rvRestaurant = view.findViewById(R.id.rvRestaurant);
        tvTitle.setText(getArguments().getString("type").toUpperCase(Locale.ROOT));
        ivBack.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });

        initViews();
        return view;
    }

    private void initViews() {
        restaurantAdapter = new RestaurantAdapter(restaurantList,getActivity());
        rvRestaurant.setAdapter(restaurantAdapter);
        rvRestaurant.setLayoutManager(new LinearLayoutManager(getContext()));

        STGAPI.getApiService()
                .getRestaurants()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restaurants -> {
                    restaurantList.clear();
                    restaurantList.addAll(restaurants);
                    restaurantAdapter.notifyItemRangeChanged(0,restaurants.size());
                },throwable -> Log.e(TAG, "initViews: "+throwable.getMessage() ));
    }

    private void hideBottomNaigationView(){
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.GONE);

    }
}