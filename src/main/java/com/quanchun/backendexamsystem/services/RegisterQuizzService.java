package com.quanchun.backendexamsystem.services;

import com.quanchun.backendexamsystem.entities.Quizz;
import com.quanchun.backendexamsystem.entities.RegisterQuizz;
import com.quanchun.backendexamsystem.entities.User;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.error.RegisterQuizzNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;
import com.quanchun.backendexamsystem.models.RegisterQuizzDTO;
import com.quanchun.backendexamsystem.models.SubmitQuizzDTO;
import com.quanchun.backendexamsystem.models.UserAnswerDTO;

import java.util.List;

public interface RegisterQuizzService {
    RegisterQuizzDTO registerQuizz(Long userId, int quizzId) throws QuizzNotFoundException, UserNotFoundException;
    RegisterQuizz findByRegisterId(int id) throws RegisterQuizzNotFoundException;

    RegisterQuizz deleteByRegisterId(int id);

    RegisterQuizz submitQuizz(int id, SubmitQuizzDTO submitQuizzDTO) throws RegisterQuizzNotFoundException;
}
