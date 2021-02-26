package com.shishkindenis.locationtracker_parent.presenters;

import com.google.firebase.auth.FirebaseAuth;
import com.shishkindenis.locationtracker_parent.R;
import com.shishkindenis.locationtracker_parent.views.CalendarView;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class CalendarPresenter extends MvpPresenter<CalendarView> {

    public CalendarPresenter() {
    }

    public void signOut() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        getViewState().showToast(R.string.sign_out_successful);
    }

}
