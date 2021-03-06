package com.shishkindenis.locationtracker_parent.presenters;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.shishkindenis.locationtracker_parent.MyApplication;
import com.shishkindenis.locationtracker_parent.R;
import com.shishkindenis.locationtracker_parent.activities.CalendarActivity;
import com.shishkindenis.locationtracker_parent.singletons.IdSingleton;
import com.shishkindenis.locationtracker_parent.views.PhoneAuthView;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class PhoneAuthPresenter extends MvpPresenter<PhoneAuthView> {

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String phoneVerificationId;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;

    @Inject
    IdSingleton idSingleton;
    String userId;

    public PhoneAuthPresenter() { }

    public PhoneAuthProvider.OnVerificationStateChangedCallbacks phoneVerificationCallback(FirebaseAuth auth) {
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                signInWithPhoneAuthCredential(auth,credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    getViewState().showInvalidPhoneNumberError();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    getViewState().showToast(R.string.quota_exceeded);
                }
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                phoneVerificationId = verificationId;
                forceResendingToken = token;
                getViewState().enableVerifyButton();
            }
        };
        return callbacks;
    }

    private void signInWithPhoneAuthCredential(FirebaseAuth auth,PhoneAuthCredential credential) {
        MyApplication.appComponent.inject(this);

        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        FirebaseUser user = task.getResult().getUser();

//                        idSingleton = IdSingleton.getInstance();
                        userId = user.getUid();
                        idSingleton.setUserId(userId);

                        getViewState().showToast(R.string.authentication_successful);
                        getViewState().goToAnotherActivity(CalendarActivity.class);
                    } else {
                        getViewState().showToast((R.string.authentication_failed));

                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            getViewState().showInvalidCodeError();
                        }
                    }
                });
    }

    public void verifyPhoneNumberWithCode(FirebaseAuth auth,String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(phoneVerificationId, code);
        signInWithPhoneAuthCredential(auth,credential);
    }

}
