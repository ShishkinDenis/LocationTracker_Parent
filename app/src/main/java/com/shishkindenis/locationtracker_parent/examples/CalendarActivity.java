package com.shishkindenis.locationtracker_parent.examples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.shishkindenis.locationtracker_parent.MapActivity;
import com.shishkindenis.locationtracker_parent.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {
    Button btnGoToMapFromCalendar;
    String date;
    TextView tvDate;
 public static     String sDate = "2021-01-12";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        CalendarView calendarView = findViewById(R.id.calendarView);
        tvDate = findViewById(R.id.tvDate);



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;

//                selectedDate = new StringBuilder().append(mMonth + 1)
//                        .append("-").append(mDay).append("-").append(mYear)
//                        .append(" ").toString();
//                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                String selectedDate = sdf.format(calendarView.getDate());
//                tvDate.setText(selectedDate);

//                date = new StringBuilder().append(mYear).append("-").append(mMonth+1).append("-").append(mDay).toString();

                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                sDate = sdf.format(calendar.getTime());

                tvDate.setText(sDate);


//                if (selectedDate != null) {
//
//                }
//                else{
//
//                }


                Toast.makeText(getApplicationContext(), sDate, Toast.LENGTH_LONG).show();
            }
        });

        btnGoToMapFromCalendar = findViewById(R.id.btnGoToMapFromCalendar);
        btnGoToMapFromCalendar.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra("abc9", "abc9");
            startActivity(intent);
        });
    }
}