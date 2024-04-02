package com.quanchun.backendexamsystem.repositories;

import com.quanchun.backendexamsystem.entities.Quizz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizzRepository extends JpaRepository<Quizz, Integer> {
    List<Quizz> findQuizzesByQuestionsId(int questionId);
}
