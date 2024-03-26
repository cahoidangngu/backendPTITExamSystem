package com.quanchun.backendexamsystem.services;

import com.quanchun.backendexamsystem.entities.User;
import com.quanchun.backendexamsystem.models.UserDTO;
import com.quanchun.backendexamsystem.models.UserLoginDTO;

import java.util.List;

public interface UserService {
    User addNewUser(UserDTO newUser);

    List<User> getAllUser();

    User getUserById(Long userId);

    User updateUserById(Long userId, UserDTO updateUser);

    boolean userLogin(UserLoginDTO userLogin);
}
