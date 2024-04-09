package com.quanchun.backendexamsystem.controllers;

import com.quanchun.backendexamsystem.entities.Quizz;
import com.quanchun.backendexamsystem.entities.RegisterQuizz;
import com.quanchun.backendexamsystem.entities.Role;
import com.quanchun.backendexamsystem.entities.User;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.error.RegisterQuizzNotFoundException;
import com.quanchun.backendexamsystem.error.RoleNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;
import com.quanchun.backendexamsystem.models.*;
import com.quanchun.backendexamsystem.services.RegisterQuizzService;
import com.quanchun.backendexamsystem.services.RoleService;
import com.quanchun.backendexamsystem.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserControllers {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RegisterQuizzService registerService;

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody @Valid UserLoginDTO userLogin) throws UserNotFoundException{
        String responseBody = "Failed to login";
        if (userService.userLogin(userLogin)){
            responseBody = "Successfully to login";
        }
        return ResponseEntity.ok(responseBody);
    }

//    test ok
    @PostMapping("/{userId}/register-quizz/{quizzId}")
    public ResponseEntity<RegisterQuizzDTO> register(@PathVariable("userId") Long userId, @PathVariable("quizzId") int quizzId) throws UserNotFoundException, QuizzNotFoundException {
        RegisterQuizzDTO registerQuizz = registerService.registerQuizz(userId, quizzId);
        return new ResponseEntity<>(registerQuizz, HttpStatus.OK);
    }

//    test ok
    @PostMapping("/addNewRole")
    public ResponseEntity<String> addNewRole(@RequestBody @Valid RoleDTO newRole){
        Role role = roleService.addNewRole(newRole);
        String responseBody = String.format("Cannot add %s Role", newRole.getName());
        if(role!=null){
            responseBody = String.format("Successfully added %s Role", role.getName());
        }
        return ResponseEntity.ok(responseBody);
    }

//    test ok
    @PostMapping("/addNewUser")
    public ResponseEntity<String> addNewUser(@RequestBody @Valid UserDTO newUser) throws RoleNotFoundException {
        User user = userService.addNewUser(newUser);
        String responseBody = "Cannot add new User";
        if(user!=null){
            responseBody = "Successfully add new User";
        }
        return ResponseEntity.ok(responseBody);
    }

    // test ok
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable("id") Long userId, @RequestBody UserDTO updateUser) throws UserNotFoundException{
        User user = userService.updateUserById(userId, updateUser);
        String responseBody = "Cannot update User";
        if(user!=null){
            responseBody = "Successfully updated User";
        }
        return ResponseEntity.ok(responseBody);
    }

    // test ok
    @GetMapping("/{id}")
    public ResponseEntity<User> fetchUserById(@PathVariable("id") Long userId) throws UserNotFoundException {
        return new ResponseEntity<>( userService.getUserById(userId), HttpStatus.OK);
    }

    // test ok
    @GetMapping("/list")
    public ResponseEntity<List<User>> fetchAllUsers() throws UserNotFoundException{
        return new ResponseEntity<>( userService.getAllUser(), HttpStatus.OK);
    }

    // test ok but we need to mapper a dto not to show too much information
    @GetMapping("/{userId}/quizzes")
    public ResponseEntity<List<QuizzDTO>> getQuizzes4StudentId(@PathVariable("userId") int userId) throws UserNotFoundException {
        return new ResponseEntity<>(userService.getQuizzesByUserId((long) userId), HttpStatus.OK);
    }

    // test ok
    @DeleteMapping("delete/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable("id") Long userId) throws UserNotFoundException {
        return new ResponseEntity<>( userService.deleteUserById(userId), HttpStatus.OK);
    }

    //test ok
    @PostMapping("/submit-quizz/{id}")
    public ResponseEntity<RegisterQuizz> usersubmitQuizzr(@PathVariable("id") int id, @RequestBody SubmitQuizzDTO submitQuizzDTO) throws RegisterQuizzNotFoundException {
        return new ResponseEntity<>(registerService.submitQuizz(id, submitQuizzDTO), HttpStatus.OK);
    }



}
