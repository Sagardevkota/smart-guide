package com.example.smarttravelguide;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.smarttravelguide.api.STGAPI;
import com.example.smarttravelguide.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MyAccountFragment extends Fragment {

    private static final String TAG = "MY_ACTIVITY_FRAGMENT";
    private TextView tvFullName,tvEmail,tvUserName,tvGender,tvPhone;
    private SharedPreferences sharedPreferences;
    private Button buEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        sharedPreferences = getContext().getSharedPreferences("smart-travel-guide", Context.MODE_PRIVATE);
        hideBottomNaigationView();
        tvFullName = view.findViewById(R.id.tvFullName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvGender = view.findViewById(R.id.tvGender);
        tvPhone = view.findViewById(R.id.tvPhone);
        buEdit = view.findViewById(R.id.buEdit);
        setViews();
        return view;
    }

    private void setViews() {

        int userId = Integer.parseInt(sharedPreferences.getString("id",""));
        STGAPI.getApiService()
                .getUserInfo(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    tvFullName.setText(user.getFullName());
                    tvEmail.setText(user.getEmail());
                    tvUserName.setText(user.getUserName());
                    tvGender.setText(user.getGender());
                    tvPhone.setText(user.getPhone());
                            buEdit.setOnClickListener(v -> {
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("user",user);
                                MyAccountEditFragment myAccountEditFragment = new MyAccountEditFragment();
                                myAccountEditFragment.setArguments(bundle);
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container,myAccountEditFragment)
                                        .addToBackStack("MY_ACCOUNT_EDIT_FRAGMENT")
                                        .commit();
                            });
                        },
                        throwable -> Log.e(TAG, "setViews: "+throwable.getMessage() ));





    }

    private void hideBottomNaigationView(){
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);

    }
}