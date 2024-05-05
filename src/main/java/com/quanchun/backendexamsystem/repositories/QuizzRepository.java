package com.quanchun.backendexamsystem.repositories;

import com.quanchun.backendexamsystem.entities.Quizz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface QuizzRepository extends JpaRepository<Quizz, Integer> {
    List<Quizz> findQuizzesByQuestionsId(Integer questionId);
    Page<Quizz> findQuizzesByDifficulty(Integer difficulty, Pageable pageable);

    Page<Quizz> findByCreatedAtAfter(Date startTime, Pageable pageable);

    Page<Quizz> findByCreatedAtAfterAndDifficulty(Date startTime, Integer difficulty, Pageable pageable);

}
