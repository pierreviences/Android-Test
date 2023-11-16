package com.example.myapplication.ui.auth;

import androidx.lifecycle.ViewModel;
import com.example.myapplication.data.model.auth.User;
import com.example.myapplication.utils.UserUtils;

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
