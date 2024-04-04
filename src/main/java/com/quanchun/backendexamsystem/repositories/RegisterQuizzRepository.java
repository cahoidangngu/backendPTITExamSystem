package com.quanchun.backendexamsystem.repositories;

import com.quanchun.backendexamsystem.entities.Quizz;
import com.quanchun.backendexamsystem.entities.RegisterQuizz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RegisterQuizzRepository extends JpaRepository<RegisterQuizz, Integer> {

}
