package com.quanchun.backendexamsystem.services.impls;

import com.quanchun.backendexamsystem.entities.*;
import com.quanchun.backendexamsystem.error.ParticipantAttemptNotFoundException;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.error.RegisterQuizzNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;
import com.quanchun.backendexamsystem.models.QuestionAnswerDTO;
import com.quanchun.backendexamsystem.models.requests.ParticipantAttemptRequestDTO;
import com.quanchun.backendexamsystem.models.requests.RegisterQuizzRequest;
import com.quanchun.backendexamsystem.models.responses.ParticipantAttemptResponseDTO;
import com.quanchun.backendexamsystem.models.responses.ResponseAnswerQuestionDTO;
import com.quanchun.backendexamsystem.models.responses.ResponseRegisterQuizzDTO;
import com.quanchun.backendexamsystem.models.UserAnswerDTO;
import com.quanchun.backendexamsystem.repositories.QuizzRepository;
import com.quanchun.backendexamsystem.repositories.RegisterQuizzRepository;
import com.quanchun.backendexamsystem.repositories.UserRepository;
import com.quanchun.backendexamsystem.services.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class RegisterQuizzServiceImpl implements RegisterQuizzService {
    @Autowired
    private RegisterQuizzRepository registerQuizzRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private QuizzService quizzService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ParticipantAttemptService participantAttemptService;


    private Logger logger = LoggerFactory.getLogger(RegisterQuizzService.class);

    private double scoreCaculator(ParticipantAttempt participantAttempt) {
        AtomicReference<Double> numberRightAnswer = new AtomicReference<>(participantAttempt.getScore());
        participantAttempt.getParticipantAnswerList().forEach(participantAnswer -> {
            int correctAnswer = questionService.findQuestionById(participantAnswer.getQuestionId())
                    .getCorrectedAnswer();
            if (correctAnswer == participantAnswer.getUserAnswer()) {
                numberRightAnswer.set(numberRightAnswer.get() + 1f);
            }
        });
        double numberAnswer = participantAttempt.getRegisterQuiz().getQuizz().getQuestions().size();
        return ((10f / numberAnswer) * (double) numberRightAnswer.get());
    }

    private ParticipantAttemptResponseDTO mapParticipantAttemptToResponseDTO(ParticipantAttempt participantAttempt) {
        RegisterQuizz registerQuizz = participantAttempt.getRegisterQuiz();
        ParticipantAttemptResponseDTO participantAttemptResponseDTO = ParticipantAttemptResponseDTO.builder()
                .participantAttemptId(
                        participantAttempt.getParticipantAttemptId())
                .userFullName(
                        registerQuizz.getUser()
                                .getFullName())
                .studyClass(
                        registerQuizz.getUser()
                                .getStudyClass())
                .quizTitle(
                        registerQuizz.getQuizz()
                                .getTitle())
                .quizDifficulty(
                        registerQuizz.getQuizz()
                                .getDifficulty())
                .beginTime(
                        registerQuizz.getBeginTime())
                .endTime(
                        registerQuizz.getEndTime())

                .status(registerQuizz.getStatus())
                .score(participantAttempt.getScore())
                .build();
        if (participantAttempt.getParticipantAnswerList() == null) return participantAttemptResponseDTO;
        participantAttemptResponseDTO.setStartedTime(participantAttempt.getStartTime());
        participantAttemptResponseDTO.setFinishedTime(participantAttempt.getFinishTime());
        participantAttemptResponseDTO.setScore(participantAttempt.getScore());
        participantAttempt.getParticipantAnswerList().forEach(participantAnswer -> {
            Question question = questionService.findQuestionById(participantAnswer.getQuestionId());
            List<QuestionAnswerDTO> questionAnswersDTO = new ArrayList<>();
            question.getQuestionAnswers().forEach(questionAnswer -> questionAnswersDTO.add(
                    QuestionAnswerDTO.builder().answer(questionAnswer.getAnswer()).build()
            ));
            participantAttemptResponseDTO.addQuizQuestion(ResponseAnswerQuestionDTO.builder()
                    .questionContent(
                            question.getQuestionContent())
                    .difficulty(question.getDifficulty())
                    .multianswer(
                            question.getMultianswer())
                    .correctedAnswer(
                            question.getCorrectedAnswer())
                    .userAnswer(
                            participantAnswer.getUserAnswer())
                    .category(question.getCategory())
                    .questionAnswers(questionAnswersDTO)
                    .build());
        });
        return participantAttemptResponseDTO;
    }


    @Override
    public ResponseRegisterQuizzDTO registerQuizz(long userId, int registerQuizId)
            throws QuizzNotFoundException, UserNotFoundException {
        Quizz quizz = quizzService.findQuizzById(registerQuizId);


        User user = userService.getUserById(userId);

        RegisterQuizz registerQuizz = RegisterQuizz.builder()
                .user(user)
                .quizz(quizz)
                .beginTime(quizz.getStartedAt())
                .endTime(quizz.getEndedAt())
                .status(0)
                .build();
        registerQuizz = registerQuizzRepository.save(registerQuizz);

        return ResponseRegisterQuizzDTO.builder()
                .registerQuizId(registerQuizz.getRegisterId())
                .numberParticipantAttempt(0)
                //.numberParticipantAttempt(registerQuizz.getParticipantAttemptList().size()) // ???? lúc nàylisist null mà
                .beginTime(registerQuizz.getBeginTime())
                .endTime(registerQuizz.getEndTime())
                .quizzTitle(registerQuizz.getQuizz().getTitle()).build();
    }

    @Override
    public RegisterQuizz findByRegisterId(int id) throws RegisterQuizzNotFoundException {
        Optional<RegisterQuizz> optional = registerQuizzRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RegisterQuizzNotFoundException("Not found!");
        }
        RegisterQuizz registerQuizz = optional.get();
        return registerQuizz;
    }

    @Override
    public RegisterQuizz deleteByRegisterId(int id) {
        return null;
    }


    /*
    * Submit quizz
    *  + Lưu lại giá trị vào bảng
    *  + So sánh kq để tính toán ra điểm cũng như viết 1 dto trả về kiểu: (paste ra jsonformatter)
    *  {
  "username": "dtn",
  "studyClass": "E03",
  "quizzname": "bla bla",
  "startedTime": "....",
  "endedTime": "....",
  "participantAnswer":
  [
    {
      "questionId": 18,
      "userAnswer": 1,
      "correctedAnswer": 0
    },
    {
      "questionId": 17,
      "userAnswer": 3,
      "correctedAnswer": 2
    }
  ]
}
    *  + update lại registerquizz do ban dau score = 0
    *
    * */


    @Override
    public ParticipantAttemptResponseDTO createParticipantAttempt(int registerQuizzId)
            throws RegisterQuizzNotFoundException {
        RegisterQuizz registerQuizz = registerQuizzRepository.findById(registerQuizzId)
                .orElseThrow(() -> new RegisterQuizzNotFoundException(
                        "Not found register quizz with id: " + registerQuizzId));
        registerQuizz.setStatus(1);

        ParticipantAttempt participantAttempt = participantAttemptService.ceateParticipantAttempt(
                ParticipantAttempt.builder().registerQuiz(registerQuizz).build());

        registerQuizzRepository.save(registerQuizz);
        return mapParticipantAttemptToResponseDTO(participantAttempt);
    }

    @Override
    public ParticipantAttemptResponseDTO submitParticipantAttempt(int participantAttemptId,
                                                                  ParticipantAttemptRequestDTO participantAttemptRequestDTO)
            throws ParticipantAttemptNotFoundException {
        ParticipantAttempt participantAttempt = participantAttemptService.getParticipantAttemptById(
                participantAttemptId);
        /*
         * BeginTime front lấy khi user bấm làm bài
         * FinishedTime front lấy khi user bấm submit
         * */
        participantAttempt.setStartTime(participantAttemptRequestDTO.getStartTime());
        participantAttempt.setFinishTime(participantAttemptRequestDTO.getFinishTime());

        for (UserAnswerDTO userAnswerDTO : participantAttemptRequestDTO.getUserAnswerDTOList()) {
            Question question = questionService.findQuestionById(userAnswerDTO.getQuestionId());
            if (question == null) continue;
            ParticipantAnswer participantAnswer = ParticipantAnswer.builder()
                    .participantAttemptId(
                            participantAttempt.getParticipantAttemptId())
                    .questionId(question.getId())
                    .userAnswer(userAnswerDTO.getUserAnswer())
                    .build();
            participantAttempt.addParticipantAnswer(participantAnswer);
        }
        participantAttempt.setScore(scoreCaculator(participantAttempt));
        participantAttemptService.updateParticipantAttemptById(participantAttemptId, participantAttempt);
        return mapParticipantAttemptToResponseDTO(participantAttempt);
    }


}