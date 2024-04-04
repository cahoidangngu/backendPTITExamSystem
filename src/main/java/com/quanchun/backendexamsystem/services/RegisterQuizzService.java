package com.quanchun.backendexamsystem.services;

import com.quanchun.backendexamsystem.entities.Quizz;
import com.quanchun.backendexamsystem.entities.RegisterQuizz;
import com.quanchun.backendexamsystem.entities.User;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.error.RegisterQuizzNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;

import java.util.List;

public interface RegisterQuizzService {
    RegisterQuizz registerQuizz(Long userId, int quizzId) throws QuizzNotFoundException, UserNotFoundException;
    RegisterQuizz findByRegisterId(int id) throws RegisterQuizzNotFoundException;

    RegisterQuizz deleteByRegisterId(int id);

    List<Quizz> getQuizzesByUserId(Long userId) throws UserNotFoundException;

    List<User> getUsersByQuizzesId(int quizzId) throws QuizzNotFoundException;
}
