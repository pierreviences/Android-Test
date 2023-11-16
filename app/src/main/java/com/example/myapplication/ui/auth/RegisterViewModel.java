package com.example.myapplication.ui.auth;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.model.User;

import java.util.ArrayList;
import java.util.List;
import androidx.lifecycle.LiveData;

public class RegisterViewModel extends ViewModel {
    private MutableLiveData<Boolean> registrationSuccess = new MutableLiveData<>();
    private List<User> users = new ArrayList<>();

    public void registerUser(String username, String password) {
        if (isRegisterValid(username, password)) {
            User user = new User(username, password);
            users.add(user);
            registrationSuccess.setValue(true);
        } else {
            registrationSuccess.setValue(false);
        }
    }

    private boolean isRegisterValid(String username, String password) {
        return username != null && !username.isEmpty() && password != null && !password.isEmpty();
    }
    public LiveData<Boolean> getRegistrationSuccessLiveData() {
        return registrationSuccess;
    }

    public List<User> getUsers() {
        return users;
    }
}