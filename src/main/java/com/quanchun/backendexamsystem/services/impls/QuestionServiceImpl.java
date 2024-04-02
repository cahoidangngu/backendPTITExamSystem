package com.quanchun.backendexamsystem.services.impls;

import com.quanchun.backendexamsystem.entities.Question;
import com.quanchun.backendexamsystem.entities.QuestionAnswer;
import com.quanchun.backendexamsystem.entities.Quizz;
import com.quanchun.backendexamsystem.error.QuestionNotFoundException;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.models.QuestionAnswerDTO;
import com.quanchun.backendexamsystem.models.QuestionDTO;
import com.quanchun.backendexamsystem.repositories.QuestionRepository;
import com.quanchun.backendexamsystem.repositories.QuizzRepository;
import com.quanchun.backendexamsystem.services.QuestionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/*
*  Nên thêm category cho ngân hàng câu hỏi rồi cho filter
* */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuizzRepository quizzRepository;
    @Autowired
    EntityManager entityManager;
    @Override
    @Transactional
    public Question addQuestion(QuestionDTO theQuestion) {
        System.out.println(theQuestion.toString());
        Question question = Question.builder()
                .questionContent(theQuestion.getQuestionContent())
                .difficulty(theQuestion.getDifficulty())
                .multianswer(theQuestion.getMultianswer())
                .category(theQuestion.getCategory())
                .build();
        for(QuestionAnswerDTO answer : theQuestion.getQuestionAnswers())
        {
            QuestionAnswer answer2Add = QuestionAnswer.builder()
                    .isCorrect(answer.isCorrect())
                    .answer(answer.getAnswer())
                    .build();
            question.addQuestionAnswer(answer2Add);
        }
        questionRepository.save(question);
        return question;
    }

    @Override
    public Question addQuestion2Quizz(int quizzId, Question theQuestion) throws QuizzNotFoundException {
        Question question = quizzRepository.findById(quizzId).map(quizz -> {
            int questionId = theQuestion.getId();

            // if question already existed
            if(questionId != 0)
            {
                try {
                    Question _question = questionRepository.findById(questionId)
                            .orElseThrow(() -> new QuestionNotFoundException("Question with id " + questionId + " not found!"));
                    quizz.addQuestion(_question);
                    quizzRepository.save(quizz);
                    return _question;
                } catch (QuestionNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            // add and create new question
            quizz.addQuestion(theQuestion);
            return questionRepository.save(theQuestion);
        }).orElseThrow(() -> new QuizzNotFoundException("Quizz with id " + quizzId + " not found!"));

        return question;
    }

    @Override
    public List<QuestionAnswer> updateAnswer(int id, List<QuestionAnswerDTO> answerDTOS) {
        Optional<Question> foundedQuestion = questionRepository.findById(id);
        if(foundedQuestion.isEmpty())
        {
            // throw exception
        }
        Question question = foundedQuestion.get();
        if(Objects.nonNull(answerDTOS))
        {
            question.getQuestionAnswers().clear();
            for(QuestionAnswerDTO answerDTO: answerDTOS)
            {
                QuestionAnswer answer = QuestionAnswer.builder()
                        .answer(answerDTO.getAnswer())
                        .isCorrect(answerDTO.isCorrect())
                        .build();
                question.getQuestionAnswers().add(answer);
            }
        }
        return null;
    }

    @Override
    @Transactional
    public Question updateQuestionById(int id, QuestionDTO theQuestion) throws QuestionNotFoundException {
        Optional<Question> foundedQuestion = questionRepository.findById(id);
        if(foundedQuestion.isEmpty())
        {
            throw new QuestionNotFoundException("Question with id " + id + "not found!");
        }
        Question question = foundedQuestion.get();
        if(Objects.nonNull(theQuestion.getQuestionContent()))
        {
            question.setQuestionContent(theQuestion.getQuestionContent());
        }
        if(Objects.nonNull(theQuestion.getCategory()))
        {
            question.setCategory(theQuestion.getCategory());
        }
        if(Objects.nonNull(theQuestion.getDifficulty()))
        {
            question.setDifficulty(theQuestion.getDifficulty());
        }
        // Multianswer: restore data for answer's quantity
        if(Objects.nonNull(theQuestion.getMultianswer()))
        {
            question.setMultianswer(theQuestion.getMultianswer());
        }
        if(Objects.nonNull(theQuestion.getQuestionAnswers()))
        {
            List<QuestionAnswer> originalList = question.getQuestionAnswers();
            List<QuestionAnswerDTO> updatedList = theQuestion.getQuestionAnswers();
            // if update size is smaller, it is ok but what if the update size is larger
            for(int i = 0; i < Math.max(updatedList.size(), originalList.size()); i++)
            {
                // handle case that user add more answers
                if(i >= originalList.size())
                {
                    question.addQuestionAnswer(QuestionAnswer.builder()
                                    .answer(updatedList.get(i).getAnswer())
                                    .isCorrect(updatedList.get(i).isCorrect())
                            .build());
                    continue;
                }
                // handle case that user remove some previous answers
                if(i >= updatedList.size())
                {
                    question.deleteQuestionAnswer(originalList.get(i));
                    continue;
                }
                QuestionAnswer answer = originalList.get(i);
                QuestionAnswerDTO dto = updatedList.get(i);
                answer.setAnswer(dto.getAnswer());
                answer.setCorrect(dto.isCorrect());

            }
        }
        return question;
    }

    @Override
    public Question findQuestionById(int id) {
        Optional<Question> result = questionRepository.findById(id);

        Question question = null;
        if(result.isPresent())
        {
            question = result.get();
        }else {
            // Exception handle later
            throw new RuntimeException("Did not find employee id - " + id);
        }
        return question;

    }

    @Override
    @Transactional
    public Question deleteQuestionById(int id) throws QuestionNotFoundException {
        Optional<Question> deletedQuestion = questionRepository.findById(id);
        if(deletedQuestion.isEmpty())
        {
            throw new QuestionNotFoundException("Question with id " + id + "not found!");
        }
        Question question = deletedQuestion.get();
        questionRepository.deleteById(id);
        return question;
    }

    @Override
    public List<Question> getQuestionsByCategory(String category) {
        TypedQuery<Question> query = entityManager.createQuery("select a from Question a where a.category = :data", Question.class);
        query.setParameter("data", category);
        List<Question> result = query.getResultList();
        return result;
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public List<Question> getQuestionsByQuizzId(int quizzId) throws QuizzNotFoundException {
        Optional<Quizz> quizz = quizzRepository.findById(quizzId);
        if(quizz.isEmpty())
        {
            throw new QuizzNotFoundException("Quizz with id " + quizzId + "not found");
        }
        List<Question> questions = questionRepository.findQuestionsByQuizzesId(quizzId);
        return questions;
    }
}
