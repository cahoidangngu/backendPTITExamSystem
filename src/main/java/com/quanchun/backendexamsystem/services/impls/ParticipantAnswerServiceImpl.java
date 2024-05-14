package com.quanchun.backendexamsystem.services.impls;

import com.quanchun.backendexamsystem.entities.ParticipantAnswer;
import com.quanchun.backendexamsystem.repositories.ParticipantRepository;
import com.quanchun.backendexamsystem.services.ParticipantAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipantAnswerServiceImpl implements ParticipantAnswerService {

    @Autowired
    private ParticipantRepository participantRepository;

    @Override
    public ParticipantAnswer saveParticipantAnswer(ParticipantAnswer participantAnswer) {
        return participantRepository.save(participantAnswer);
    }
}
