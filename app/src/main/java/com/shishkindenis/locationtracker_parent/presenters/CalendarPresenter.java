package com.shishkindenis.locationtracker_parent.presenters;

import com.google.firebase.auth.FirebaseAuth;
import com.shishkindenis.locationtracker_parent.MyApplication;
import com.shishkindenis.locationtracker_parent.R;
import com.shishkindenis.locationtracker_parent.views.CalendarView;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class CalendarPresenter extends MvpPresenter<CalendarView> {

    @Inject
    FirebaseAuth auth;

    public CalendarPresenter() {
    }

    public void signOut() {
        MyApplication.appComponent.inject(this);
        auth.signOut();
        getViewState().showToast(R.string.sign_out_successful);
    }

}
