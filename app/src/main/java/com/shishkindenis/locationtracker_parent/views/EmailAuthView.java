package com.shishkindenis.locationtracker_parent.views;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface EmailAuthView extends MvpView {

    void showError();
    void checkEmail();
    void checkPassword();

//    void signOut();//перенести в отдельный презентер

//    void startSignIn();
//
//    void finishSignIn();
//
//    void failedSignIn(String message);
//
//    void hideError();
//
//    void hideFormError();
//
//    void showFormError(Integer emailError, Integer passwordError);

}

