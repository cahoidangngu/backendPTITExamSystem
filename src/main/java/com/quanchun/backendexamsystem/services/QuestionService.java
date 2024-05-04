package com.quanchun.backendexamsystem.services;

import com.quanchun.backendexamsystem.entities.Question;
import com.quanchun.backendexamsystem.entities.QuestionAnswer;
import com.quanchun.backendexamsystem.entities.User;
import com.quanchun.backendexamsystem.error.QuestionNotFoundException;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;
import com.quanchun.backendexamsystem.models.QuestionAnswerDTO;
import com.quanchun.backendexamsystem.models.QuestionDTO;
import com.quanchun.backendexamsystem.models.UserDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QuestionService {
    Question addQuestion(QuestionDTO theQuestion);
    Question addQuestion2Quizz(int quizzId, Question theQuestion) throws QuizzNotFoundException;
    List<QuestionAnswer> updateAnswer(int id, List<QuestionAnswerDTO> answerDTOS);

    Question updateQuestionById(int id, QuestionDTO theQuestion) throws QuestionNotFoundException;

    Question findQuestionById(int id);

    Question deleteQuestionById(int id) throws QuestionNotFoundException;

    List<Question> getQuestionsByCategory(String category);

    List<Question> getAllQuestions();

    List<Question> getQuestionsByQuizzId(int quizzId) throws QuizzNotFoundException;

    Page<Question> getAllQuestionWithSortingAndPaginationAndFilter(String category, Integer difficulty,String field, Integer page, Integer pageSize, String order);


}
