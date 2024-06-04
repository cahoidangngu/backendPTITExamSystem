package com.quanchun.backendexamsystem;

import com.quanchun.backendexamsystem.entities.*;
import com.quanchun.backendexamsystem.repositories.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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
		initDB();
	}

	private java.sql.Date randomDate()
	{
		long millisInYear = 365L * 24 * 60 * 60 * 1000;
		long currentTimeMillis = System.currentTimeMillis();
		long randomMillis = (long) (Math.random() * millisInYear);
		return new java.sql.Date(currentTimeMillis - randomMillis);
	}

	public void initDB() {
		List<Quizz> quizzes = new ArrayList<>();
		List<Question> questions = new ArrayList<>();
		List<QuestionAnswer> questionAnswers = new ArrayList<>();
		List<User> users = new ArrayList<>();
		List<Role> roles = new ArrayList<>();

		Random random = new Random();

		// Creating roles
			Role role = new Role();
			role.setRoleId((long) 0);
			role.setName("ROLE_ADMIN");
			roles.add(role);
			role.setRoleId((long) 1);
			role.setName("ROLE_USER");
			roles.add(role);

		// Creating users
		for (int i = 0; i < 200; i++) {
			User user = new User();
			user.setUserId((long) i + 1);
			user.setUsername("User" + (i + 1));
			user.setPassword("Password" + (i + 1));
			user.setFullName("Full Name " + (i + 1));
			user.setDob(randomDate()); // Static date of birth
			user.setStudyClass("Class " + (i % 10 + 1));
			user.setPhone("1234567890");
			user.setGender(random.nextBoolean());
			user.setAddress("Address " + (i + 1));
			user.setImagePath("/path/to/image" + (i + 1));
			user.addRole(roles.get(random.nextInt(2))); // Each user gets 2 roles
			users.add(user);
		}

		for (int i = 0; i < 500; i++) {
			QuestionAnswer answer = new QuestionAnswer();
			answer.setQaId(i); // Ensure unique ID
			answer.setAnswer("Answer " + (i));
			questionAnswers.add(answer);
		}

		// Creating questions and linking answers
		for (int i = 0; i < 200; i++) {
			Question question = new Question();
			question.setId(i + 1);
			question.setQuestionContent("Question content " + (i + 1));
			question.setDifficulty(random.nextInt(4) ); // Difficulty from 1 to 5
			question.setMultianswer(random.nextInt(4,6) +1); // Number of answers from 1 to 4
			question.setCorrectedAnswer(random.nextInt(question.getMultianswer())); // Correct answer index
			question.setCategory("Category " + ((i % 10) + 1));
			List<QuestionAnswer> uniqueAnswers = new ArrayList<>();

			while (uniqueAnswers.size() < question.getMultianswer()) {
				QuestionAnswer questionAnswer = questionAnswers.get(random.nextInt(500));
				if (!uniqueAnswers.contains(questionAnswer)) {
					uniqueAnswers.add(questionAnswer);
					question.addQuestionAnswer(questionAnswer);
				}
			}
			questions.add(question);
		}

		List<String> subjectList = new ArrayList<>();
		subjectList.add("Toán");
		subjectList.add("Văn");
		subjectList.add("Anh");
		subjectList.add("Lịch sử");

		// Creating quizzes and linking questions
		for (int i = 0; i < 200; i++) {
			Date date = randomDate();
			LocalDate localDate = date.toLocalDate();

			// Cộng thêm 10 ngày
			localDate = localDate.plusDays(10);
			Date end = Date.valueOf(localDate);
			Quizz quizz = new Quizz();
			quizz.setId(i + 1);
			quizz.setCreatedAt(date);
			quizz.setStartedAt(date);
			quizz.setEndedAt(end);
			quizz.setTitle("Quizz " + (i + 1));
			quizz.setDescription("Description for Quizz " + (i + 1));
			quizz.setDifficulty(random.nextInt(10) + 1);
			quizz.setDuration(random.nextInt(60,90)); // Duration between 30 to 90 minutes
			quizz.setScore(random.nextInt(100));
			quizz.setType(random.nextInt(2));
			quizz.setSubject(subjectList.get(random.nextInt(4)));
			quizz.setHostId(random.nextInt(200)); // Host IDs corresponding to user IDs
			if(i * 2 % 200<180)
				quizz.setQuestions(new ArrayList<>(questions.subList(i * 2 % 200, (i * 2 % 200) + random.nextInt(10,15)))); // Each quizz gets 2 random questions
			quizzes.add(quizz);
		}

		// Save to repositories
		roleRepository.saveAll(roles);
		userRepository.saveAll(users);
		questionAnswerRepository.saveAll(questionAnswers);
		questionRepository.saveAll(questions);
		quizzRepository.saveAll(quizzes);
	}

}
