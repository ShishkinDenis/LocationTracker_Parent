package com.shishkindenis.locationtracker_parent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.shishkindenis.locationtracker_parent.databinding.ActivityMainBinding;
import com.shishkindenis.locationtracker_parent.examples.CalendarActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        activityMainBinding.btnEmail.setOnClickListener(v -> {
            Intent intent = new Intent(this, EmailAuthActivity.class);
            intent.putExtra("abc", "abc");
            startActivity(intent);
        });

        activityMainBinding.btnPhone.setOnClickListener(v -> {
            Intent intent = new Intent(this, PhoneAuthActivity.class);
            intent.putExtra("abc2", "abc2");
            startActivity(intent);

        });

        activityMainBinding.btnCalendar.setOnClickListener(v -> {
            Intent intent = new Intent(this, CalendarActivity.class);
            intent.putExtra("abc22", "abc22");
            startActivity(intent);

        });
    }
}