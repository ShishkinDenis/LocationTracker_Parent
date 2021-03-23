package com.shishkindenis.locationtracker_parent.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shishkindenis.locationtracker_parent.databinding.ActivityMainBinding;
import com.shishkindenis.locationtracker_parent.presenters.MainPresenter;
import com.shishkindenis.locationtracker_parent.views.MainView;

import moxy.presenter.InjectPresenter;

public class MainActivity extends BaseActivity implements MainView {

    @InjectPresenter
    MainPresenter mainPresenter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if (savedInstanceState == null) {
            mainPresenter.checkIfUserLoggedIn();
        }

        binding.btnEmail.setOnClickListener(v -> goToEmailAuthActivity());
        binding.btnPhone.setOnClickListener(v -> goToPhoneAuthActivity());
    }

    @Override
    public void goToCalendarActivityForResult() {
        Intent intent = new Intent(this, CalendarActivity.class);
        finish();
        startActivityForResult(intent, 2);
    }

    public void goToEmailAuthActivity() {
        Intent intent = new Intent(this, EmailAuthActivity.class);
        finish();
        startActivity(intent);

    }

    public void goToPhoneAuthActivity() {
        Intent intent = new Intent(this, PhoneAuthActivity.class);
        finish();
        startActivity(intent);
    }
}