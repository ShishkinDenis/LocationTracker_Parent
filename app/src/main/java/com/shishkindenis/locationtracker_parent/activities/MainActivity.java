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
    public static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        //            сделать гетер
        userID = user.getUid();

        if (user != null) {
            goToAnotherActivity(CalendarActivity.class,"abc", "abc");
        }

        activityMainBinding.btnEmail.setOnClickListener(v -> {
                goToAnotherActivity(EmailAuthActivity.class, "abc", "abc");
        });

        activityMainBinding.btnPhone.setOnClickListener(v -> {
            goToAnotherActivity(PhoneAuthActivity.class,"abc2","abc2");
        });
    }

    public void goToAnotherActivity(Class activity, String name, String value){
        Intent intent = new Intent(this, activity);
        intent.putExtra(name, value);
        startActivity(intent);
    }
}