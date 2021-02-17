package com.shishkindenis.locationtracker_parent.views;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface PhoneAuthView extends MvpView {
    void goToAnotherActivity(Class activity, String name, String value);

    void showToast(String toastMessage);
}
