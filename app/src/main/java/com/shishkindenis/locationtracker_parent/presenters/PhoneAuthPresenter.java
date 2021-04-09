package com.shishkindenis.locationtracker_parent.presenters;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.shishkindenis.locationtracker_parent.R;
import com.shishkindenis.locationtracker_parent.singletons.FirebaseUserSingleton;
import com.shishkindenis.locationtracker_parent.views.PhoneAuthView;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class PhoneAuthPresenter extends MvpPresenter<PhoneAuthView> {

    FirebaseUserSingleton firebaseUserSingleton;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String phoneVerificationId;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private String userId;

    @Inject
    public PhoneAuthPresenter(FirebaseUserSingleton firebaseUserSingleton) {
        this.firebaseUserSingleton = firebaseUserSingleton;
    }

    public PhoneAuthProvider.OnVerificationStateChangedCallbacks phoneVerificationCallback(FirebaseAuth auth) {
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                signInWithPhoneAuthCredential(auth, credential);
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

    public void signInWithPhoneAuthCredential(FirebaseAuth auth, PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            userId = user.getUid();
                            firebaseUserSingleton.setUserId(userId);
                            PhoneAuthPresenter.this.getViewState().showToast(R.string.authentication_successful);
                            PhoneAuthPresenter.this.getViewState().goToCalendarActivity();
                        } else {
                            PhoneAuthPresenter.this.getViewState().showToast((R.string.authentication_failed));
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                PhoneAuthPresenter.this.getViewState().showInvalidCodeError();
                            }
                        }
                    }
                });
    }

    public void verifyPhoneNumberWithCode(FirebaseAuth auth, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(phoneVerificationId, code);
        signInWithPhoneAuthCredential(auth, credential);
    }

//    DELETE
    public void someMethod(){
//        Log.d("1","1");
//        logMethod();
//        System.out.println(1);
        print();
    }

    public void logMethod(){
        Log.d("1","1");
    }
    public void print(){
        System.out.println(1);
    }

}
