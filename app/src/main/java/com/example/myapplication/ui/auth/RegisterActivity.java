package com.example.myapplication.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.model.User;
import com.example.myapplication.databinding.ActivityLoginBinding;
import com.example.myapplication.databinding.ActivityRegisterBinding;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    private boolean passwordShowing = false;
    private RegisterViewModel viewModel;
    private List<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.tvSignup.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            RegisterActivity.this.finish();
        });

        binding.icEyePassword.setOnClickListener(v -> {
            passwordShowing = !passwordShowing;
            int passwordVisibility = passwordShowing ?
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                    InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;

            binding.etRegisterPassword.setInputType(passwordVisibility);
            binding.icEyePassword.setImageResource(passwordShowing ? R.drawable.ic_eye_hide : R.drawable.ic_eye_show);
            binding.etRegisterPassword.setSelection(binding.etRegisterPassword.length());
        });

        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(RegisterViewModel.class);
        users = viewModel.getUsers();

        binding.btnRegister.setOnClickListener( v -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            String username = binding.etRegisterUsername.getText().toString().trim();
            String password = binding.etRegisterPassword.getText().toString().trim();
            binding.progressBar.setVisibility(View.VISIBLE);
            viewModel.registerUser(username, password);
        });

        viewModel.getRegistrationSuccessLiveData().observe(this, registrationSuccess -> {
            binding.progressBar.setVisibility(View.INVISIBLE);
            if (registrationSuccess) {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Username dan password harus diisi", Toast.LENGTH_SHORT).show();
            }
        });

    }
}