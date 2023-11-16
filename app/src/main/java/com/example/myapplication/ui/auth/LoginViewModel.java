package com.example.myapplication.ui.auth;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.model.User;
import com.example.myapplication.utils.UserUtils;

import java.util.List;

public class LoginViewModel extends ViewModel {
    private User[] users = UserUtils.getUsers();

    public boolean isValidUser(String inputUsername, String inputPassword) {
        for (User user : users) {
            if (user.getUsername().equals(inputUsername) && user.getPassword().equals(inputPassword)) {
                return true;
            }
        }
        return false;
    }
}
