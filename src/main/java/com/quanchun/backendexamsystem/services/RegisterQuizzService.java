package com.quanchun.backendexamsystem.services;

import com.quanchun.backendexamsystem.entities.RegisterQuizz;
import com.quanchun.backendexamsystem.error.ParticipantAttemptNotFoundException;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.error.RegisterQuizzNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;
import com.quanchun.backendexamsystem.models.requests.ParticipantAttemptRequestDTO;
import com.quanchun.backendexamsystem.models.requests.RegisterQuizzRequest;
import com.quanchun.backendexamsystem.models.responses.ParticipantAttemptResponseDTO;
import com.quanchun.backendexamsystem.models.responses.ResponseRegisterQuizzDTO;
import com.quanchun.backendexamsystem.models.SubmitQuizzDTO;

public interface RegisterQuizzService {
    ResponseRegisterQuizzDTO registerQuizz(long userId,int quizzId) throws QuizzNotFoundException, UserNotFoundException;
    RegisterQuizz findByRegisterId(int id) throws RegisterQuizzNotFoundException;

    RegisterQuizz deleteByRegisterId(int id);

    ParticipantAttemptResponseDTO createParticipantAttempt (int registerQuizzId) throws RegisterQuizzNotFoundException;

    ParticipantAttemptResponseDTO submitParticipantAttempt(int id, ParticipantAttemptRequestDTO submitQuizzDTO) throws ParticipantAttemptNotFoundException;
}