package com.shishkindenis.locationtracker_parent.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.shishkindenis.locationtracker_parent.R;
import com.shishkindenis.locationtracker_parent.databinding.ActivityPhoneAuthBinding;
import com.shishkindenis.locationtracker_parent.presenters.PhoneAuthPresenter;
import com.shishkindenis.locationtracker_parent.views.PhoneAuthView;

import java.util.concurrent.TimeUnit;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class PhoneAuthActivity extends MvpAppCompatActivity implements PhoneAuthView {

    @InjectPresenter
    PhoneAuthPresenter phoneAuthPresenter;

    private ActivityPhoneAuthBinding activityPhoneAuthBinding;
    private FirebaseAuth auth;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPhoneAuthBinding = ActivityPhoneAuthBinding.inflate(getLayoutInflater());
        View view = activityPhoneAuthBinding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();

        activityPhoneAuthBinding.btnRequestCode.setOnClickListener(v -> {
            if (validatePhoneNumber()) {
                startPhoneNumberVerification(activityPhoneAuthBinding.etPhoneNumber.getText().toString());
            }
        });

        activityPhoneAuthBinding.btnVerifyCode.setOnClickListener(v -> verifyPhoneNumberWithCode(
                mVerificationId, activityPhoneAuthBinding.etVerificationCode.getText().toString()));

//        activityPhoneAuthBinding.btnSignOut.setOnClickListener(v -> signOut());

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                mVerificationInProgress = false;
                signInWithPhoneAuthCredential(credential);
            }
            @Override
            public void onVerificationFailed(FirebaseException e) {
                mVerificationInProgress = false;
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    activityPhoneAuthBinding.etPhoneNumber.setError(getString(R.string.invalid_phone_number));
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    showToast(R.string.quota_exceeded);
                }
            }
            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
    }

    //        Почему onStart?
//    @Override
//    public void onStart() {
//        super.onStart();
//current user
//        FirebaseUser currentUser = auth.getCurrentUser();
//        if (mVerificationInProgress && validatePhoneNumber()) {
//            startPhoneNumberVerification(activityPhoneAuthBinding.etPhoneNumber.getText().toString());
//        }
//    }

    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        mVerificationInProgress = true;
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = task.getResult().getUser();
                       showToast(R.string.authentication_successful);
//                        getViewState().showToast(R.string.authentication_successful);
                        goToAnotherActivity(CalendarActivity.class);
                    } else {
                   showToast((R.string.authentication_failed));
//                        getViewState().showToast((R.string.authentication_failed));
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            activityPhoneAuthBinding.etVerificationCode.setError(getString(R.string.invalid_code));
                        }
                    }
                });
    }

    private boolean validatePhoneNumber() {
        if (activityPhoneAuthBinding.etPhoneNumber.getText().toString().isEmpty()) {
            activityPhoneAuthBinding.etPhoneNumber.setError(getString(R.string.invalid_phone_number));
            showToast(R.string.invalid_phone_number);
            return false;
        }
        return true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
//        if (validatePhoneNumber()) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            signInWithPhoneAuthCredential(credential);
//        }
//        else {
//            showToast(R.string.verification_failed);
//             getViewState().showToast(getString(R.string.verification_failed));
//        }
    }

//    private void signOut(){
//
//        if(auth.getCurrentUser() != null) {
//            auth.signOut();
//            showToast(R.string.sign_out_successful);
////            getViewState().showToast(R.string.sign_out_successful);
//        }
//        else {
//          showToast(R.string.you_havent_sign_in);
////            getViewState().showToast(R.string.you_havent_sign_in);
//        }
//    }

    public void goToAnotherActivity(Class activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    public void showToast(int toastMessage){
        Toast.makeText(getApplicationContext(), toastMessage,
                Toast.LENGTH_LONG).show();
    }

//    TODO Resending Code
//    private void resendVerificationCode(String phoneNumber,
//                                        PhoneAuthProvider.ForceResendingToken token) {
//        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(mAuth)
//                        .setPhoneNumber(phoneNumber)       // Phone number to verify
//                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//                        .setActivity(this)                 // Activity (for callback binding)
//                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
//                        .setForceResendingToken(token)     // ForceResendingToken from callbacks
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//    }
}