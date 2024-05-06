package com.quanchun.backendexamsystem.services;

import com.quanchun.backendexamsystem.entities.ParticipantAttempt;
import com.quanchun.backendexamsystem.error.ParticipantAttemptNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ParticipantAttemptService {
    public ParticipantAttempt ceateParticipantAttempt (ParticipantAttempt participantAttempt);
    public ParticipantAttempt getParticipantAttemptById (int participantAttemptId) throws ParticipantAttemptNotFoundException;
    public ParticipantAttempt updateParticipantAttemptById (int participantAttemptId, ParticipantAttempt participantAttempt) throws ParticipantAttemptNotFoundException;

    public boolean deleteParticipantAttempt (int participantAttemptId) throws ParticipantAttemptNotFoundException;
    public List<ParticipantAttempt> getAllParticipantAttempt();

}
