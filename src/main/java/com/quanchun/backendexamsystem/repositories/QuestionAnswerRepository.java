package com.quanchun.backendexamsystem.repositories;

import com.quanchun.backendexamsystem.entities.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Integer> {
}
