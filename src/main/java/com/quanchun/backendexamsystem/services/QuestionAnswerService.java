package com.quanchun.backendexamsystem.services;

import com.quanchun.backendexamsystem.entities.QuestionAnswer;
import com.quanchun.backendexamsystem.error.OptionAnswerNotFoundException;
import com.quanchun.backendexamsystem.models.OptionAnswerDTO;
import com.quanchun.backendexamsystem.models.QuestionAnswerDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionAnswerService {


    OptionAnswerDTO toOptionAnswerDTO(QuestionAnswer questionAnswer);



    QuestionAnswer toQuestionAnswer(OptionAnswerDTO optionAnswerDTO, boolean status);

    @Transactional
    QuestionAnswer addQuestionAnswer(OptionAnswerDTO optionAnswerDTO);

    QuestionAnswer getQuestionAnswer(int id) throws OptionAnswerNotFoundException;

    QuestionAnswer updateOptionAnswer(int id, OptionAnswerDTO optionAnswerDTO) throws OptionAnswerNotFoundException;


    boolean deletedQuestionAnswer(int id);
}
