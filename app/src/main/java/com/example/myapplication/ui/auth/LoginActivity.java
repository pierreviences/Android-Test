package com.example.myapplication.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private boolean passwordShowing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.tvSignup.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
        binding.icEyePassword.setOnClickListener(v -> {
            passwordShowing = !passwordShowing;
            int passwordVisibility = passwordShowing ?
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                    InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;

            binding.etLoginPassword.setInputType(passwordVisibility);
            binding.icEyePassword.setImageResource(passwordShowing ? R.drawable.ic_eye_hide : R.drawable.ic_eye_show);
            binding.etLoginPassword.setSelection(binding.etLoginPassword.length());
        });

    }
}