package com.shishkindenis.locationtracker_parent.views;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface EmailAuthView extends MvpView {
    void showToast(int toastMessage);

    void showToastWithEmail(String toastMessage);

    void goToCalendarActivity();

}

