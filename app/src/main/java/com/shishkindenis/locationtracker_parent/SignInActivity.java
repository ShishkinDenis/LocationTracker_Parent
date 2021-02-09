package com.shishkindenis.locationtracker_parent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shishkindenis.locationtracker_parent.databinding.ActivitySignInBinding;
import com.shishkindenis.locationtracker_parent.presenters.SignInPresenter;
import com.shishkindenis.locationtracker_parent.views.SignInView;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class SignInActivity extends MvpAppCompatActivity implements SignInView {

    @InjectPresenter
    SignInPresenter signInPresenter;

    private static final String TAG = "EmailPassword";

    private ActivitySignInBinding binding;

    private FirebaseAuth mAuth;

    private String email;
    private String password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        email = binding.etEmail.getText().toString();
        password = binding.etPassword.getText().toString();


        binding.btnLogin.setOnClickListener(v -> {
            Toast toast = Toast.makeText(getApplicationContext(), "Действие",
                    Toast.LENGTH_SHORT);
            toast.show();
            checkEmail();
            checkPassword();
            binding.etEmail.setError("f");
            showError();
        });
        binding.btnRegister.setOnClickListener(v -> {
//                createAccount(email,password);
//                 createAccount("sh-kin@mail.ru","stalker");
             createAccount(binding.etEmail.getText().toString(),binding.etPassword.getText().toString());
        });

        binding.btnLogin.setOnClickListener(v -> signIn(binding.etEmail.getText().toString(),
                binding.etPassword.getText().toString()));

        binding.btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

//        showProgressBar();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(SignInActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                    }

                    // [START_EXCLUDE]
//                        hideProgressBar();
                    // [END_EXCLUDE]
                });
        // [END create_user_with_email]
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

//        showProgressBar();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                        binding.status.setText("Online");
                        //вынести в отдельный метод -goToMapActivity
                        Intent intent = new Intent(this, MapActivity.class);
                        intent.putExtra("abc3", "abc3");
                        startActivity(intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(SignInActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        // [START_EXCLUDE]
//                        checkForMultiFactorFailure(task.getException());
                        // [END_EXCLUDE]
                    }

                    // [START_EXCLUDE]
                    if (!task.isSuccessful()) {
//                        mBinding.status.setText(R.string.auth_failed);
                    }
//                        hideProgressBar();
                    // [END_EXCLUDE]
                });
        // [END sign_in_with_email]
    }

    private void signOut() {
        mAuth.signOut();
        binding.status.setText("OFFline");
    }

    private boolean validateForm() {
        boolean valid = true;

//        if (TextUtils.isEmpty(email)) {
        if (binding.etEmail.getText().toString().isEmpty()) {
            binding.etEmail.setError("Required email.");
            valid = false;
        } else {
            binding.etEmail.setError(null);
        }

//        if (TextUtils.isEmpty(password)) {
        if (binding.etPassword.getText().toString().isEmpty()) {
            binding.etPassword.setError("Required.");
            valid = false;
        } else {
            binding.etPassword.setError(null);
        }

        return valid;
    }

   /* private void updateUI(FirebaseUser user) {
        hideProgressBar();
        if (user != null) {
            mBinding.status.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            mBinding.detail.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            mBinding.emailPasswordButtons.setVisibility(View.GONE);
            mBinding.emailPasswordFields.setVisibility(View.GONE);
            mBinding.signedInButtons.setVisibility(View.VISIBLE);

            if (user.isEmailVerified()) {
                mBinding.verifyEmailButton.setVisibility(View.GONE);
            } else {
                mBinding.verifyEmailButton.setVisibility(View.VISIBLE);
            }
        } else {
            mBinding.status.setText(R.string.signed_out);
            mBinding.detail.setText(null);

            mBinding.emailPasswordButtons.setVisibility(View.VISIBLE);
            mBinding.emailPasswordFields.setVisibility(View.VISIBLE);
            mBinding.signedInButtons.setVisibility(View.GONE);
        }
    }*/








    private void showLoginFailed(){
//        TODO
        Toast.makeText(getApplicationContext(),"LogIn failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage("Что-то")
//                .setOnCancelListener(dialog -> mRepositoriesPresenter.onErrorCancel())
                .show();
    }
    @Override
    public void checkEmail(){
        if (binding.etEmail.getText().toString().isEmpty()){
            binding.etEmail.setError("f");
        }

//        if else проверка на валидность
    }

    @Override
    public void checkPassword() {
        if (binding.etPassword.getText().toString().isEmpty()){
            binding.etPassword.setError("a");
        }
        //        if else проверка на валидность
    }



}