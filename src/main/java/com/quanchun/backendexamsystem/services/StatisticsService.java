package com.quanchun.backendexamsystem.services;

import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;
import com.quanchun.backendexamsystem.models.SubmittedUserDTO;
import com.quanchun.backendexamsystem.models.responses.SubmittedQuizzDetailResponse;

import java.util.List;

public interface StatisticsService {
    SubmittedQuizzDetailResponse getDetailResult(int quizzId) throws QuizzNotFoundException;

    List<SubmittedUserDTO> getUserSubmittedResult(int userId) throws UserNotFoundException;


}