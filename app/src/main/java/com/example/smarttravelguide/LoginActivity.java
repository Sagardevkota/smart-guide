package com.example.smarttravelguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import kotlin.jvm.internal.Ref;

public class LoginActivity extends AppCompatActivity {

    //declare variables
    private EditText textEmail, textPassword;
    private TextView tvRegister;
    private Button buLogin;
    private SharedPreferences sharedPreferences;
    private LinearLayout rootLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set xml view
        setContentView(R.layout.activity_login);
        getSupportActionBar().show();
        //initialize with xml ids
        sharedPreferences = getSharedPreferences("smart-travel-guide", Context.MODE_PRIVATE);
        //check for authenticate boolean variable in shared preference
        rootLayout = findViewById(R.id.linearLayout);
        textEmail = findViewById(R.id.etEmail);
        textPassword = findViewById(R.id.etPassword);
        buLogin = findViewById(R.id.loginClick);
        tvRegister = findViewById(R.id.tvCreateAccount);
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });


        buLogin.setOnClickListener(v -> {
            doLogin();
        });
    }


    private void doLogin() {
        //get edittext values
        String email = textEmail.getText().toString();
        String password = textPassword.getText().toString();
        //check if email and password are not empty
        if (email.length() != 0 && password.length() != 0) {

            if (email.equalsIgnoreCase("Bishal") && password.equalsIgnoreCase("bishal123")) {
                sharedPreferences.edit().putBoolean("authenticated", true).apply();
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                goToHomeActivity();
            } else
                Snackbar.make(rootLayout, "Incorrect email or password", Snackbar.LENGTH_SHORT).show();
        } else
            Snackbar.make(rootLayout, "Please fill up every field", Snackbar.LENGTH_SHORT).show();
    }


    private void goToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this);
        alertDialogBuilder.setTitle("Exit")
                .setMessage("Do you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    finishAffinity();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

}