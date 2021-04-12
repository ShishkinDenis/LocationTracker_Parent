package com.shishkindenis.locationtracker_parent.views;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MapView extends MvpView {

    void showToast(int toastMessage);

    void backToCalendarActivityWithCancelledResult();

    void setTrack();

    void getPosition(QueryDocumentSnapshot document);

    void backToCalendarActivityWithOkResult();
}
