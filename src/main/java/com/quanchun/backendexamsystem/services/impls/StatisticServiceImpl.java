package com.quanchun.backendexamsystem.services.impls;

import com.quanchun.backendexamsystem.entities.Quizz;
import com.quanchun.backendexamsystem.entities.RegisterQuizz;
import com.quanchun.backendexamsystem.entities.User;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;
import com.quanchun.backendexamsystem.models.SubmittedUserDTO;
import com.quanchun.backendexamsystem.models.responses.SubmittedQuizzDetailResponse;
import com.quanchun.backendexamsystem.repositories.QuizzRepository;
import com.quanchun.backendexamsystem.repositories.RegisterQuizzRepository;
import com.quanchun.backendexamsystem.repositories.UserRepository;
import com.quanchun.backendexamsystem.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class StatisticServiceImpl implements StatisticsService {
    @Autowired
    private RegisterQuizzRepository registerQuizzRepository;

    @Autowired
    private QuizzRepository quizzRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public SubmittedQuizzDetailResponse getDetailResult(int quizzId) throws QuizzNotFoundException {
        double totalScore = 0;
        double totalTime = 0;
        Optional<Quizz> optional = quizzRepository.findById(quizzId);
        if(optional.isEmpty())
        {
            throw new QuizzNotFoundException("Quizz with id: " + quizzId + " not found");
        }
        Quizz quizz = optional.get();
        List<RegisterQuizz> registerQuizzes = registerQuizzRepository.findByQuizz(quizz);
        SubmittedQuizzDetailResponse response = new SubmittedQuizzDetailResponse();
        for(RegisterQuizz registerQuizz : registerQuizzes) {
            SubmittedUserDTO theDto = SubmittedUserDTO.builder()
                    .userName(registerQuizz.getUser().getFullName())
                    .beginTime(registerQuizz.getBeginTime())
                    .finishedTime(registerQuizz.getFinishedTime())
                    .score(registerQuizz.getScore())
                    .build();
            totalScore += registerQuizz.getScore();
            long diffMil = registerQuizz.getFinishedTime().getTime() - registerQuizz.getBeginTime().getTime();
            long diffMinutes = TimeUnit.MINUTES.convert(diffMil, TimeUnit.MILLISECONDS);
            totalTime += diffMinutes;
            response.addSumittedUser(theDto);
        }
        int sz = registerQuizzes.size();
        response.setAvgScore(totalScore/sz);
        response.setAvgTakeExam(totalTime/sz);
        return response;
    }

    @Override
    public List<SubmittedUserDTO> getUserSubmittedResult(int userId) throws UserNotFoundException {
        Optional<User> optional = userRepository.findById((long) userId);
        if(optional.isEmpty())
        {
            throw new UserNotFoundException("User with id: " + userId + " not found");
        }
        User user = optional.get();
        List<RegisterQuizz> registerQuizzes = registerQuizzRepository.findByUser(user);
        List<SubmittedUserDTO> response = new ArrayList<>();
        for(RegisterQuizz registerQuizz : registerQuizzes)
        {
            response.add(SubmittedUserDTO.builder()
                            .userName(registerQuizz.getUser().getFullName())
                            .beginTime(registerQuizz.getBeginTime())
                            .finishedTime(registerQuizz.getFinishedTime())
                            .score(registerQuizz.getScore())
                    .build());
        }
        return response;
    }
}
