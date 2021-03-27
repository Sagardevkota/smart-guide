package com.example.smarttravelguide;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.smarttravelguide.api.STGAPI;
import com.example.smarttravelguide.model.User;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MyAccountEditFragment extends Fragment {


    private static final String TAG = "MY_ACCOUNT_EDIT_FRAGMENT";
    private final List<String> genderList = Arrays.asList("Male","Female","Others");
    private EditText etFullName, etEmail, etUserName, etPhone,etPassword;
    private ChipGroup genderChipGroup;
    private Button buSave;
    private ImageButton ibBack;
    private LinearLayout rootLayout;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_account_edit, container, false);
        etFullName = view.findViewById(R.id.etFullName);
        etEmail = view.findViewById(R.id.etEmail);
        etUserName = view.findViewById(R.id.etUserName);
        genderChipGroup = view.findViewById(R.id.genderChipGroup);
        etPhone = view.findViewById(R.id.etPhone);
        etPassword = view.findViewById(R.id.etPassword);
        buSave = view.findViewById(R.id.buSave);
        rootLayout = view.findViewById(R.id.rootLayout);
        ibBack = view.findViewById(R.id.ivCancel);

        ibBack.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });

        setViews();


        return view;
    }

    private void setViews() {
        User user = getArguments().getParcelable("user");
        Log.i(TAG, "setViews: "+user.toString());
        etFullName.setText(user.getFullName());
        etEmail.setText(user.getEmail());
        genderList.forEach(gender->{
            Chip chip =
                    (Chip) getLayoutInflater()
                            .inflate(R.layout.single_chip_layout, genderChipGroup, false);
            chip.setText(gender);
            genderChipGroup.addView(chip);
        });
        genderChipGroup.check(genderChipGroup.getChildAt(0).getId());
        genderChipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Chip chip = group.findViewById(checkedId);
            if (chip!=null)
                user.setGender(chip.getText().toString());

        });
        etPhone.setText(user.getPhone());
        etUserName.setText(user.getUserName());
        etPassword.setText("");

        buSave.setOnClickListener(v -> {
            validateEmail();validatePhone();validatePassword();validateFullName();validateUserName();

            if (!validateEmail()||!validatePassword()||!validatePhone()||!validateUserName()||!validateFullName()){
                Snackbar.make(rootLayout, "Please fill up every field", Snackbar.LENGTH_SHORT).show();
            }
            else{

                user.setFullName(etFullName.getText().toString());
                user.setEmail(etEmail.getText().toString());
                user.setUserName(etUserName.getText().toString());
                user.setPassword(etPhone.getText().toString());

                STGAPI.getApiService().updateUser(user)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(jsonResponse -> {

                            Snackbar.make(rootLayout, jsonResponse.getMessage(), Snackbar.LENGTH_SHORT).show();
                            if (jsonResponse.getMessage().equalsIgnoreCase("User updated successfully"))
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, new MyAccountFragment())
                                        .commit();

                        },throwable -> Log.e(TAG, "setViews: "+throwable.getMessage() ));

            }


            });

    }



    private boolean validateEmail(){
        String emailInput = etEmail.getText().toString().trim();
        if (emailInput.isEmpty()) {
            etEmail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            etEmail.setError("Please enter a valid email address");
            return false;
        } else {
            etEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput1 = etPassword.getText().toString().trim();

        if (passwordInput1.isEmpty()) {
            etPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput1).matches()) {
            etPassword.setError("Password too weak");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput1).matches()) {
            etPassword.setError("Password too weak");
            return false;
        } else {
            etPassword.setError(null);
            return true;
        }
    }



    private boolean validatePhone() {
        if (etPhone.getText().length()!=10)
        {
            etPhone.setError("Invalid phone number");
            return false;
        }
        else return true;
    }

    private boolean validateFullName(){
        if (etFullName.getText().length()==0)
        {
            etFullName.setError("Field cant be empty");
            return false;
        }
        else return true;
    }

    private boolean validateUserName(){
        if (etUserName.getText().length()==0)
        {
            etUserName.setError("Field cant be empty");
            return false;
        }
        else return true;

    }
}
