package com.quanchun.backendexamsystem.services.impls;

import com.quanchun.backendexamsystem.entities.*;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.error.RegisterQuizzNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;
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
    public RegisterQuizz registerQuizz(Long userId, int quizzId) throws QuizzNotFoundException, UserNotFoundException {
        Quizz quizz = quizzRepository.findById(quizzId)
                .orElseThrow(() -> new QuizzNotFoundException("Quizz with id " + quizzId + " not found!"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found!"));

        // Co the kiem tra xem co phai role user ko thi moi add


        RegisterQuizz registerQuizz = RegisterQuizz.builder()
                .user(user)
                .quizz(quizz)
                .startedTime(quizz.getStartedAt())
                .endTime(quizz.getEndedAt())
                .build();
        registerQuizzRepository.save(registerQuizz);
        return registerQuizz;
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

    @Override
    public List<Quizz> getQuizzesByUserId(Long userId) throws UserNotFoundException {
        Optional<User> optional = userRepository.findById(userId);
        if(optional.isEmpty())
        {
            throw new UserNotFoundException("User with id " + userId + " not found!");
        }
        List<Quizz> quizzes = quizzRepository.findQuizzesByUsersUserId(userId);
        return quizzes;
    }

    @Override
    public List<User> getUsersByQuizzesId(int quizzId) throws QuizzNotFoundException {
        Optional<Quizz> optional = quizzRepository.findById(quizzId);
        if(optional.isEmpty())
        {
            throw new QuizzNotFoundException("Quizz with id " + quizzId + " not found!");
        }
        List<User> users = userRepository.findUsersByQuizzesId((long) quizzId);
        return users;
    }


    @Override
    public RegisterQuizz submitQuizz(int id, SubmitQuizzDTO submitQuizzDTO) throws RegisterQuizzNotFoundException {
        RegisterQuizz registerQuizz = findByRegisterId(id);
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
