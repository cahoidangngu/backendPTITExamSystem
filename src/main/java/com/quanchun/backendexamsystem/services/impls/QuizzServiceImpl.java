package com.quanchun.backendexamsystem.services.impls;

import com.quanchun.backendexamsystem.entities.*;
import com.quanchun.backendexamsystem.error.QuestionNotFoundException;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.mappers.UserMapper;
import com.quanchun.backendexamsystem.models.QuestionDTO;
import com.quanchun.backendexamsystem.models.UserDTO;
import com.quanchun.backendexamsystem.models.responses.QuizDTO;
import com.quanchun.backendexamsystem.repositories.QuizzRepository;
import com.quanchun.backendexamsystem.services.QuestionAnswerService;
import com.quanchun.backendexamsystem.services.QuestionService;
import com.quanchun.backendexamsystem.services.QuizzService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuizzServiceImpl implements QuizzService {
    @Autowired
    private QuizzRepository quizzRepository;
    @Autowired
    private QuestionAnswerService questionAnswerService;

    @Autowired
    private QuestionService questionService;

    private Logger logger = LoggerFactory.getLogger(QuizzService.class);


    @Override
    public Quizz toQuizz(QuizDTO quizDTO, boolean status) {
        List<Question> questionList = new ArrayList<>();
        quizDTO.getQuestionList().forEach(questionDTO -> {
                                              try {
                                                  if (questionService.findQuestionById(questionDTO.getId()) == null)
                                                      questionList.add(questionService.toQuestion(questionDTO,
                                                                                                  true));
                                                  else
                                                      questionList.add(questionService.toQuestion(questionDTO,
                                                                                                  false));
                                              } catch (QuestionNotFoundException e) {
                                                  throw new RuntimeException(e);
                                              }
                                          }
        );

        if (status)
            return Quizz.builder()
                        .description(quizDTO.getDescription())
                        .endedAt(quizDTO.getEndedAt())
                        .title(quizDTO.getTitle())
                        .type(quizDTO.getType())
                        .difficulty(quizDTO.getDifficulty())
                        .startedAt(quizDTO.getStartedAt())
                        .createdAt(quizDTO.getCreatedAt())
                        .hostId(quizDTO.getHostId())
                        .questions(questionList)
                        .build();
        return Quizz.builder()
                    .id(quizDTO.getId())
                    .description(quizDTO.getDescription())
                    .endedAt(quizDTO.getEndedAt())
                    .title(quizDTO.getTitle())
                    .type(quizDTO.getType())
                    .difficulty(quizDTO.getDifficulty())
                    .startedAt(quizDTO.getStartedAt())
                    .createdAt(quizDTO.getCreatedAt())
                    .hostId(quizDTO.getHostId())
                    .questions(questionList)
                    .build();
    }

    @Override
    public QuizDTO toQuizDTO(Quizz quizz) {
        List<QuestionDTO> questionItemDTOList = new ArrayList<>();

        quizz.getQuestions().forEach((question) -> {
            QuestionDTO questionItemDTO = questionService.toQuestionDTO(question);
            question.getQuestionAnswers()
                    .forEach(questionAnswer -> questionItemDTO.addOptionAnswer(
                            questionAnswerService.toOptionAnswerDTO(questionAnswer)));
            questionItemDTOList.add(questionItemDTO);
        });

        return QuizDTO.builder()
                      .id(quizz.getId())
                      .hostId(quizz.getHostId())
                      .title(quizz.getTitle())
                      .createdAt(quizz.getCreatedAt())
                      .startedAt(quizz.getStartedAt())
                      .endedAt(quizz.getEndedAt())
                      .description(quizz.getDescription())
                      .duration(quizz.getDuration())
                      .type(quizz.getType())
                      .subject(quizz.getSubject())
                      .questionList(questionItemDTOList)
                      .build();
    }


    @Override
    @Transactional
    public Quizz addQuizz(QuizDTO quizDTO) {
        return quizzRepository.save(toQuizz(quizDTO, true));
    }

    // lien quan den question entity
    @Override
    @Transactional
    public Quizz addQuestions(int id, List<QuestionDTO> questions) throws QuizzNotFoundException {
        Optional<Quizz> foundQuizz = quizzRepository.findById(id);
        if (foundQuizz.isEmpty()) {
            throw new QuizzNotFoundException("Quizz with id " + id + "not found");
        }
        Quizz quizz = foundQuizz.get();
        for (QuestionDTO q : questions) {
            Question tmp = Question.builder()
                                   .category(q.getCategory())
                                   .correctedAnswer(q.getAnswer())
                                   //.questionAnswers(q.getQuestionAnswers())
                                   .questionContent(q.getQuestion())
                                   .multianswer(q.getMultianswer())
                                   .difficulty(q.getDifficulty())
                                   .build();
            quizz.addQuestion(tmp);
        }
        return quizz;
    }


    @Override
    public Quizz findQuizzById(int id) throws QuizzNotFoundException {
        Optional<Quizz> theQuizz = quizzRepository.findById(id);
        if (theQuizz.isEmpty()) throw new QuizzNotFoundException("Not found quiz with id:" + id);
        return theQuizz.get();
    }

    @Override
    public List<QuizDTO> getAllQuizzes() {
        List<QuizDTO> quizDTOList = new ArrayList<>();
        quizzRepository.findAll().forEach(quizz -> quizDTOList.add(toQuizDTO(quizz)));
        return quizDTOList;
    }

    @Override
    @Transactional
    public QuizDTO updateQuizzById(int id, QuizDTO updatedQuizz) throws QuizzNotFoundException {
        Quizz quizz = findQuizzById(id);


        quizz.setHostId(updatedQuizz.getHostId());
        quizz.setDuration(updatedQuizz.getDuration());
        quizz.setType(updatedQuizz.getType());
        quizz.setDifficulty(updatedQuizz.getDifficulty());


        if (Objects.nonNull(updatedQuizz.getTitle())) {
            quizz.setTitle(updatedQuizz.getTitle());
        }
        if (Objects.nonNull(updatedQuizz.getDescription())) {
            quizz.setDescription(updatedQuizz.getDescription());
        }

        if (Objects.nonNull(updatedQuizz.getSubject())) {
            quizz.setSubject(updatedQuizz.getSubject());
        }

        if (Objects.nonNull(updatedQuizz.getQuestionList())) {
            List<Question> updateQuestionList = new ArrayList<>();
            updatedQuizz.getQuestionList().forEach(questionDTO -> {
                try {
                    updateQuestionList.add(questionService.updateQuestionById(questionDTO.getId(), questionDTO));
                } catch (QuestionNotFoundException e) {
                    Question newQuestion = questionService.addQuestion(questionDTO);
                    updateQuestionList.add(newQuestion);
                }
            });
            quizz.setQuestions(updateQuestionList);
        }



        return toQuizDTO(quizzRepository.save(quizz));
    }


    @Override
    public List<Quizz> getQuizzesByHostId(int hostId) {
        return quizzRepository.findByHostId(hostId);
    }


    @Override
    @Transactional
    public void deleteById(int theId) throws QuizzNotFoundException {
        Optional<Quizz> optionalQuizz = quizzRepository.findById(theId);
        if (optionalQuizz.isEmpty()) {
            //throw exception
            throw new QuizzNotFoundException("Quizz with id " + theId + "not found");
        }

        quizzRepository.deleteById(theId);

    }

    @Override
    public List<UserDTO> getUsersByQuizzesId(int quizzId) throws QuizzNotFoundException {
        Optional<Quizz> optional = quizzRepository.findById(quizzId);
        if (optional.isEmpty()) {
            throw new QuizzNotFoundException("Quizz with id " + quizzId + " not found!");
        }
        Quizz quizz = optional.get();

        //         users = optional.get().getUsers();
        List<User> users = quizz.getRegisterQuizzes().stream()
                                .map(RegisterQuizz::getUser)
                                .collect(Collectors.toList());
        // mapper
        return UserMapper.MAPPER.toResponses(users);
    }


    private Date calculateStartTime(String period) {
        Date now = new Date();
        long millisecondsInDay = 24 * 60 * 60 * 1000L; // Milliseconds in a day
        long startTimeMillis;

        switch (period) {
            case "day":
                startTimeMillis = now.getTime() - millisecondsInDay;
                break;
            case "week":
                startTimeMillis = now.getTime() - 7 * millisecondsInDay;
                break;
            case "month":
                startTimeMillis = now.getTime() - 30 * millisecondsInDay; // Approximation for a month
                break;
            default:
                startTimeMillis = now.getTime() - millisecondsInDay;
                break;
        }

        return new Date(startTimeMillis);
    }
}
