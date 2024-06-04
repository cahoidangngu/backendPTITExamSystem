package com.quanchun.backendexamsystem.repositories;

import com.quanchun.backendexamsystem.entities.Quizz;
import com.quanchun.backendexamsystem.entities.RegisterQuizz;
import com.quanchun.backendexamsystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RegisterQuizzRepository extends JpaRepository<RegisterQuizz, Integer> {
    List<RegisterQuizz> findByUser(User user);

    List<RegisterQuizz> findByQuizz(Quizz quizz);

}
