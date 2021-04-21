package com.example.smarttravelguide;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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

import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.smarttravelguide.api.STGAPI;
import com.example.smarttravelguide.model.DestinationBook;
import com.example.smarttravelguide.model.Place;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.shawnlin.numberpicker.NumberPicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DestinationDetailsFragment extends Fragment {


    private static final String TAG = "DESTINATION_DETAILS_FRAGMENT";
    private TextView tvPlaceName,tvLocation,tvDescription,tvPrice,tvTotal,tvDate;
    private ImageView ivPicturePath;
    private NumberPicker numberPicker;
    private int totalPrice = 0;
    private int price = 0;
    private ImageView ivBack;
    private Button buBook;
    private DestinationBook destinationBook;
    private SharedPreferences sharedPreferences;
    private TextView tvOpenInMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_destination_details, container, false);
        sharedPreferences = getContext().getSharedPreferences("smart-travel-guide", Context.MODE_PRIVATE);
        ivBack = view.findViewById(R.id.ivBack);
        tvPlaceName = view.findViewById(R.id.tvPlaceName);
        tvLocation = view.findViewById(R.id.tvLocation);
        tvDescription = view.findViewById(R.id.tvDesc);
        ivPicturePath = view.findViewById(R.id.ivPlacePicture);
        tvPrice = view.findViewById(R.id.tvPrice);
        tvTotal = view.findViewById(R.id.tvTotal);
        tvDate = view.findViewById(R.id.tvDate);
        numberPicker = view.findViewById(R.id.num_picker);
        buBook = view.findViewById(R.id.buBook);
         tvOpenInMap = view.findViewById(R.id.tvShowInMap);
        ivBack.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });

        buBook.setOnClickListener((v)->bookAdventure());

        setViews();
        return view;
    }

    private void bookAdventure() {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getContext());
        materialAlertDialogBuilder.setMessage("Are you sure you want to book "+tvPlaceName.getText()+"?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    showProgressDialog("Booking ");
                    STGAPI.getApiService().bookDestination(destinationBook)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(jsonResponse -> {
                                if (jsonResponse.getStatus().equalsIgnoreCase("200 Ok"))
                                    createDestinationAddedDialog();
                            },throwable -> {Log.e(TAG, "bookAdventure: "+throwable.getMessage() );
                            hideProgressDialog();
                            });

                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();


    }

    private void createDestinationAddedDialog() {
        hideProgressDialog();
        final Dialog dialogView = new Dialog(getContext(),android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogView.setContentView(R.layout.layout_booking_successful);
        dialogView.create();
        TextView tvDetails = dialogView.findViewById(R.id.tvDetails);
        TextView tvGoBack = dialogView.findViewById(R.id.tvGoBack);
        tvGoBack.setOnClickListener(v -> {
            dialogView.dismiss();
            getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,new HomeFragment())
                .commit();
        });
        tvDetails.setText("Name: "+tvPlaceName.getText()+
                "\n Quantity: "+destinationBook.getQuantity()+
                "\n Package Type: "+destinationBook.getPackageType()+
                "\n Time:"+destinationBook.getDate()+
                "\n Price: "+destinationBook.getPrice());

        dialogView.setCancelable(true);
        dialogView.show();




    }

    private void setViews() {


        Bundle bundle = getArguments();
        if (bundle!=null) {
            Place place = bundle.getParcelable("place");
            tvPlaceName.setText(place.getPlaceName());
            tvLocation.setText(place.getLocation());
            tvPrice.setText("Rs. " + place.getExpense());
            price = Integer.parseInt(place.getExpense());
            tvTotal.setText("Rs. " + price);
            tvDescription.setText(place.getDescription());
            Glide.with(getContext())
                    .load(place.getPicturePath())
                    .centerCrop()
                    .into(ivPicturePath);


            tvOpenInMap.setOnClickListener(v -> {

                Intent intent = new Intent(getContext(),MapActivity.class);
                intent.putExtra("latitude",place.getLatitude());
                intent.putExtra("longitude",place.getLongitude());
                intent.putExtra("name",place.getPlaceName());
                startActivity(intent);
            });


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            tvDate.setText(sdf.format(new Date()));

            tvDate.setOnClickListener(v -> {
                final Calendar myCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
                datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, month);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    tvDate.setText(sdf.format(myCalendar.getTime()));
                    destinationBook.setDate(tvDate.getText().toString());

                });

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            });

            destinationBook = new DestinationBook(place.getPlaceId(),
                    place.getType(),
                    1,tvDate.getText().toString(),
                    place.getExpense(),
                    Integer.parseInt(sharedPreferences.getString("id","")));


            numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
                totalPrice = price * newVal;
                tvTotal.setText("Rs. " + totalPrice);
                //change quantity here
                destinationBook.setQuantity(newVal);
                //change new price
                destinationBook.setPrice(String.valueOf(totalPrice));
            });

        }

    }

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage(msg);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


}