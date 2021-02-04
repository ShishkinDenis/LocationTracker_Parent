package com.shishkindenis.locationtracker_parent.presenters;

import com.shishkindenis.locationtracker_parent.views.SignInView;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class SignInPresenter extends MvpPresenter<SignInView> {

    public SignInPresenter() {
    }

//    public void signIn(String email, String password) {
//
//        Integer emailError = null;
//        Integer passwordError = null;
//
//        getViewState().hideFormError();
//
//        if (TextUtils.isEmpty(email)) {
//            emailError = "This field is required";
//        }
//
//        if (TextUtils.isEmpty(password)) {
//            passwordError = "This password is too short";
//        }
//
//        if (emailError != null || passwordError != null) {
//            getViewState().showFormError(emailError, passwordError);
//            return;
//        }



//    public void loginDataChanged(String username, String password) {
//        if (!isUserNameValid(username)) {
//            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
//        } else if (!isPasswordValid(password)) {
//            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
//        } else {
//            loginFormState.setValue(new LoginFormState(true));
//        }
//    }
//    }
}
