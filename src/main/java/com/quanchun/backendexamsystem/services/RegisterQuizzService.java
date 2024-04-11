package com.quanchun.backendexamsystem.services;

import com.quanchun.backendexamsystem.entities.RegisterQuizz;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.error.RegisterQuizzNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;
import com.quanchun.backendexamsystem.models.responses.ResponseRegisterQuizzDTO;
import com.quanchun.backendexamsystem.models.SubmitQuizzDTO;

public interface RegisterQuizzService {
    ResponseRegisterQuizzDTO registerQuizz(Long userId, int quizzId) throws QuizzNotFoundException, UserNotFoundException;
    RegisterQuizz findByRegisterId(int id) throws RegisterQuizzNotFoundException;

    RegisterQuizz deleteByRegisterId(int id);

    ResponseRegisterQuizzDTO submitQuizz(int id, SubmitQuizzDTO submitQuizzDTO) throws RegisterQuizzNotFoundException;
}
