package com.quanchun.backendexamsystem.repositories;

import com.quanchun.backendexamsystem.entities.ParticipantAnswer;
import com.quanchun.backendexamsystem.entities.ParticipantAnswerId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<ParticipantAnswer, ParticipantAnswerId> {

}
