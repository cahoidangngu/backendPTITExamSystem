package com.quanchun.backendexamsystem.services.impls;

import com.quanchun.backendexamsystem.entities.QuestionAnswer;
import com.quanchun.backendexamsystem.error.OptionAnswerNotFoundException;
import com.quanchun.backendexamsystem.models.OptionAnswerDTO;
import com.quanchun.backendexamsystem.repositories.QuestionAnswerRepository;
import com.quanchun.backendexamsystem.services.QuestionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class QuestionAnswerServiceImpl implements QuestionAnswerService {
    @Autowired
    QuestionAnswerRepository questionAnswerRepository;


    @Override
    public OptionAnswerDTO toOptionAnswerDTO(QuestionAnswer questionAnswer) {
        return OptionAnswerDTO.builder().id(questionAnswer.getQaId()).content(questionAnswer.getAnswer()).build();
    }

    @Override
    public QuestionAnswer toQuestionAnswer(OptionAnswerDTO optionAnswerDTO, boolean status) {
        if (status)
            return QuestionAnswer.builder().answer(optionAnswerDTO.getContent()).build();
        return QuestionAnswer.builder().qaId(optionAnswerDTO.getId()).answer(optionAnswerDTO.getContent()).build();
    }


    @Override
    @Transactional
    public QuestionAnswer addQuestionAnswer(OptionAnswerDTO optionAnswerDTO) {
        return questionAnswerRepository.save(toQuestionAnswer(optionAnswerDTO, true));
    }

    @Override
    public QuestionAnswer getQuestionAnswer(int id) {
        return questionAnswerRepository.findById(id).orElse(null);
    }

    @Override
    public QuestionAnswer updateOptionAnswer(int id, OptionAnswerDTO optionAnswerDTO) throws OptionAnswerNotFoundException {
        if (getQuestionAnswer(id) == null) {
            throw new OptionAnswerNotFoundException("Not found option answer with id: " + id);
        }
        QuestionAnswer updateQuestionAnswer = toQuestionAnswer(optionAnswerDTO, false);
        return questionAnswerRepository.save(updateQuestionAnswer);
    }

    @Override
    public boolean deletedQuestionAnswer(int id) {
        if (questionAnswerRepository.existsById(id)) {
            questionAnswerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
