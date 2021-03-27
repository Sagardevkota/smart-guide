package com.example.smarttravelguide;

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

import com.example.smarttravelguide.api.STGAPI;
import com.example.smarttravelguide.model.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DestinationFragment extends Fragment {

    private static final String TAG = "DESTINATION_FRAGMENT";
    private PlaceAdapter placeAdapter;
    private final List<Place> placeList = new ArrayList<>();
    private RecyclerView rvPlaces;
    private TextView tvTitle;
    private ImageView ivBack;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_destination, container, false);
        rvPlaces = view.findViewById(R.id.rvPlace);
        tvTitle = view.findViewById(R.id.tvTitle);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });
        setViews();
        return view;
    }

    private void setViews() {
        placeList.clear();
        String type = getArguments().getString("type", "");
        tvTitle.setText(type.toUpperCase(Locale.ROOT));
        placeAdapter = new PlaceAdapter(getContext(), placeList);
        rvPlaces.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPlaces.setAdapter(placeAdapter);
        STGAPI.getApiService()
                .getPlaces(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(placeList1 -> {
                    placeList.addAll(placeList1);
                    placeAdapter.notifyItemRangeInserted(0, placeList1.size());
                },throwable -> Log.e(TAG, "setViews: "+throwable.getMessage() ));

    }
}