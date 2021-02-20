package com.shishkindenis.locationtracker_parent.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.shishkindenis.locationtracker_parent.databinding.ActivityEmailAuthBinding;
import com.shishkindenis.locationtracker_parent.presenters.EmailAuthPresenter;
import com.shishkindenis.locationtracker_parent.views.EmailAuthView;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class EmailAuthActivity extends MvpAppCompatActivity implements EmailAuthView {

    @InjectPresenter
    EmailAuthPresenter emailAuthPresenter;

    private ActivityEmailAuthBinding binding;

    private FirebaseAuth auth;

    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmailAuthBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        View view = binding.getRoot();
        setContentView(view);

        email = binding.etEmail.getText().toString();
        password = binding.etPassword.getText().toString();

        binding.btnRegister.setOnClickListener(v -> {
            if (binding.etEmail.getText().toString().isEmpty()) {
                binding.etEmail.setError("Required email");
            }
//            if else?
            if (binding.etPassword.getText().toString().isEmpty()) {
                binding.etPassword.setError("Required password");
            } else {
                emailAuthPresenter.createAccount(auth, binding.etEmail.getText().toString(),
                        binding.etPassword.getText().toString());
            }
        });
        binding.btnLogin.setOnClickListener(v -> {
//            вынести If в ValidateForm
            if (binding.etEmail.getText().toString().isEmpty()) {
                binding.etEmail.setError("Required email");
            }
//            if else?
            if (binding.etPassword.getText().toString().isEmpty()) {
                binding.etPassword.setError("Required password");
            } else {
                emailAuthPresenter.signIn(auth, binding.etEmail.getText().toString(),
                        binding.etPassword.getText().toString());
            }
        });
        binding.btnSignOut.setOnClickListener(v -> emailAuthPresenter.signOut(auth));
    }

    public void goToAnotherActivity(Class activity, String name, String value) {
        Intent intent = new Intent(this, activity);
        intent.putExtra(name, value);
        startActivity(intent);
    }

    public void showToast(String toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage,
                Toast.LENGTH_LONG).show();
    }

     /*  private void createAccount(String email, String password) {
//        ЧТО значит пустой return?
        if (!validateForm()) {
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                       Toast.makeText(getApplicationContext(), "User with email: " + email + " was signed up",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(EmailAuthActivity.this, "Signing up failed. Check your internet connection",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }*/
   /* private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
//                        if user !=null
                        userID = user.getUid();

                        Toast.makeText(getApplicationContext(), "Authentication successful",
                                Toast.LENGTH_LONG).show();

                        goToAnotherActivity(CalendarActivity.class,"abc3","abc3");
                    } else {
                        Toast.makeText(EmailAuthActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }*/
    //    private void signOut() {
//                если вход совершен
//        mAuth.signOut();
//        Toast.makeText(getApplicationContext(), "Sign out successful",
//                Toast.LENGTH_LONG).show();
//                 Если вход не совершен
//    }
  /*  public boolean validateForm() {
        boolean valid = true;
        if (binding.etEmail.getText().toString().isEmpty()) {
            binding.etEmail.setError("Required email");
            valid = false;
        } else {
            binding.etEmail.setError(null);
        }
        if (binding.etPassword.getText().toString().isEmpty()) {
            binding.etPassword.setError("Required password");
            valid = false;
        } else {
            binding.etPassword.setError(null);
        }
        return valid;
    }*/

    public void requestIgnoringBatteryOptimizations() {
    Intent intent = new
            Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
            Uri.parse("package:" + getPackageName()));
    startActivity(intent);
    }
}