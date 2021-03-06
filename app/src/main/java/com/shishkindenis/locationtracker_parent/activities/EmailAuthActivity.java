package com.shishkindenis.locationtracker_parent.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.shishkindenis.locationtracker_parent.MyApplication;
import com.shishkindenis.locationtracker_parent.R;
import com.shishkindenis.locationtracker_parent.databinding.ActivityEmailAuthBinding;
import com.shishkindenis.locationtracker_parent.presenters.EmailAuthPresenter;
import com.shishkindenis.locationtracker_parent.views.EmailAuthView;

import javax.inject.Inject;

import moxy.presenter.InjectPresenter;

public class EmailAuthActivity extends BaseActivity implements EmailAuthView {

    @InjectPresenter
    EmailAuthPresenter emailAuthPresenter;

    @Inject
    FirebaseAuth auth;

    private ActivityEmailAuthBinding binding;

    @Override
    public void showToast(int toastMessage) {
        super.showToast(toastMessage);
    }

    @Override
    public void goToAnotherActivity(Class activity) {
        super.goToAnotherActivity(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmailAuthBinding.inflate(getLayoutInflater());

        MyApplication.appComponent.inject(this);

        View view = binding.getRoot();
        setContentView(view);

        binding.btnRegister.setOnClickListener(v -> {
            if (validateForm()) {
                binding.pbEmailAuth.setVisibility(View.VISIBLE);
                emailAuthPresenter.createAccount(auth, binding.etEmail.getText().toString(),
                        binding.etPassword.getText().toString());
                binding.pbEmailAuth.setVisibility(View.INVISIBLE);
            }
        });
        binding.btnLogin.setOnClickListener(v -> {
            if (validateForm()) {
                binding.pbEmailAuth.setVisibility(View.VISIBLE);
                emailAuthPresenter.signIn(auth, binding.etEmail.getText().toString(),
                        binding.etPassword.getText().toString());
                binding.pbEmailAuth.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void showToastWithEmail(String toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage,
                Toast.LENGTH_LONG).show();
    }

    public boolean validateForm() {
        boolean valid = true;
        if (binding.etEmail.getText().toString().isEmpty()) {
            binding.etEmail.setError(getString(R.string.required_email));
            valid = false;
        }
        if (binding.etPassword.getText().toString().isEmpty()) {
            binding.etPassword.setError(getString(R.string.required_password));
            valid = false;
        }
        return valid;
    }
}