package com.quanchun.backendexamsystem.controllers;

import com.quanchun.backendexamsystem.entities.Role;
import com.quanchun.backendexamsystem.entities.User;
import com.quanchun.backendexamsystem.error.RoleNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;
import com.quanchun.backendexamsystem.models.RoleDTO;
import com.quanchun.backendexamsystem.models.UserDTO;
import com.quanchun.backendexamsystem.models.UserLoginDTO;
import com.quanchun.backendexamsystem.services.RoleService;
import com.quanchun.backendexamsystem.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserControllers {
    @Autowired
    private UserService userService;




    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody @Valid UserLoginDTO userLogin) throws UserNotFoundException{
        String responseBody = "Failed to login";
        if (userService.userLogin(userLogin)){
            responseBody = "Successfully to login";
        }
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/addNewUser")
    public ResponseEntity<String> addNewUser(@RequestBody @Valid UserDTO newUser) throws RoleNotFoundException {
        User user = userService.addNewUser(newUser);
        String responseBody = "Cannot add new User";
        if(user!=null){
            responseBody = "Successfully add new User";
        }
        return ResponseEntity.ok(responseBody);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable("id") Long userId, @RequestBody UserDTO updateUser) throws UserNotFoundException{
        User user = userService.updateUserById(userId, updateUser);
        String responseBody = "Cannot update User";
        if(user!=null){
            responseBody = "Successfully updated User";
        }
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> fetchUserById(@PathVariable("id") Long userId) throws UserNotFoundException {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long userId) throws UserNotFoundException {
        String responseBody = "Cannot delete User";
        if(userService.deleteUserById(userId)){
            responseBody = "Successfully delete User";
        }
        return ResponseEntity.ok(responseBody);
    }


    @GetMapping("/list")
    public List<User> fetchAllUsers() throws UserNotFoundException{
        return userService.getAllUser();
    }
}
