package com.quanchun.backendexamsystem.services;

import com.quanchun.backendexamsystem.entities.QuestionAnswer;
import com.quanchun.backendexamsystem.models.QuestionAnswerDTO;

import java.util.List;

public interface QuestionAnswerService {
    List<QuestionAnswer> getAnswersByQuizzId(int quizzId);

    QuestionAnswer getAnswerByQuizzAndAnswerId(int quizzId, int answerId);

    QuestionAnswer addQuestionAnswer(QuestionAnswer answer);

    QuestionAnswer updatedQuestionAnswer(int quizzId, int answerId, QuestionAnswerDTO answerDTO);

    QuestionAnswer deletedQuestionAnswer(int quizzId, int answerId);
}
