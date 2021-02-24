package com.shishkindenis.locationtracker_parent.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shishkindenis.locationtracker_parent.databinding.ActivityMainBinding;
import com.shishkindenis.locationtracker_parent.presenters.MainPresenter;
import com.shishkindenis.locationtracker_parent.views.MainView;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @InjectPresenter
    MainPresenter mainPresenter;

    private ActivityMainBinding activityMainBinding;
    private FirebaseAuth mAuth;
    private static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            userID = user.getUid();
            goToAnotherActivity(CalendarActivity.class);
        }

        activityMainBinding.btnEmail.setOnClickListener(v -> goToAnotherActivity(EmailAuthActivity.class));
        activityMainBinding.btnPhone.setOnClickListener(v -> goToAnotherActivity(PhoneAuthActivity.class));
    }

    public static String getUserID() {
        return userID;
    }

    public void goToAnotherActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}