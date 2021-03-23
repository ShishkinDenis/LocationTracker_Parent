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
import com.shishkindenis.locationtracker_parent.daggerUtils.MyApplication;
import com.shishkindenis.locationtracker_parent.databinding.ActivityCalendarBinding;
import com.shishkindenis.locationtracker_parent.presenters.CalendarPresenter;
import com.shishkindenis.locationtracker_parent.singletons.DateSingleton;
import com.shishkindenis.locationtracker_parent.views.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class CalendarActivity extends BaseActivity implements CalendarView {

    private static final String DATE_FIELD = "Date";
    private static final String YEAR = "Year";
    private static final String MONTH = "Month";
    private static final String DAY = "Day";
    private final String datePattern = "yyyy-MM-dd";
    @Inject
    @InjectPresenter
    CalendarPresenter calendarPresenter;
    String date;
    @Inject
    DateSingleton dateSingleton;
    private ActivityCalendarBinding binding;
    private Calendar calendar;
    private int calendarYear;
    private int calendarMonth;
    private int calendarDay;

    @ProvidePresenter
    CalendarPresenter providePresenter() {
        return calendarPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.appComponent.inject(this);
        super.onCreate(savedInstanceState);
        binding = ActivityCalendarBinding.inflate(getLayoutInflater());
        View calendarActivityView = binding.getRoot();
        setContentView(calendarActivityView);
        calendar = Calendar.getInstance();
        setSupportActionBar(binding.toolbar);

        if (savedInstanceState == null) {
            showAlertDialog();
        }

        if (savedInstanceState != null) {
            restoreChosenDate(savedInstanceState);
        }

        binding.calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            calendarYear = year;
            calendarMonth = month;
            calendarDay = dayOfMonth;
            calendar.set(calendarYear, calendarMonth, calendarDay);

            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
            date = sdf.format(calendar.getTime());
            dateSingleton.setDate(date);
        });
        binding.btnGoToMapFromCalendar.setOnClickListener(v -> goToMapActivity());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(YEAR, calendarYear);
        outState.putInt(MONTH, calendarMonth);
        outState.putInt(DAY, calendarDay);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        calendarPresenter.signOut();
        setResult(RESULT_OK, null);
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.choose_the_date_of_tracking)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                })
                .show();
    }

    public void goToMapActivity() {
        Intent intent = new Intent(this, MapActivity.class);
        startActivityForResult(intent, 5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            showToast(R.string.there_is_no_track);
        }
    }

    public void restoreChosenDate(Bundle savedInstanceState) {
        calendarYear = savedInstanceState.getInt(YEAR);
        calendarMonth = savedInstanceState.getInt(MONTH);
        calendarDay = savedInstanceState.getInt(DAY);
        calendar.set(Calendar.YEAR, calendarYear);
        calendar.set(Calendar.MONTH, calendarMonth);
        calendar.set(Calendar.DATE, calendarDay);
        long time = calendar.getTimeInMillis();
        binding.calendarView.setDate(time);
    }

}