package com.quanchun.backendexamsystem.services;

import com.quanchun.backendexamsystem.entities.ParticipantAnswer;
import org.springframework.stereotype.Service;


public interface ParticipantService  {
    ParticipantAnswer saveParticipantAnswer (ParticipantAnswer participantAnswer);
}
