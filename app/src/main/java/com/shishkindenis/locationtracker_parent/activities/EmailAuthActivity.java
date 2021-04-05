package com.shishkindenis.locationtracker_parent.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.shishkindenis.locationtracker_parent.R;
import com.shishkindenis.locationtracker_parent.daggerUtils.MyApplication;
import com.shishkindenis.locationtracker_parent.databinding.ActivityEmailAuthBinding;
import com.shishkindenis.locationtracker_parent.presenters.EmailAuthPresenter;
import com.shishkindenis.locationtracker_parent.singletons.FirebaseUserSingleton;
import com.shishkindenis.locationtracker_parent.views.EmailAuthView;

import javax.inject.Inject;

import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class EmailAuthActivity extends BaseActivity implements EmailAuthView {

    @Inject
    @InjectPresenter
    EmailAuthPresenter emailAuthPresenter;
    @Inject
    FirebaseUserSingleton firebaseUserSingleton;
    private ActivityEmailAuthBinding binding;

    @ProvidePresenter
    EmailAuthPresenter provideEmailAuthPresenter() {
        return emailAuthPresenter;
    }

    public void goToCalendarActivity() {
        Intent intent = new Intent(this, CalendarActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.appComponent.inject(this);
        super.onCreate(savedInstanceState);
        binding = ActivityEmailAuthBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnRegister.setOnClickListener(v -> {
            if (emailIsValid() & passwordIsValid()) {
                registerIfValid();
            } else {
                setErrorIfInvalid();
            }
        });
        binding.btnLogin.setOnClickListener(v -> {
            if (emailIsValid() & passwordIsValid()) {
                logInIfValid();
            } else {
                setErrorIfInvalid();
            }
        });
    }

    public void showToastWithEmail(String toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage,
                Toast.LENGTH_LONG).show();
    }

    public boolean emailIsValid() {
        return !binding.etEmail.getText().toString().isEmpty();
    }

    public boolean passwordIsValid() {
        return !binding.etPassword.getText().toString().isEmpty();
    }

    public void setErrorIfInvalid() {
        if (!emailIsValid()) {
            binding.etEmail.setError(getString(R.string.required_email));
        }
        if (!passwordIsValid()) {
            binding.etPassword.setError(getString(R.string.required_password));
        }
    }

    public void logInIfValid() {
        binding.pbEmailAuth.setVisibility(View.VISIBLE);
        emailAuthPresenter.signIn(firebaseUserSingleton.getFirebaseAuth(), binding.etEmail.getText().toString(),
                binding.etPassword.getText().toString());
        binding.pbEmailAuth.setVisibility(View.INVISIBLE);
    }

    public void registerIfValid() {
        binding.pbEmailAuth.setVisibility(View.VISIBLE);
        emailAuthPresenter.createAccount(firebaseUserSingleton.getFirebaseAuth(), binding.etEmail.getText().toString(),
                binding.etPassword.getText().toString());
        binding.pbEmailAuth.setVisibility(View.INVISIBLE);
    }

}