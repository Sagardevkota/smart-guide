package com.example.smarttravelguide.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarttravelguide.HomeActivity;
import com.example.smarttravelguide.HomeFragment;
import com.example.smarttravelguide.R;
import com.example.smarttravelguide.api.STGAPI;
import com.example.smarttravelguide.model.Room;
import com.example.smarttravelguide.model.RoomBook;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.shawnlin.numberpicker.NumberPicker;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.MyViewHolder> {

    private static final String TAG = "ROOM_ADAPTER";
    private final Activity context;
    private final List<Room> roomList;

    public RoomAdapter(Activity context, List<Room> roomList) {
        this.context = context;
        this.roomList = roomList;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_room_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        final Room room = roomList.get(position);

        holder.tvRoomType.setText(room.getRoomType());
        holder.tvSleeps.setText("" + room.getSleepCount());
        holder.tvPrice.setText(room.getPrice());
        holder.tvTotalRooms.setText(room.getTotalRooms());
        holder.buBookNow.setOnClickListener(v -> {
            bookAdventure(room);
        });

    }


    private void bookAdventure(Room room) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("smart-travel-guide", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("id", "");
        int id = Integer.parseInt(userId);

        final Dialog dialogView = new Dialog(context,android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogView.setContentView(R.layout.layout_room_check);
        dialogView.create();
        TextView tvChooseCheckInDate = dialogView.findViewById(R.id.tvChooseCheckInDate);
        TextView tvChooseCheckOutDate = dialogView.findViewById(R.id.tvChooseCheckOutDate);
        TextView tvCheckInDate = dialogView.findViewById(R.id.tvCheckInDate);
        TextView tvCheckOutDate = dialogView.findViewById(R.id.tvCheckOutDate);
        Button buDismiss = dialogView.findViewById(R.id.buDismiss);
        buDismiss.setOnClickListener(v -> {
            dialogView.dismiss();
        });



        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        RoomBook roomBook = new RoomBook(room.getRoomId(),
                room.getRoomType(),
                1,
                "CASH ON CHECK IN",
                tvCheckInDate.getText().toString(),
                tvCheckOutDate.getText().toString(),
                id,
                room.getPrice()
        );


        tvChooseCheckInDate.setOnClickListener(v -> {
            final Calendar myCalendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(context);
            datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String date = sdf.format(myCalendar.getTime());
                tvCheckInDate.setText(date);
                roomBook.setCheckInDate(date);

            });



            if (!tvCheckOutDate.getText().toString().equalsIgnoreCase("Not Selected"))
            {
                try {
                    Date date = sdf.parse(tvCheckOutDate.getText().toString());
                    date.setTime(date.getTime() - 86400000);
                    datePickerDialog.getDatePicker().setMaxDate(date.getTime());
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() -  1000);
                }
                catch (Exception e){
                    Log.e(TAG, "bookAdventure: "+e.getMessage() );
                }

            }
                else
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });

        tvChooseCheckOutDate.setOnClickListener(v -> {
            if (tvCheckInDate.getText().toString().equalsIgnoreCase("Not Selected"))
                return;
            final Calendar myCalendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(context);


            datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String date = sdf.format(myCalendar.getTime());
                tvCheckOutDate.setText(date);
                roomBook.setCheckOutDate(date);

            });

            try {
                Date date = sdf.parse(tvCheckInDate.getText().toString());
                date.setTime(date.getTime() + 86400000);
                datePickerDialog.getDatePicker().setMinDate(date.getTime());
                datePickerDialog.show();



            } catch (ParseException e) {
                e.printStackTrace();
            }

        });

        Button buBook = dialogView.findViewById(R.id.buBook);

        buBook.setOnClickListener(v -> {
            if (tvCheckInDate.getText().toString().equalsIgnoreCase("Not Selected") ||
                    tvCheckOutDate.getText().toString().equalsIgnoreCase("Not Selected"))
                Toast.makeText(context, "Please select check in and check out dates", Toast.LENGTH_SHORT).show();
            else
            {
                dialogView.dismiss();
                checkForRoomAvailability(roomBook,room);
            }
        });

        dialogView.show();

    }

    private void checkForRoomAvailability(RoomBook roomBook,Room room) {
        showProgressDialog("Checking For Room");

        STGAPI.getApiService()
                .checkForRoom(roomBook.getRoomId(), roomBook.getCheckInDate(), roomBook.getCheckOutDate())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonResponse -> {
                    if (jsonResponse.getStatus().equalsIgnoreCase("200 OK"))
                    {
                        if (jsonResponse.getMessage().equalsIgnoreCase("All room available")){
                            createRoomAvailableDialog(Integer.parseInt(room.getTotalRooms()),room,roomBook);
                        }
                        else {
                            int roomsBooked = Integer.parseInt(jsonResponse.getMessage());
                            int roomAvailable = Integer.parseInt(room.getTotalRooms())-roomsBooked;
                            if (roomAvailable==0)
                                createRoomNotAvalableDialog();
                            else
                                createRoomAvailableDialog(roomAvailable,room,roomBook);
                        }

                    }
                },throwable -> Log.e(TAG, "checkForRoomAvailability: "+throwable.getMessage() ));



    }

    private void createRoomNotAvalableDialog() {
        hideProgressDialog();
        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(context);
        alertDialogBuilder.setMessage("Sorry!!! this room is not available for your CHECK IN and CHECK OUT dates " +
                "\n Please Select Another CHECK IN and CHECK OUT Date")
                .setPositiveButton("Ok", (dialog, which) -> dialog.dismiss()).show();
    }

    private void createRoomAvailableDialog(int roomAvailable,Room room,RoomBook roomBook) {

        hideProgressDialog();

        final Dialog dialogView = new Dialog(context,android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogView.setContentView(R.layout.layout_booking_room);
        dialogView.create();

        TextView tvCancel = dialogView.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(v -> dialogView.dismiss());


        TextView tvTotalPrice = dialogView.findViewById(R.id.tvTotal);
        TextView tvPricePerDay = dialogView.findViewById(R.id.tvPrice);
        TextView tvCheckInDate = dialogView.findViewById(R.id.tvCheckInDate);
        TextView tvCheckOutDate = dialogView.findViewById(R.id.tvCheckOutDate);
        tvCheckInDate.setText("Check In Date:         \b "+roomBook.getCheckInDate());
        tvCheckOutDate.setText("Check Out Date:        \b"+roomBook.getCheckOutDate());
        tvTotalPrice.setText("Rs. " + room.getPrice());

        TextView tvDays = dialogView.findViewById(R.id.tvDays);

        tvPricePerDay.setText("Rs. "+room.getPrice());


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date checkInDate = sdf.parse(roomBook.getCheckInDate());
            Date checkOutDate = sdf.parse(roomBook.getCheckOutDate());
            String days = dayDiff(checkInDate,checkOutDate);
            tvDays.setText(days);
        }
        catch (Exception e){
            Log.e(TAG, "createRoomAvailableDialog: "+e.getMessage() );
        }

        int day = Integer.parseInt(tvDays.getText().toString());
        int total = Integer.parseInt(room.getPrice())*day;
        tvTotalPrice.setText("Rs. "+total);

        NumberPicker numberPicker = dialogView.findViewById(R.id.num_picker);
        numberPicker.setMaxValue(roomAvailable);
        numberPicker.setMinValue(1);
        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {

            int dayDiff = Integer.parseInt(tvDays.getText().toString());

            roomBook.setRoomsBooked(newVal);
            int totalPrice = newVal * Integer.parseInt(room.getPrice()) * dayDiff;
            roomBook.setTotalPrice(String.valueOf(totalPrice));
            tvTotalPrice.setText("Rs. " + totalPrice);

        });

        Button buBook = dialogView.findViewById(R.id.buBook);

        buBook.setOnClickListener(v -> {
        createRoomAddedDialog(roomBook,dialogView);

        });


        dialogView.show();
    }

    private void createRoomAddedDialog(RoomBook roomBook,Dialog prevDialog) {
        showProgressDialog("Adding Room");

        STGAPI.getApiService()
                .bookRoom(roomBook)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonResponse -> {
                    if (jsonResponse.getMessage().equalsIgnoreCase("Room booked successfully"))
                    {
                        hideProgressDialog();

                        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(context);
                        alertDialogBuilder.setMessage("Do you want to book now?")
                                .setCancelable(true)
                                .setNegativeButton("Cancel", (dialog, which) -> {
                                    dialog.dismiss();
                                })
                                .setPositiveButton("Yes", (dialog, which) -> {


                                    prevDialog.dismiss();
                                    final Dialog dialogView = new Dialog(context,android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                                    dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialogView.setContentView(R.layout.layout_booking_successful);
                                    dialogView.create();


                                    TextView tvGoBack = dialogView.findViewById(R.id.tvGoBack);
                                    tvGoBack.setOnClickListener(v -> {
                                        dialogView.dismiss();
                                        ((HomeActivity)context).getSupportFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.fragment_container,new HomeFragment())
                                                .commit();
                                    });


                                    TextView tvDetails = dialogView.findViewById(R.id.tvDetails);
                                    tvDetails.setText("Name: " + roomBook.getPackageType() +
                                            "\n \n Number of Rooms: " + roomBook.getRoomsBooked() +
                                            "\n \n CheckInDate: " + roomBook.getCheckInDate() +
                                            "\n \n CheckoutDate: " + roomBook.getCheckOutDate() +
                                            "\n \n Payment Method: " + roomBook.getPaymentMethod() +
                                            "\n \n Total: Rs. " + roomBook.getTotalPrice());


                                    dialogView.show();

                                })
                                .show();
                    }
                    else
                        Toast.makeText(context,jsonResponse.getMessage(),Toast.LENGTH_SHORT).show();

                },throwable -> Log.e(TAG, "createRoomAddedDialog: "+throwable.getMessage() ));


    }


    @Override
    public int getItemCount() {
        return roomList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvRoomType, tvSleeps, tvTotalRooms, tvPrice;
        private final Button buBookNow;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvRoomType = itemView.findViewById(R.id.tvRoomType);
            tvSleeps = itemView.findViewById(R.id.tvSleeps);
            tvTotalRooms = itemView.findViewById(R.id.tvRooms);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            buBookNow = itemView.findViewById(R.id.bookNow);
        }
    }

    private String dayDiff(Date date1, Date date2) {
        //Comparing dates
        long difference = Math.abs(date1.getTime() - date2.getTime());
        long differenceDates = difference / (24 * 60 * 60 * 1000);

        //Convert long to String
        return Long.toString(differenceDates);
    }


    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
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
