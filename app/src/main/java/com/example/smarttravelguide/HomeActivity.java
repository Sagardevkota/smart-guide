package com.example.smarttravelguide;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private BottomNavigationView bottomNavigationView;
    private RelativeLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferences = getSharedPreferences("smart-travel-guide", Context.MODE_PRIVATE);

        rootLayout = findViewById(R.id.rootLayout);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,new HomeFragment())
                .commit();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.bottom_nav_home:
                     item.setChecked(true);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container,new HomeFragment())
                            .commit();
                    break;
                case R.id.bottom_nav_map:
                   Intent intent = new Intent(this, MapActivity.class);
                   startActivity(intent);
                    break;


                case R.id.bottom_nav_my_account:
                    item.setChecked(true);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container,new MyAccountFragment())
                            .commit();
                    break;

                case R.id.bottom_nav_my_booking:
                    item.setChecked(true);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container,new MyBookingFragment())
                            .commit();
                    break;
            }

            return false;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting_menu_settings:
                break;
            case R.id.setting_menu_updates:
                Snackbar.make(rootLayout,"Your app is upto date",Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.setting_about:
              break;
            case R.id.logout:
                MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this);
                alertDialogBuilder.setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            sharedPreferences.edit().putBoolean("authenticated",false).apply();
                            Intent intent = new Intent(this,LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();



        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
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
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if(f instanceof HomeFragment){
                bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);
            }

            if(f instanceof MyAccountFragment){
                bottomNavigationView.setSelectedItemId(R.id.bottom_nav_my_account);
            }

        }


    }


}