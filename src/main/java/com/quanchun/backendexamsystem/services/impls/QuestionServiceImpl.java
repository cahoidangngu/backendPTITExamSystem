package com.quanchun.backendexamsystem.services.impls;

import com.quanchun.backendexamsystem.entities.Question;
import com.quanchun.backendexamsystem.entities.QuestionAnswer;
import com.quanchun.backendexamsystem.entities.Quizz;
import com.quanchun.backendexamsystem.error.OptionAnswerNotFoundException;
import com.quanchun.backendexamsystem.error.QuestionNotFoundException;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.models.OptionAnswerDTO;
import com.quanchun.backendexamsystem.models.QuestionDTO;
import com.quanchun.backendexamsystem.repositories.QuestionAnswerRepository;
import com.quanchun.backendexamsystem.repositories.QuestionRepository;
import com.quanchun.backendexamsystem.repositories.QuizzRepository;
import com.quanchun.backendexamsystem.services.QuestionAnswerService;
import com.quanchun.backendexamsystem.services.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/*
 *  Nên thêm category cho ngân hàng câu hỏi rồi cho filter
 * */
@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuizzRepository quizzRepository;

    @Autowired
    private QuestionAnswerService questionAnswerService;

    Logger logger = LoggerFactory.getLogger(QuestionService.class);

    @Override
    public QuestionDTO toQuestionDTO(Question question) {
        List<OptionAnswerDTO> optionAnswerDTOList = new ArrayList<>();
        question.getQuestionAnswers().forEach(questionAnswer ->
                                                      optionAnswerDTOList.add(
                                                              questionAnswerService.toOptionAnswerDTO(questionAnswer)));

        return QuestionDTO.builder()
                          .id(question.getId())
                          .question(question.getQuestionContent())
                          .multianswer(question.getMultianswer())
                          .category(question.getCategory())
                          .difficulty(question.getDifficulty())
                          .optionAnswers(optionAnswerDTOList).answer(question.getCorrectedAnswer()).build();
    }

    @Override
    public Question toQuestion(QuestionDTO questionDTO, boolean status) {
        List<QuestionAnswer> questionAnswerList = new ArrayList<>();
        questionDTO.getOptionAnswers().forEach(optionAnswerDTO -> {
            try {
                questionAnswerService.getQuestionAnswer(optionAnswerDTO.getId());
                questionAnswerList.add(questionAnswerService.toQuestionAnswer(optionAnswerDTO, true));

            } catch (OptionAnswerNotFoundException e) {
                questionAnswerList.add(questionAnswerService.toQuestionAnswer(optionAnswerDTO, false));

            }
        });

        if (status)
            return Question.builder()
                           .questionContent(questionDTO.getQuestion())
                           .category(questionDTO.getCategory())
                           .difficulty(questionDTO.getDifficulty())
                           .multianswer(questionDTO.getMultianswer())
                           .questionAnswers(questionAnswerList)
                           .correctedAnswer(questionDTO.getAnswer())
                           .build();
        return Question.builder()
                       .id(questionDTO.getId())
                       .questionContent(questionDTO.getQuestion())
                       .category(questionDTO.getCategory())
                       .difficulty(questionDTO.getDifficulty())
                       .multianswer(questionDTO.getMultianswer())
                       .questionAnswers(questionAnswerList)
                       .correctedAnswer(questionDTO.getAnswer())
                       .build();
    }

    @Override
    @Transactional
    public Question addQuestion(QuestionDTO questionDTO) {
        return questionRepository.save(toQuestion(questionDTO, true));
    }

    @Override
    public Question addQuestion2Quizz(int quizzId, Question theQuestion) throws QuizzNotFoundException {
        return null;
    }


    @Override
    @Transactional
    public Question updateQuestionById(int id, QuestionDTO updateQuestion) throws QuestionNotFoundException {
        Question question = findQuestionById(id);
        question.setCorrectedAnswer(updateQuestion.getAnswer());
        question.setDifficulty(updateQuestion.getDifficulty());
        question.setMultianswer(updateQuestion.getOptionAnswers().size());

        if (Objects.nonNull(updateQuestion.getQuestion())) {
            question.setQuestionContent(updateQuestion.getQuestion());
        }
        if (Objects.nonNull(updateQuestion.getCategory())) {
            question.setCategory(updateQuestion.getCategory());
        }


        if (Objects.nonNull(updateQuestion.getOptionAnswers())) {
            List<QuestionAnswer> updateQuestionOptionAnswers = new ArrayList<>();
            logger.info(updateQuestion.getOptionAnswers().toString());
            updateQuestion.getOptionAnswers().forEach(optionAnswerDTO -> {
                try {
                    updateQuestionOptionAnswers.add(
                            questionAnswerService.updateOptionAnswer(optionAnswerDTO.getId(),
                                                                     optionAnswerDTO));

                } catch (OptionAnswerNotFoundException e) {
                    QuestionAnswer newQuestionAnswer = questionAnswerService.addQuestionAnswer(optionAnswerDTO);
                    updateQuestionOptionAnswers.add(newQuestionAnswer);
                }

            });
            question.setQuestionAnswers(updateQuestionOptionAnswers);
            logger.info(updateQuestionOptionAnswers.toString());
        }

        return questionRepository.save(question);
    }

    @Override
    public Question findQuestionById(int id) throws QuestionNotFoundException {
        Optional<Question> result = questionRepository.findById(id);

        Question question = null;
        if (result.isPresent()) {
            question = result.get();
        } else {
            // Exception handle later
            throw new QuestionNotFoundException("Did not find question with id - " + id);
        }
        return question;

    }

    @Override
    @Transactional
    public Question deleteQuestionById(int id) throws QuestionNotFoundException {
        Optional<Question> deletedQuestion = questionRepository.findById(id);
        if (deletedQuestion.isEmpty()) {
            throw new QuestionNotFoundException("Question with id " + id + "not found!");
        }
        Question question = deletedQuestion.get();
        questionRepository.deleteById(id);
        return question;
    }


    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public List<Question> getQuestionsByQuizzId(int quizzId) throws QuizzNotFoundException {
        Optional<Quizz> quizz = quizzRepository.findById(quizzId);
        if (quizz.isEmpty()) {
            throw new QuizzNotFoundException("Quizz with id " + quizzId + "not found");
        }
        List<Question> questions = questionRepository.findQuestionsByQuizzesId(quizzId);
        return questions;
    }

    @Override
    public Page<Question> getAllQuestionWithSortingAndPaginationAndFilter(String category, Integer
            difficulty, String field, Integer page, Integer pageSize, String order) {
        if (page == null) page = UserServiceImpl.DEFAULT_PAGE;
        if (pageSize == null) pageSize = UserServiceImpl.DEFAULT_PAGE_SIZE;
        Pageable pageable;
        if (field != null && order != null) {
            Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Sort sort = Sort.by(direction, field);
            pageable = PageRequest.of(page, pageSize, sort);
        } else {
            pageable = PageRequest.of(page, pageSize);
        }
        Page<Question> questions;
        if (category != null && difficulty != null) {
            questions = questionRepository.findByCategoryAndDifficulty(category, difficulty, pageable);
        } else if (category != null) {
            questions = questionRepository.findByCategory(category, pageable);
        } else if (difficulty != null) {
            questions = questionRepository.findByDifficulty(difficulty, pageable);
        } else {
            questions = questionRepository.findAll(pageable);
        }
        return questions;
    }


}
