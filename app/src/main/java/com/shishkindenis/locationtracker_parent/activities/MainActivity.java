package com.shishkindenis.locationtracker_parent.activities;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shishkindenis.locationtracker_parent.MyApplication;
import com.shishkindenis.locationtracker_parent.databinding.ActivityMainBinding;
import com.shishkindenis.locationtracker_parent.presenters.MainPresenter;
import com.shishkindenis.locationtracker_parent.singletons.IdSingleton;
import com.shishkindenis.locationtracker_parent.views.MainView;

import javax.inject.Inject;

import moxy.presenter.InjectPresenter;

public class MainActivity extends BaseActivity implements MainView {

    @InjectPresenter
    MainPresenter mainPresenter;

    @Inject
    FirebaseAuth auth;

    @Inject
    IdSingleton idSingleton;

    public static String userID;
    public static FirebaseUser user;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        MyApplication.appComponent.inject(this);
        user = auth.getCurrentUser();

//        sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        if (user != null) {
            userID = user.getUid();

//            idSingleton = IdSingleton.getInstance();
            idSingleton.setUserId(userID);

//            вынести в метод
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString(APP_PREFERENCES_USER_ID, userID);
//            editor.apply();



            goToAnotherActivity(CalendarActivity.class);
            showButtonBackToCalendar();
        }
        //        else


        binding.btnEmail.setOnClickListener(v -> goToAnotherActivity(EmailAuthActivity.class));
        binding.btnPhone.setOnClickListener(v -> goToAnotherActivity(PhoneAuthActivity.class));
//        buttons for landscape orientation
        binding.btnBackToCalendar.setOnClickListener(v -> goToAnotherActivity(CalendarActivity.class));
    }

    @Override
    public void goToAnotherActivity(Class activity) {
        super.goToAnotherActivity(activity);
    }

    public void showButtonBackToCalendar(){
        binding.btnBackToCalendar.setVisibility(View.VISIBLE);
        binding.ivCalendar.setVisibility(View.VISIBLE);
    }
}