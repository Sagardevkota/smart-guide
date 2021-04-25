package com.example.smarttravelguide;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.smarttravelguide.api.STGAPI;
import com.example.smarttravelguide.model.Restaurant;
import com.example.smarttravelguide.model.RestaurantBook;
import com.example.smarttravelguide.model.RoomBook;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.KhaltiCheckOut;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.checkout.helper.PaymentPreference;
import com.khalti.widget.KhaltiButton;
import com.shawnlin.numberpicker.NumberPicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RestaurantDetails extends Fragment {

    private static final String TAG = "RESTAURANT_DETAILS";
    private ImageView ivRestaurantPicture;
    private TextView tvDetails, tvDesc, tvLocation, tvChooseTime, tvChooseDate,tvOpenInMap;
    private NumberPicker numberPicker;
    private Button buFindATable;
    private int partyCount = 1;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_restaurant_details, container, false);
        sharedPreferences = getContext().getSharedPreferences("smart-travel-guide", Context.MODE_PRIVATE);

        ImageView ivBack = view.findViewById(R.id.ivBack);
        tvDetails = view.findViewById(R.id.tvTitle);
        tvDesc = view.findViewById(R.id.tvDescription);
        ivRestaurantPicture = view.findViewById(R.id.ivRestaurantPicture);
        tvLocation = view.findViewById(R.id.tvLocation);
        numberPicker = view.findViewById(R.id.num_picker);
        tvChooseTime = view.findViewById(R.id.tvChooseTime);
        tvChooseDate = view.findViewById(R.id.tvChooseDate);
        buFindATable = view.findViewById(R.id.buFindATable);
        tvOpenInMap = view.findViewById(R.id.tvShowInMap);
        ivBack.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

        Restaurant restaurant = getArguments().getParcelable("restaurant");
        if (restaurant != null)
            initViews(restaurant);

        return view;
    }

    private void initViews(Restaurant restaurant) {
        tvDetails.setText(restaurant.getName());


        tvOpenInMap.setOnClickListener(v -> {

            Intent intent = new Intent(getContext(),MapActivity.class);
            intent.putExtra("latitude",restaurant.getLatitude());
            intent.putExtra("longitude",restaurant.getLongitude());
            intent.putExtra("name",restaurant.getName());
            startActivity(intent);
        });
        Glide.with(getContext())
                .load(restaurant.getPicturePath())
                .into(ivRestaurantPicture);
        tvDesc.setText(restaurant.getDescription());
        tvLocation.setText(restaurant.getLocation());
        numberPicker.setMaxValue(restaurant.getTotalPartySize());
        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            partyCount = newVal;
        });
        tvChooseDate.setOnClickListener(v -> {

            final Calendar myCalendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String date = sdf.format(myCalendar.getTime());
                tvChooseDate.setText(date);

            });


            Date date = new Date();
            date.setTime(date.getTime());
            datePickerDialog.getDatePicker().setMinDate(date.getTime());
            datePickerDialog.show();


        });


        tvChooseTime.setOnClickListener(v -> {

            if (tvChooseDate.getText().toString().equalsIgnoreCase("Choose Date"))
                return;
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


            final Calendar calendar = Calendar.getInstance();


            final int hour = calendar.get(Calendar.HOUR_OF_DAY);
            final int minute = calendar.get(Calendar.MINUTE);
            RangeTimePickerDialog timePickerDialog = new RangeTimePickerDialog(getContext(),
                    (view, hourOfDay, minute1) ->{
                        tvChooseTime.setText(" "+hourOfDay+ minute1);

                    }
                    ,
                    hour + 1, minute, false);

            try {
                Date date = sdf.parse(tvChooseDate.getText().toString());
                //if there is no time before today
                if (!calendar.getTime().before(date))
                    timePickerDialog.setMin(hour + 1, minute);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            timePickerDialog.show();

        });

        buFindATable.setOnClickListener(v -> {
            if (tvChooseDate.getText().toString().equalsIgnoreCase("Choose Date") ||
                    tvChooseTime.getText().toString().equalsIgnoreCase("Choose Time")) {
                Toast.makeText(getContext(), "Please select date and time", Toast.LENGTH_SHORT).show();
                return;
            } else {
                String date = tvChooseDate.getText().toString();
                String time = tvChooseTime.getText().toString();
                String userId = sharedPreferences.getString("id", "");
                int id = Integer.parseInt(userId);



                STGAPI.getApiService()
                        .checkForTable(restaurant.getId(), date, time)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(jsonResponse -> {
                            if (jsonResponse.getMessage().equalsIgnoreCase("All tables available"))
                            {
                                        RestaurantBook restaurantBook =
                                                new RestaurantBook(restaurant.getId(),partyCount,date,time,id);
                                showDialog(restaurantBook);
                                return;
                            }

                            int bookedPartyCount = Integer.parseInt(jsonResponse.getMessage());
                            if (restaurant.getTotalPartySize()-bookedPartyCount>partyCount){
                                RestaurantBook restaurantBook =
                                        new RestaurantBook(restaurant.getId(),partyCount,date,time,id);

                                showDialog(restaurantBook);

                            } else {
                                Toast.makeText(getContext(),"Sorry no room is available for "+partyCount+" people", Toast.LENGTH_SHORT).show();
                            }
                        }, throwable -> Log.e(TAG, "initViews: " + throwable.getMessage()));

            }
        });


    }

    private void showDialog(RestaurantBook restaurantBook) {

        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(getContext());
        alertDialogBuilder.setMessage("Room is available for "+restaurantBook.getPartyCount()+ " people");
        alertDialogBuilder.setPositiveButton("Book Now", (dialog, which) -> {

            STGAPI.getApiService()
                    .bookRestaurant(restaurantBook)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(jsonResponse -> {
                        if (jsonResponse.getMessage().equalsIgnoreCase("Booked Restaurant")){
                            dialog.dismiss();
                            MaterialAlertDialogBuilder alertDialogBuilder1 = new MaterialAlertDialogBuilder(getContext());
                            alertDialogBuilder1.setMessage(jsonResponse.getMessage())
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", (dialog1, which1) ->{
                                        dialog1.dismiss();
                                        getActivity().getSupportFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.fragment_container,new HomeFragment())
                                                .commit();
                                    })
                                    .show();
                        }
                            },
                            throwable -> Log.e(TAG, "showDialog: "+throwable.getMessage() ));

        })
                .setNegativeButton("Cancel", (dialog, which) -> {

                })
                .setTitle("Room Availability")
                .show();

    }




}