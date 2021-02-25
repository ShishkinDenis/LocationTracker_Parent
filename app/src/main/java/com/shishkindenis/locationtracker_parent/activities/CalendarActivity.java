package com.shishkindenis.locationtracker_parent.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.shishkindenis.locationtracker_parent.R;
import com.shishkindenis.locationtracker_parent.databinding.ActivityCalendarBinding;
import com.shishkindenis.locationtracker_parent.views.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import moxy.MvpAppCompatActivity;

public class CalendarActivity extends MvpAppCompatActivity implements CalendarView {
    private ActivityCalendarBinding activityCalendarBinding;

    private static String date;
    private String datePattern = "yyyy-MM-dd";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCalendarBinding = ActivityCalendarBinding.inflate(getLayoutInflater());
        View calendarActivityView = activityCalendarBinding.getRoot();
        setContentView(calendarActivityView);

        setSupportActionBar(activityCalendarBinding.toolbar);

        showAlertDialog();

        activityCalendarBinding.calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            int mYear = year;
            int mMonth = month;
            int mDay = dayOfMonth;

            final SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            date = sdf.format(calendar.getTime());
        });
        activityCalendarBinding.btnGoToMapFromCalendar.setOnClickListener(v -> {
            goToAnotherActivity(MapActivity.class);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        signOut();
       goToAnotherActivity(MainActivity.class);
        return super.onOptionsItemSelected(item);
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

    public void signOut() {
       FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        showToast(R.string.sign_out_successful);
    }

    public void goToAnotherActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    public void showToast(int toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage,
                Toast.LENGTH_LONG).show();
    }

}