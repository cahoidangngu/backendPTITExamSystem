package com.quanchun.backendexamsystem.repositories;

import com.quanchun.backendexamsystem.entities.Question;
import com.quanchun.backendexamsystem.entities.Quizz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findQuestionsByQuizzesId(int quizzId);
    Page<Question> findByCategoryAndDifficulty(String category, Integer difficulty, Pageable pageable);
    Page<Question> findByCategory(String category, Pageable pageable);
    Page<Question> findByDifficulty(Integer difficulty, Pageable pageable);
}
