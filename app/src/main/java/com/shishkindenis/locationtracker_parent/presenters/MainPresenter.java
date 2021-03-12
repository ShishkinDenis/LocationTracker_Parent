package com.shishkindenis.locationtracker_parent.presenters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shishkindenis.locationtracker_parent.daggerUtils.MyApplication;
import com.shishkindenis.locationtracker_parent.singletons.IdSingleton;
import com.shishkindenis.locationtracker_parent.views.MainView;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    @Inject
    FirebaseAuth auth;

    @Inject
    IdSingleton idSingleton;

    private String userID;
    private FirebaseUser user;

    public MainPresenter() {
        MyApplication.appComponent.inject(this);
    }

    public void checkIfUserLoggedIn() {
        user = auth.getCurrentUser();
        if (user != null) {
            userID = user.getUid();
            idSingleton.setUserId(userID);
            getViewState().goToCalendarActivityForResult();
        }
    }
}
