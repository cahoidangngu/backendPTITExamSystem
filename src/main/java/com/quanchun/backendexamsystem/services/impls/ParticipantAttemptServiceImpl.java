package com.quanchun.backendexamsystem.services.impls;

import com.quanchun.backendexamsystem.entities.ParticipantAttempt;
import com.quanchun.backendexamsystem.error.ParticipantAttemptNotFoundException;
import com.quanchun.backendexamsystem.repositories.ParticipantAttemptRepository;
import com.quanchun.backendexamsystem.services.ParticipantAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantAttemptServiceImpl implements ParticipantAttemptService {

    @Autowired
    private ParticipantAttemptRepository participantAttemptRepository;

    @Override
    public ParticipantAttempt ceateParticipantAttempt(ParticipantAttempt participantAttempt) {
        return participantAttemptRepository.save(participantAttempt);
    }

    @Override
    public ParticipantAttempt getParticipantAttemptById(int participantAttemptId) throws ParticipantAttemptNotFoundException {
        return participantAttemptRepository.findById(participantAttemptId).orElseThrow(() -> new ParticipantAttemptNotFoundException("Not found participant attempt with id:" + participantAttemptId));
    }

    @Override
    public ParticipantAttempt updateParticipantAttemptById(int participantAttemptId, ParticipantAttempt participantAttempt) throws ParticipantAttemptNotFoundException {
        return participantAttemptRepository.save(participantAttempt);
    }


    @Override
    public boolean deleteParticipantAttempt(int participantAttemptId) throws ParticipantAttemptNotFoundException {
        if (!participantAttemptRepository.existsById(participantAttemptId)) {
            throw new ParticipantAttemptNotFoundException("Participant Attempt not found with id: " + participantAttemptId);
        }
        participantAttemptRepository.deleteById(participantAttemptId);
        boolean isDeleted = !participantAttemptRepository.existsById(participantAttemptId);
        if (!isDeleted) {
            // LOgger
            return false;
        }
        return true;
    }

    @Override
    public List<ParticipantAttempt> getAllParticipantAttempt() {
        return participantAttemptRepository.findAll();
    }
}
