package com.quanchun.backendexamsystem.services;

import com.quanchun.backendexamsystem.entities.Quizz;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.models.QuestionDTO;
import com.quanchun.backendexamsystem.models.UserDTO;
import com.quanchun.backendexamsystem.models.responses.QuizDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuizzService {


    @Transactional
    Quizz addQuizz(QuizDTO quizDTO);

    Quizz addQuestions(int id, List<QuestionDTO> questions) throws QuizzNotFoundException;


    Quizz findQuizzById(int id) throws QuizzNotFoundException;

    List<QuizDTO> getAllQuizzes();

    QuizDTO updateQuizzById(int id, QuizDTO updatedQuizz) throws QuizzNotFoundException;

    List<Quizz> getQuizzesByHostId(int hostId);

    List<UserDTO> getUsersByQuizzesId(int quizzId) throws QuizzNotFoundException;

    Quizz toQuizz(QuizDTO quizDTO, boolean status);

    QuizDTO toQuizDTO (Quizz quizz);

    void deleteById(int id) throws QuizzNotFoundException;



}
