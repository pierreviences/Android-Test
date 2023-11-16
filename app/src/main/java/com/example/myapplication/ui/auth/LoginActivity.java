package com.example.myapplication.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Toast;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityLoginBinding;
import com.example.myapplication.ui.MainActivity;


public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private boolean passwordShowing = false;
    private LoginViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

       binding.icEyePassword.setOnClickListener(v -> {
            passwordShowing = !passwordShowing;
            int passwordVisibility = passwordShowing ?
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                    InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;

            binding.etLoginPassword.setInputType(passwordVisibility);
            binding.icEyePassword.setImageResource(passwordShowing ? R.drawable.ic_eye_hide : R.drawable.ic_eye_show);
            binding.etLoginPassword.setSelection(binding.etLoginPassword.length());
        });
       viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
       binding.btnLogin.setOnClickListener(v -> {
            String username = binding.etLoginUsername.getText().toString();
            String password = binding.etLoginPassword.getText().toString();

            if (isInputValid(username, password)) {
                showToast(viewModel.isValidUser(username, password) ? R.string.login_success : R.string.login_failure);
                if (viewModel.isValidUser(username, password)){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                showToast(R.string.fill_both_fields);
            }
        });
    }
    private boolean isInputValid(String username, String password) {
        return !username.isEmpty() && !password.isEmpty();
    }
    private void showToast(int messageResId) {
        Toast.makeText(LoginActivity.this, messageResId, Toast.LENGTH_SHORT).show();
    }

}