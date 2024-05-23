package com.quanchun.backendexamsystem.services.impls;

import com.quanchun.backendexamsystem.entities.QuestionAnswer;
import com.quanchun.backendexamsystem.error.OptionAnswerNotFoundException;
import com.quanchun.backendexamsystem.models.OptionAnswerDTO;
import com.quanchun.backendexamsystem.repositories.QuestionAnswerRepository;
import com.quanchun.backendexamsystem.services.QuestionAnswerService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class QuestionAnswerServiceImpl implements QuestionAnswerService {
    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;

    private Logger logger = LoggerFactory.getLogger(QuestionAnswer.class);

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
    public QuestionAnswer getQuestionAnswer(int id) throws OptionAnswerNotFoundException {
        Optional<QuestionAnswer> result = questionAnswerRepository.findById(id);

        if (result.isPresent()) {
            return result.get();
        } else {
            throw new OptionAnswerNotFoundException("Did not find option answer with id - " + id);
        }
    }

    @Transactional
    @Override
    public QuestionAnswer updateOptionAnswer(int id, OptionAnswerDTO updateOptionAnswerDTO) throws OptionAnswerNotFoundException {
        QuestionAnswer questionAnswer = getQuestionAnswer(id);
        questionAnswer.setAnswer(updateOptionAnswerDTO.getContent());
        // QuestionAnswer phải được đính kèm vào EntityManager để cập nhật tự động
        return questionAnswerRepository.save(questionAnswer);
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
