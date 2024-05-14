package com.quanchun.backendexamsystem.controllers;

import com.quanchun.backendexamsystem.entities.RegisterQuizz;
import com.quanchun.backendexamsystem.entities.Role;
import com.quanchun.backendexamsystem.entities.User;
import com.quanchun.backendexamsystem.error.*;
import com.quanchun.backendexamsystem.models.*;
import com.quanchun.backendexamsystem.models.requests.ParticipantAttemptRequestDTO;
import com.quanchun.backendexamsystem.models.responses.ParticipantAttemptResponseDTO;
import com.quanchun.backendexamsystem.models.responses.ResponseRegisterQuizzDTO;
import com.quanchun.backendexamsystem.services.RegisterQuizzService;
import com.quanchun.backendexamsystem.services.RoleService;
import com.quanchun.backendexamsystem.services.StatisticsService;
import com.quanchun.backendexamsystem.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserControllers {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RegisterQuizzService registerService;

    @Autowired
    private StatisticsService statisticsService;

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody @Valid UserLoginDTO userLogin) throws UserNotFoundException{
        String responseBody = "Failed to login";
        if (userService.userLogin(userLogin)){
            responseBody = "Successfully to login";
        }
        return ResponseEntity.ok(responseBody);
    }

//    test ok
    @PostMapping("users/{userId}/register-quizz/{quizzId}")
    public ResponseEntity<ResponseRegisterQuizzDTO> register(@PathVariable("userId")long userId, @PathVariable(
            "quizzId") int quizzId) throws UserNotFoundException, QuizzNotFoundException {
        ResponseRegisterQuizzDTO registerQuizz = registerService.registerQuizz(userId, quizzId);
        return new ResponseEntity<>(registerQuizz, HttpStatus.OK);
    }

//    test ok
    @PostMapping("/add-role")
    public ResponseEntity<String> addNewRole(@RequestBody @Valid RoleDTO newRole) throws RoleExistsException {
        Role role = roleService.addNewRole(newRole);
        String responseBody = String.format("Cannot add %s Role", newRole.getName());
        if(role!=null){
            responseBody = String.format("Successfully added %s Role", role.getName());
        }
        return ResponseEntity.ok(responseBody);
    }

//    test ok
    @PostMapping("/add-user")
    public ResponseEntity<String> addNewUser(@RequestBody @Valid UserDTO newUser) throws RoleNotFoundException, UserExistsException {
        User user = userService.addNewUser(newUser);
        String responseBody = "Cannot add new User";
        if(user!=null){
            responseBody = "Successfully add new User";
        }
        return ResponseEntity.ok(responseBody);
    }

    // test ok
    @PutMapping("/users/{id}/update")
    public ResponseEntity<String> updateUserById(@PathVariable("id") Long userId, @RequestBody UserDTO updateUser) throws UserNotFoundException{
        User user = userService.updateUserById(userId, updateUser);
        String responseBody = "Cannot update User";
        if(user!=null){
            responseBody = "Successfully updated User";
        }
        return ResponseEntity.ok(responseBody);
    }

    // test ok
    @GetMapping("/users/{id}")
    public ResponseEntity<User> fetchUserById(@PathVariable("id") Long userId) throws UserNotFoundException {
        return new ResponseEntity<>( userService.getUserById(userId), HttpStatus.OK);
    }

    // test ok
    @GetMapping("/users")
    public ResponseEntity<List<User>> fetchAllUsers() throws UserNotFoundException{
        return new ResponseEntity<>( userService.getAllUser(), HttpStatus.OK);
    }

    // test ok, but we need to map a dto not to show too much information
    @GetMapping("/users/{userId}/quizzes")
    public ResponseEntity<List<QuizzDTO>> getQuizzes4StudentId(@PathVariable("userId") int userId) throws UserNotFoundException {
        return new ResponseEntity<>(userService.getQuizzesByUserId((long) userId), HttpStatus.OK);
    }

    // test ok
    @DeleteMapping("/users/{id}/delete")
    public ResponseEntity<User> deleteUserById(@PathVariable("id") Long userId) throws UserNotFoundException {
        return new ResponseEntity<>( userService.deleteUserById(userId), HttpStatus.OK);
    }


    @PostMapping("/doing-quizz/{registerQuizzId}")
    public ResponseEntity<ParticipantAttemptResponseDTO> userStartQuizz(@PathVariable("registerQuizzId") int registerQuizzId) throws RegisterQuizzNotFoundException {
        return new ResponseEntity<>(registerService.createParticipantAttempt(registerQuizzId), HttpStatus.OK);
    }

    //test ok
    @PostMapping("/submit-quizz/{participantAttemptId}")
    public ResponseEntity<ParticipantAttemptResponseDTO> userSubmitQuizz(@PathVariable("participantAttemptId") int participantAttemptId, @RequestBody ParticipantAttemptRequestDTO participantAttemptRequestDTO) throws  ParticipantAttemptNotFoundException {
        return new ResponseEntity<>(registerService.submitParticipantAttempt(participantAttemptId, participantAttemptRequestDTO), HttpStatus.OK);
    }

    @GetMapping("users/{userId}/statistics")
    public ResponseEntity<List<SubmittedUserDTO>> getUserStatistic(@PathVariable("userId") int userId) throws UserNotFoundException {
        return new ResponseEntity<>(statisticsService.getUserSubmittedResult(userId), HttpStatus.OK);
    }



}
