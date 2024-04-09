package com.quanchun.backendexamsystem.services.impls;

import com.quanchun.backendexamsystem.entities.Question;
import com.quanchun.backendexamsystem.entities.Quizz;
import com.quanchun.backendexamsystem.entities.RegisterQuizz;
import com.quanchun.backendexamsystem.entities.User;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;
import com.quanchun.backendexamsystem.mappers.UserMapper;
import com.quanchun.backendexamsystem.models.QuestionDTO;
import com.quanchun.backendexamsystem.models.QuizzDTO;
import com.quanchun.backendexamsystem.models.UserDTO;
import com.quanchun.backendexamsystem.repositories.QuizzRepository;
import com.quanchun.backendexamsystem.services.QuizzService;
import com.quanchun.backendexamsystem.services.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuizzServiceImpl implements QuizzService {
    @Autowired
    private QuizzRepository quizzRepository;
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserService userService;
    @Override
    @Transactional
    public Quizz addQuizz(QuizzDTO theQuizz) {
        Quizz nwQuizz = Quizz.builder()
                .description(theQuizz.getDescription())
                .score(theQuizz.getScore())
                .endedAt(theQuizz.getEndedAt())
                .title(theQuizz.getTitle())
                .type(theQuizz.getType())
                .difficulty(theQuizz.getDifficulty())
                .startedAt(theQuizz.getStartedAt())
                .createdAt(theQuizz.getCreatedAt())
                .hostId(theQuizz.getHostId())
                .questions(theQuizz.getQuestions())
                .build();
        quizzRepository.save(nwQuizz);

        return nwQuizz;
    }

    // lien quan den question entity
    @Override
    @Transactional
    public Quizz addQuestions(int id, List<QuestionDTO> questions) throws QuizzNotFoundException {
        Optional<Quizz> foundQuizz = quizzRepository.findById(id);
        if(foundQuizz.isEmpty())
        {
            throw new QuizzNotFoundException("Quizz with id " + id + "not found");
        }
        Quizz quizz = foundQuizz.get();
        for(QuestionDTO q: questions)
        {
            Question tmp = Question.builder()
                    .category(q.getCategory())
                    .correctedAnswer(q.getCorrectedAnswer())
                    //.questionAnswers(q.getQuestionAnswers())
                    .questionContent(q.getQuestionContent())
                    .multianswer(q.getMultianswer())
                    .difficulty(q.getDifficulty())
                    .build();
            quizz.addQuestion(tmp);
        }
        return quizz;
    }



    @Override
    public Quizz findQuizzById(int id) {
        Optional<Quizz> theQuizz = quizzRepository.findById(id);
        return theQuizz.get();
    }

    @Override
    public List<Quizz> getAllQuizzes() {
        List<Quizz> quizzes = quizzRepository.findAll();
        return quizzes;
    }

    @Override
    @Transactional
    public Quizz updateQuizzById(int id, QuizzDTO updatedQuizz) throws QuizzNotFoundException {
        Optional<Quizz> foundedQuizz = quizzRepository.findById(id);
        if(foundedQuizz.isEmpty()){
            throw new QuizzNotFoundException("Quizz with id " + id + "not found");
        }
        Quizz quizz = foundedQuizz.get();
        System.out.println(updatedQuizz.toString());
        if(Objects.nonNull((Integer) updatedQuizz.getHostId()))
        {
            quizz.setHostId(updatedQuizz.getHostId());
        }

        if(Objects.nonNull(updatedQuizz.getTitle()))
        {
            quizz.setTitle(updatedQuizz.getTitle());
        }

        if(Objects.nonNull((Integer)updatedQuizz.getType()))
        {
            quizz.setType(updatedQuizz.getType());
        }

        if(Objects.nonNull((Integer)updatedQuizz.getScore()))
        {
            quizz.setScore(updatedQuizz.getScore());
        }

        if(Objects.nonNull(updatedQuizz.getCreatedAt()))
        {
            quizz.setCreatedAt(updatedQuizz.getCreatedAt());
        }

        if(Objects.nonNull(updatedQuizz.getStartedAt()))
        {
            quizz.setStartedAt(updatedQuizz.getStartedAt());
        }

        if(Objects.nonNull(updatedQuizz.getEndedAt()))
        {
            quizz.setEndedAt(updatedQuizz.getEndedAt());
        }

        if(Objects.nonNull((Integer)updatedQuizz.getDifficulty()))
        {
            quizz.setDifficulty(updatedQuizz.getDifficulty());
        }

//        if(Objects.nonNull(updatedQuizz.getQuestions()))
//        {
//            quizz.setQuestions(updatedQuizz.getQuestions());
//        }

        return quizzRepository.save(quizz);
    }

    @Override
    public List<Quizz> getQuizzesByDifficulty(int difficulty) {
        TypedQuery<Quizz> request = entityManager.createQuery(
                "select a from Quizz a where a.difficulty = :data", Quizz.class);
        request.setParameter("data", difficulty);
        List<Quizz> result = request.getResultList();
        return result;
    }

    @Override
    public List<Quizz> getQuizzesByHostId(int hostId) {
        TypedQuery<Quizz> request = entityManager.createQuery(
                "select a from Quizz a "
                + "JOIN FETCH a.questions "
                + "where a.hostId = :data", Quizz.class);
        request.setParameter("data", hostId);
        List<Quizz> result = request.getResultList();
        return result;
    }


    @Override
    @Transactional
    public void deleteById(int theId) throws QuizzNotFoundException {
        Optional<Quizz> optionalQuizz = quizzRepository.findById(theId);
        if(optionalQuizz.isEmpty())
        {
            //throw exception
            throw new QuizzNotFoundException("Quizz with id " + theId + "not found");
        }
        Quizz deletedQuizz = optionalQuizz.get();
        for(Question question:deletedQuizz.getQuestions())
        {

        }
        quizzRepository.deleteById(theId);
    }

    @Override
    public List<UserDTO> getUsersByQuizzesId(int quizzId) throws QuizzNotFoundException {
        Optional<Quizz> optional = quizzRepository.findById(quizzId);
        if(optional.isEmpty())
        {
            throw new QuizzNotFoundException("Quizz with id " + quizzId + " not found!");
        }
        Quizz quizz = optional.get();

//         users = optional.get().getUsers();
        List<User> users = quizz.getRegisterQuizzes().stream()
                        .map(RegisterQuizz::getUser)
                        .collect(Collectors.toList());
        System.out.println(users.size() + "");
        System.out.println(users.get(users.size() - 1).toString());
        // mapper
        return UserMapper.MAPPER.toResponses(users);
    }
}
