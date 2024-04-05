package com.quanchun.backendexamsystem;

import com.quanchun.backendexamsystem.entities.Quizz;
import com.quanchun.backendexamsystem.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendExamSystemApplication implements CommandLineRunner {

	@Autowired
	QuestionAnswerRepository questionAnswerRepository;

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	QuizzRepository quizzRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;


	public static void main(String[] args) {
		SpringApplication.run(BackendExamSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
