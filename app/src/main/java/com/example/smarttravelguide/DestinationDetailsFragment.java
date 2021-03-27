package com.example.smarttravelguide;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.smarttravelguide.model.Place;
import com.shawnlin.numberpicker.NumberPicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DestinationDetailsFragment extends Fragment {


    private TextView tvPlaceName,tvLocation,tvDescription,tvPrice,tvTotal,tvDate;
    private ImageView ivPicturePath;
    private NumberPicker numberPicker;
    private int totalPrice = 0;
    private int price = 0;
    private ImageView ivBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_destination_details, container, false);
        ivBack = view.findViewById(R.id.ivBack);
        tvPlaceName = view.findViewById(R.id.tvPlaceName);
        tvLocation = view.findViewById(R.id.tvLocation);
        tvDescription = view.findViewById(R.id.tvDesc);
        ivPicturePath = view.findViewById(R.id.ivPlacePicture);
        tvPrice = view.findViewById(R.id.tvPrice);
        tvTotal = view.findViewById(R.id.tvTotal);
        tvDate = view.findViewById(R.id.tvDate);
        numberPicker = view.findViewById(R.id.num_picker);
        ivBack.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });

        setViews();
        return view;
    }

    private void setViews() {


        Bundle bundle = getArguments();
        if (bundle!=null) {
            Place place = bundle.getParcelable("place");
            tvPlaceName.setText(place.getPlaceName());
            tvLocation.setText(place.getLocation());
            tvPrice.setText("Rs. "+place.getExpense());
            price = Integer.parseInt(place.getExpense());
            tvTotal.setText("Rs. "+price);
            tvDescription.setText(place.getDescription());
            Glide.with(getContext())
                    .load(place.getPicturePath())
                    .centerCrop()
                    .into(ivPicturePath);


        }

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
            });

            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });




        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            totalPrice = price*newVal;
            tvTotal.setText("Rs. "+totalPrice);
        });

    }


}