package com.quanchun.backendexamsystem.services;

import com.quanchun.backendexamsystem.entities.Quizz;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;
import com.quanchun.backendexamsystem.models.QuestionDTO;
import com.quanchun.backendexamsystem.models.QuizzDTO;
import com.quanchun.backendexamsystem.models.UserDTO;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface QuizzService {
    Quizz addQuizz(QuizzDTO theQuizz);

    Quizz addQuestions(int id, List<QuestionDTO> questions) throws QuizzNotFoundException;


    Quizz findQuizzById(int id);

    List<Quizz> getAllQuizzes();

    Quizz updateQuizzById(int id, QuizzDTO updatedQuizz) throws QuizzNotFoundException;

    List<Quizz> getQuizzesByDifficulty(int difficulty);
    List<Quizz> getQuizzesByHostId(int hostId);

    List<UserDTO> getUsersByQuizzesId(int quizzId) throws QuizzNotFoundException;

    Page<QuizzDTO> getQuizzesWithSortingAndPagingAndFilter(String field, String order, Integer page, Integer pageSize, Integer difficulty, String preDateOption);
    void deleteById(int theId) throws QuizzNotFoundException;



}
