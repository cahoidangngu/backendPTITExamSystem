package com.quanchun.backendexamsystem.repositories;

import com.quanchun.backendexamsystem.entities.ParticipantAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantAttemptRepository extends JpaRepository<ParticipantAttempt, Integer> {
}
