package com.shishkindenis.locationtracker_parent.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shishkindenis.locationtracker_parent.R;
import com.shishkindenis.locationtracker_parent.databinding.ActivityCalendarBinding;
import com.shishkindenis.locationtracker_parent.views.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import moxy.MvpAppCompatActivity;

public class CalendarActivity extends MvpAppCompatActivity implements CalendarView {
    private ActivityCalendarBinding activityCalendarBinding;

    private static String date;

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

            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            date = sdf.format(calendar.getTime());
        });
        activityCalendarBinding.btnGoToMapFromCalendar.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });
    }

    public static String getDate() {
        return date;
    }

    public void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.choose_the_date_of_tracking)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                })
                .show();
    }
}