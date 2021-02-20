package com.shishkindenis.locationtracker_parent.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shishkindenis.locationtracker_parent.databinding.ActivityCalendarBinding;
import com.shishkindenis.locationtracker_parent.views.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import moxy.MvpAppCompatActivity;

public class CalendarActivity extends MvpAppCompatActivity implements CalendarView {
    private ActivityCalendarBinding activityCalendarBinding;

    public static String sDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCalendarBinding = ActivityCalendarBinding.inflate(getLayoutInflater());
        View calendarActivityView = activityCalendarBinding.getRoot();
        setContentView(calendarActivityView);

        showAlertDialog();

        activityCalendarBinding.calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            int mYear = year;
            int mMonth = month;
            int mDay = dayOfMonth;

//Вынести в метод паттерн с датой
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);

//            сделать гетер
            sDate = sdf.format(calendar.getTime());

//            Toast.makeText(getApplicationContext(), sDate, Toast.LENGTH_LONG).show();
        });

        activityCalendarBinding.btnGoToMapFromCalendar.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra("abc9", "abc9");
            startActivity(intent);
        });
    }
    public void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Please choose the date of tracking")
                .setPositiveButton("OK", (dialog, which) -> {
                })
                .show();
    }
}