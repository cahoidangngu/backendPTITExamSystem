package com.quanchun.backendexamsystem.services.impls;

import com.quanchun.backendexamsystem.entities.QuestionAnswer;
import com.quanchun.backendexamsystem.models.QuestionAnswerDTO;
import com.quanchun.backendexamsystem.repositories.QuestionAnswerRepository;
import com.quanchun.backendexamsystem.services.QuestionAnswerService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

public class QuestionAnswerServiceImpl implements QuestionAnswerService {
    @Autowired
    QuestionAnswerRepository answerRepository;
    @Autowired
    EntityManager entityManager;
    @Override
    public List<QuestionAnswer> getAnswersByQuizzId(int quizzId) {
        TypedQuery<QuestionAnswer> query = entityManager.createQuery(
                "select a from QuestionAnswer a where a.quId = :data", QuestionAnswer.class
        );
        query.setParameter("data", quizzId);

        return query.getResultList();
    }

    @Override
    public QuestionAnswer getAnswerByQuizzAndAnswerId(int quizzId, int answerId) {
        TypedQuery<QuestionAnswer> query = entityManager.createQuery(
                "select a from QuestionAnswer a where a.quId = :quizzId and a.qaId = :answerId", QuestionAnswer.class
        );
        query.setParameter("quizzId", quizzId);
        query.setParameter("answerId", answerId);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public QuestionAnswer addQuestionAnswer(QuestionAnswer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public QuestionAnswer updatedQuestionAnswer(int quizzId, int answerId, QuestionAnswerDTO answerDTO) {
        QuestionAnswer foundQuestion = getAnswerByQuizzAndAnswerId(quizzId, answerId);
        if(foundQuestion == null)
        {
            // throw exception
        }
        if(Objects.nonNull(answerDTO.getAnswer()))
        {
            foundQuestion.setAnswer(answerDTO.getAnswer());
        }
//        if(Objects.nonNull(answerDTO.getQuestion()))
//        {
//            foundQuestion.setQuestion(answerDTO.getQuestion());
//        }
        return foundQuestion;
    }

    @Override
    public QuestionAnswer deletedQuestionAnswer(int quizzId, int answerId) {
        return null;
    }
}
