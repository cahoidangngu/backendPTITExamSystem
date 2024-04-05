package com.quanchun.backendexamsystem.repositories;

import com.quanchun.backendexamsystem.entities.Quizz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizzRepository extends JpaRepository<Quizz, Integer> {
    List<Quizz> findQuizzesByQuestionsId(int questionId);

    List<Quizz> findQuizzesByHostId(Long userId);
}
