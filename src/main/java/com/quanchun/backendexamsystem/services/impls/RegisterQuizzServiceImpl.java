package com.quanchun.backendexamsystem.services.impls;

import com.quanchun.backendexamsystem.entities.*;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.error.RegisterQuizzNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;
import com.quanchun.backendexamsystem.models.RegisterQuizzDTO;
import com.quanchun.backendexamsystem.models.SubmitQuizzDTO;
import com.quanchun.backendexamsystem.models.UserAnswerDTO;
import com.quanchun.backendexamsystem.repositories.QuizzRepository;
import com.quanchun.backendexamsystem.repositories.RegisterQuizzRepository;
import com.quanchun.backendexamsystem.repositories.UserRepository;
import com.quanchun.backendexamsystem.services.QuestionService;
import com.quanchun.backendexamsystem.services.RegisterQuizzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegisterQuizzServiceImpl implements RegisterQuizzService {
    @Autowired
    private RegisterQuizzRepository registerQuizzRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuizzRepository quizzRepository;

    @Autowired
    private QuestionService questionService;

    @Override
    public RegisterQuizzDTO registerQuizz(Long userId, int quizzId) throws QuizzNotFoundException, UserNotFoundException {
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
        RegisterQuizzDTO registerQuizzDTO = RegisterQuizzDTO.builder()
                .userFullName(user.getFullName())
                .quizzTitle(quizz.getTitle())
                .startedTime(quizz.getStartedAt())
                .endTime(quizz.getEndedAt())
                .studyClass(user.getStudyClass())
                .build();
        quizz.addRegisterQuizz(registerQuizz);
        user.addRegisterQuizz(registerQuizz);
        registerQuizzRepository.save(registerQuizz);
        return registerQuizzDTO;
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
    public RegisterQuizz submitQuizz(int id, SubmitQuizzDTO submitQuizzDTO) throws RegisterQuizzNotFoundException {
        RegisterQuizz registerQuizz = findByRegisterId(id);
        /*
        * BeginTime front lấy khi user bấm làm bài
        * FinishedTime front lấy khi user bấm submit
        * */
        registerQuizz.setBeginTime(submitQuizzDTO.getBeginTime());
        registerQuizz.setFinishedTime(submitQuizzDTO.getFinishTime());

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
        return registerQuizzRepository.save(registerQuizz);
    }


}
