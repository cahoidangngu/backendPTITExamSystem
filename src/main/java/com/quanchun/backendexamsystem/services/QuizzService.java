package com.quanchun.backendexamsystem.services;

import com.quanchun.backendexamsystem.entities.Question;
import com.quanchun.backendexamsystem.entities.Quizz;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;
import com.quanchun.backendexamsystem.models.QuestionDTO;
import com.quanchun.backendexamsystem.models.QuizzDTO;

import java.util.List;
import java.util.Set;

public interface QuizzService {
    Quizz addQuizz(QuizzDTO theQuizz);

    Quizz addQuestions(int id, List<QuestionDTO> questions) throws QuizzNotFoundException;

    Quizz findQuizzById(int id);

    List<Quizz> getAllQuizzes();

    Quizz updateQuizzById(int id, QuizzDTO updatedQuizz) throws QuizzNotFoundException;

    List<Quizz> getQuizzesByDifficulty(int difficulty);
    List<Quizz> getQuizzesByHostId(int hostId);

    Set<Quizz> getQuizzByUserId(int userId) throws UserNotFoundException;
    void deleteById(int theId) throws QuizzNotFoundException;



}
