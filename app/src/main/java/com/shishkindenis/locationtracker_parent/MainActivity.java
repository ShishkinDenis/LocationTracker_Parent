package com.shishkindenis.locationtracker_parent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.shishkindenis.locationtracker_parent.databinding.ActivityMainBinding;
import com.shishkindenis.locationtracker_parent.databinding.ActivityPhoneAuthBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnEmail.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignInActivity.class);
            intent.putExtra("abc", "abc");
            startActivity(intent);
        });

        binding.btnPhone.setOnClickListener(v -> {
            Intent intent = new Intent(this, PhoneAuthActivity.class);
            intent.putExtra("abc2", "abc2");
            startActivity(intent);

        });
    }
}