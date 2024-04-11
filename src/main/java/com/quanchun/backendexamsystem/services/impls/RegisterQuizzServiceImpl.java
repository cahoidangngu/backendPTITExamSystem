package com.quanchun.backendexamsystem.services.impls;

import com.quanchun.backendexamsystem.entities.*;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.error.RegisterQuizzNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;
import com.quanchun.backendexamsystem.models.QuestionAnswerDTO;
import com.quanchun.backendexamsystem.models.responses.ResponseAnswerQuestionDTO;
import com.quanchun.backendexamsystem.models.responses.ResponseRegisterQuizzDTO;
import com.quanchun.backendexamsystem.models.SubmitQuizzDTO;
import com.quanchun.backendexamsystem.models.UserAnswerDTO;
import com.quanchun.backendexamsystem.repositories.QuizzRepository;
import com.quanchun.backendexamsystem.repositories.RegisterQuizzRepository;
import com.quanchun.backendexamsystem.repositories.UserRepository;
import com.quanchun.backendexamsystem.services.QuestionService;
import com.quanchun.backendexamsystem.services.RegisterQuizzService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class RegisterQuizzServiceImpl implements RegisterQuizzService {
    @Autowired
    private RegisterQuizzRepository registerQuizzRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuizzRepository quizzRepository;

    @Autowired
    private QuestionService questionService;


    private Logger logger = LoggerFactory.getLogger(RegisterQuizzService.class);

    private int scoreCaculator(RegisterQuizz registerQuizz){
        AtomicInteger numberRightAnswer = new AtomicInteger(registerQuizz.getScore());
        registerQuizz.getParticipantAnswerList().forEach(participantAnswer -> {
            int correctAnswer = questionService.findQuestionById(participantAnswer.getQuestionId()).getCorrectedAnswer();
            if(correctAnswer == participantAnswer.getUserAnswer()){
                numberRightAnswer.set(numberRightAnswer.get() + 1);
            }
        });
        double numberAnswer = registerQuizz.getQuizz().getQuestions().size();
        return (int)((10f/numberAnswer)* (double)numberRightAnswer.get());
    }

    private ResponseRegisterQuizzDTO RegisterQuizz2ResponseDTO(RegisterQuizz registerQuizz) {
        ResponseRegisterQuizzDTO registerQuizzDTO = ResponseRegisterQuizzDTO.builder()
                .userFullName(registerQuizz.getUser().getFullName())
                .quizzTitle(registerQuizz.getQuizz().getTitle())
                .startedTime(registerQuizz.getQuizz().getStartedAt())
                .endTime(registerQuizz.getQuizz().getEndedAt())
                .studyClass(registerQuizz.getUser().getStudyClass())
                .build();
        if (registerQuizz.getParticipantAnswerList() == null) return registerQuizzDTO;
                registerQuizzDTO.setBeginTime(registerQuizz.getBeginTime());
                registerQuizzDTO.setFinishedTime(registerQuizz.getFinishedTime());
                registerQuizzDTO.setScore(registerQuizz.getScore());
        registerQuizz.getParticipantAnswerList().forEach(participantAnswer -> {
            Question question = questionService.findQuestionById(participantAnswer.getQuestionId());
            List<QuestionAnswerDTO> questionAnswersDTO = new ArrayList<>();
            question.getQuestionAnswers().forEach(questionAnswer -> questionAnswersDTO.add(
                    QuestionAnswerDTO.builder().answer(questionAnswer.getAnswer()).build()
            ));
            registerQuizzDTO.addQuizzQuestion(ResponseAnswerQuestionDTO.builder()
                    .questionContent(question.getQuestionContent())
                    .difficulty(question.getDifficulty())
                    .multianswer(question.getMultianswer())
                    .correctedAnswer(question.getCorrectedAnswer())
                    .userAnswer(participantAnswer.getUserAnswer())
                    .category(question.getCategory())
                    .questionAnswers(questionAnswersDTO)
                    .build());
        });
        return registerQuizzDTO;
    }


    @Override
    public ResponseRegisterQuizzDTO registerQuizz(Long userId, int quizzId) throws QuizzNotFoundException, UserNotFoundException {
        Quizz quizz = quizzRepository.findById(quizzId)
                .orElseThrow(() -> new QuizzNotFoundException("Quizz with id " + quizzId + " not found!"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found!"));

        // Co the kiem tra xem co phai role user ko thi moi add
//        quizz.addUser(user);
//        user.addQuizz(quizz);
        RegisterQuizz registerQuizz = RegisterQuizz.builder()
                .user(user)
                .quizz(quizz)
                .startedTime(quizz.getStartedAt())
                .endTime(quizz.getEndedAt())
                .score(0)
                .status(0)
                .build();
        quizz.addRegisterQuizz(registerQuizz);
        user.addRegisterQuizz(registerQuizz);
        registerQuizzRepository.save(registerQuizz);
        return RegisterQuizz2ResponseDTO(registerQuizz);
    }

    @Override
    public RegisterQuizz findByRegisterId(int id) throws RegisterQuizzNotFoundException {
        Optional<RegisterQuizz> optional = registerQuizzRepository.findById(id);
        if(optional.isEmpty())
        {
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
    public ResponseRegisterQuizzDTO submitQuizz(int id, SubmitQuizzDTO submitQuizzDTO) throws RegisterQuizzNotFoundException {
        RegisterQuizz registerQuizz = findByRegisterId(id);
        /*
        * BeginTime front lấy khi user bấm làm bài
        * FinishedTime front lấy khi user bấm submit
        * */
        registerQuizz.setBeginTime(submitQuizzDTO.getBeginTime());
        registerQuizz.setFinishedTime(submitQuizzDTO.getFinishTime());
        logger.info(submitQuizzDTO.getUserAnswerDTOList().toString());
        for(UserAnswerDTO userAnswerDTO : submitQuizzDTO.getUserAnswerDTOList()){
            Question question = questionService.findQuestionById(userAnswerDTO.getQuestionId());
            if (question == null) continue;
            ParticipantAnswer participantAnswer = ParticipantAnswer.builder()
                    .registerQuizzId(id)
                    .questionId(question.getId())
                    .userAnswer(userAnswerDTO.getUserAnswer())
                    .build();
            registerQuizz.addParticipantAnswer(participantAnswer);
        }
        registerQuizz.setScore(scoreCaculator(registerQuizz));
        return RegisterQuizz2ResponseDTO(registerQuizzRepository.save(registerQuizz));
    }


}
