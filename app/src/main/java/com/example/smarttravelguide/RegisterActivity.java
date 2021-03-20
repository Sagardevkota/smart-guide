package com.example.smarttravelguide;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.example.smarttravelguide.model.User;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "REGISTER_ACTIVITY";
    private EditText etEmail,etPassword,etConfirmPassword,etPhone,etFullName,etUserName;
    private NestedScrollView nestedScrollView;
    private ChipGroup genderChipGroup;
    private final List<String> genderList = Arrays.asList("Male","Female","Others");
    private Button buRegister;
    private String selectedGender = "Male";
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nestedScrollView = findViewById(R.id.nestedScrollView);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        buRegister = findViewById(R.id.buRegister);
        etPhone = findViewById(R.id.etPhone);
        etFullName = findViewById(R.id.etFullName);
        etUserName = findViewById(R.id.etUsername);
        genderChipGroup = findViewById(R.id.genderChipGroup);
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
                selectedGender = chip.getText().toString();

        });
        buRegister.setOnClickListener(v -> doRegister());

    }

    private void doRegister() {
        validateEmail();
        validatePassword();
        validatePassword2();
        validatePhone();
        validateUserName();
        validateFullName();
        confirmPassword();
        if (!validateEmail()||!validatePassword()||!validatePassword2()||!confirmPassword()||!validatePhone()||!validateUserName()||!validateFullName()){
            Snackbar.make(nestedScrollView, "Please fill up every field", Snackbar.LENGTH_SHORT).show();
        }
        else
            uploadData();
    }



    private void uploadData() {
        Toast.makeText(getApplicationContext(),"Registering",Toast.LENGTH_SHORT).show();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String phone = etPhone.getText().toString();
        String gender = selectedGender;
        String userName = etUserName.getText().toString();
        String fullName = etFullName.getText().toString();

        //construct user object
        User user = new User(fullName,email,password,userName,gender,phone);

        Log.i(TAG, "uploadData: "+user.toString());


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
    private boolean validatePassword2() {
        String passwordInput1 = etConfirmPassword.getText().toString().trim();

        if (passwordInput1.isEmpty()) {
            etConfirmPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput1).matches()) {
            etConfirmPassword.setError("Password too weak");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput1).matches()) {
            etConfirmPassword.setError("Password too weak");
            return false;
        } else {
            etConfirmPassword.setError(null);
            return true;
        }
    }

    private boolean confirmPassword() {
        String passwordInput1 = etPassword.getText().toString().trim();
        String passwordInput2 = etConfirmPassword.getText().toString().trim();
        if (!passwordInput1.equals(passwordInput2)) {
          etConfirmPassword.setError("Password donot match");
            return false;
        } else {
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