package com.quanchun.backendexamsystem.services.impls;

import com.quanchun.backendexamsystem.entities.Quizz;
import com.quanchun.backendexamsystem.entities.RegisterQuizz;
import com.quanchun.backendexamsystem.entities.User;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.error.RegisterQuizzNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;
import com.quanchun.backendexamsystem.repositories.QuizzRepository;
import com.quanchun.backendexamsystem.repositories.RegisterQuizzRepository;
import com.quanchun.backendexamsystem.repositories.UserRepository;
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

    @Override
    public RegisterQuizz registerQuizz(Long userId, int quizzId) throws QuizzNotFoundException, UserNotFoundException {
        Quizz quizz = quizzRepository.findById(quizzId)
                .orElseThrow(() -> new QuizzNotFoundException("Quizz with id " + quizzId + " not found!"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found!"));

        // Co the kiem tra xem co phai role user ko thi moi add


        RegisterQuizz registerQuizz = new RegisterQuizz(quizz, user, 0, 10, quizz.getStartedAt(), quizz.getEndedAt());
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

}
