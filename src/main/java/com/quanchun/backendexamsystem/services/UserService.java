package com.quanchun.backendexamsystem.services;

import com.quanchun.backendexamsystem.entities.User;
import com.quanchun.backendexamsystem.error.RoleNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;
import com.quanchun.backendexamsystem.models.UserDTO;
import com.quanchun.backendexamsystem.models.UserLoginDTO;

import java.util.List;

public interface UserService {
    User addNewUser(UserDTO newUser) throws RoleNotFoundException;

    List<User> getAllUser() throws UserNotFoundException;

    User getUserById(Long userId) throws UserNotFoundException;

    User updateUserById(Long userId, UserDTO updateUser) throws UserNotFoundException;

    boolean userLogin(UserLoginDTO userLogin) throws UserNotFoundException;

    boolean deleteUserById(Long userId) throws UserNotFoundException;
}
