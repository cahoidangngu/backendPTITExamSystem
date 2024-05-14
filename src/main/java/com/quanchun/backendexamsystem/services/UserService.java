package com.quanchun.backendexamsystem.services;

import com.quanchun.backendexamsystem.entities.Quizz;
import com.quanchun.backendexamsystem.entities.RegisterQuizz;
import com.quanchun.backendexamsystem.entities.User;
import com.quanchun.backendexamsystem.error.RoleNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;
import com.quanchun.backendexamsystem.models.QuizzDTO;
import com.quanchun.backendexamsystem.models.UserDTO;
import com.quanchun.backendexamsystem.models.UserLoginDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface UserService {
    User addNewUser(UserDTO newUser) throws RoleNotFoundException;

    List<User> getAllUser() throws UserNotFoundException;

    User getUserById(Long userId) throws UserNotFoundException;
    List<QuizzDTO> getQuizzesByUserId(Long userId) throws UserNotFoundException;
    User getUserByUsername(String username) throws UserNotFoundException;

    User updateUserById(Long userId, UserDTO updateUser) throws UserNotFoundException;

    boolean userLogin(UserLoginDTO userLogin) throws UserNotFoundException;

    User deleteUserById(Long userId) throws UserNotFoundException;
    List<User> getUserWithSorting(String field, Sort.Direction direction);
    Page<User> getUserWithPagination(Integer page, Integer pageSize);
    Page<User> getUserWithSortAndPagination(String field, Integer page, Integer pageSize);
}
