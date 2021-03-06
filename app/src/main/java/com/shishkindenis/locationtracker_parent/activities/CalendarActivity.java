package com.shishkindenis.locationtracker_parent.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shishkindenis.locationtracker_parent.R;
import com.shishkindenis.locationtracker_parent.databinding.ActivityCalendarBinding;
import com.shishkindenis.locationtracker_parent.presenters.CalendarPresenter;
import com.shishkindenis.locationtracker_parent.views.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import moxy.presenter.InjectPresenter;

public class CalendarActivity extends BaseActivity implements CalendarView {

    @InjectPresenter
    CalendarPresenter calendarPresenter;

    private String date;
    private String datePattern = "yyyy-MM-dd";
    private ActivityCalendarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalendarBinding.inflate(getLayoutInflater());
        View calendarActivityView = binding.getRoot();
        setContentView(calendarActivityView);

        setSupportActionBar(binding.toolbar);

        showAlertDialog();

        binding.calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            int calendarYear = year;
            int calendarMonth = month;
            int calendarDay = dayOfMonth;

            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            date = sdf.format(calendar.getTime());
        });
        binding.btnGoToMapFromCalendar.setOnClickListener(v -> goToAnotherActivity(MapActivity.class));
    }

    @Override
    public void showToast(int toastMessage) {
        super.showToast(toastMessage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        calendarPresenter.signOut();
        goToAnotherActivity(MainActivity.class);
        return super.onOptionsItemSelected(item);
    }

    public void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.choose_the_date_of_tracking)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                })
                .show();
    }

    @Override
    public void goToAnotherActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        intent.putExtra("Date",date);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            showToast(R.string.there_is_no_track);
        }
    }

}