package com.quanchun.backendexamsystem.services.impls;

import com.quanchun.backendexamsystem.entities.*;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.mappers.QuizzMapper;
import com.quanchun.backendexamsystem.mappers.UserMapper;
import com.quanchun.backendexamsystem.models.OptionAnswerDTO;
import com.quanchun.backendexamsystem.models.QuestionDTO;
import com.quanchun.backendexamsystem.models.QuizzDTO;
import com.quanchun.backendexamsystem.models.UserDTO;
import com.quanchun.backendexamsystem.repositories.QuestionRepository;
import com.quanchun.backendexamsystem.models.responses.ResponseQuizDTO;
import com.quanchun.backendexamsystem.repositories.QuizzRepository;
import com.quanchun.backendexamsystem.services.QuestionAnswerService;
import com.quanchun.backendexamsystem.services.QuizzService;
import com.quanchun.backendexamsystem.services.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizzServiceImpl implements QuizzService {
    @Autowired
    private QuizzRepository quizzRepository;
    @Autowired
    private QuestionAnswerService questionAnswerService;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initDB()
    {
        List<Quizz> quizzes = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 200; i++) {
            Quizz quizz = new Quizz();
//            quizz.setId(i + 1); // Assuming id starts from 1
            quizz.setCreatedAt(randomDate());
            quizz.setStartedAt(randomDate());
            quizz.setEndedAt(randomDate());
            quizz.setTitle("Quizz " + (i + 1));
            quizz.setDescription("Description for Quizz " + (i + 1));
            quizz.setDifficulty(random.nextInt(10) + 1); // Random difficulty from 1 to 10
            quizz.setScore(random.nextInt(100)); // Random score
            quizz.setType(random.nextInt(2)); // Random type
            quizz.setHostId(random.nextInt(1000) + 1); // Assuming hostId starts from 1 and goes up to 1000

            quizzes.add(quizz);
        }

        quizzRepository.saveAll(quizzes);
    }
    private java.sql.Date randomDate()
    {
        long millisInYear = 365L * 24 * 60 * 60 * 1000;
        long currentTimeMillis = System.currentTimeMillis();
        long randomMillis = (long) (Math.random() * millisInYear);
        return new java.sql.Date(currentTimeMillis - randomMillis);
    }

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
                    .correctedAnswer(q.getAnswer())
                    //.questionAnswers(q.getQuestionAnswers())
                    .questionContent(q.getQuestion())
                    .multianswer(q.getMultianswer())
                    .difficulty(q.getDifficulty())
                    .build();
            quizz.addQuestion(tmp);
        }
        return quizz;
    }



    @Override
    public Quizz findQuizzById(int id) throws QuizzNotFoundException {
        Optional<Quizz> theQuizz = quizzRepository.findById(id);
        if(theQuizz.isEmpty())throw new QuizzNotFoundException("Not found quiz with id:" +id);
        return theQuizz.get();
    }

    @Override
    public List<ResponseQuizDTO> getAllQuizzes() {
        List<ResponseQuizDTO> responseQuizDTOList = new ArrayList<>();
                quizzRepository.findAll().forEach(quizz -> responseQuizDTOList.add(toResponseQuizDTO(quizz)));
        return responseQuizDTOList;
    }

    @Override
    @Transactional
    public Quizz updateQuizzById(int id, QuizzDTO updatedQuizz) throws QuizzNotFoundException {
        Optional<Quizz> foundedQuizz = quizzRepository.findById(id);
        if(foundedQuizz.isEmpty()){
            throw new QuizzNotFoundException("Quizz with id " + id + "not found");
        }
        Quizz quizz = foundedQuizz.get();
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
    public List<Quizz> getQuizzesByHostId(int hostId) {
        return quizzRepository.findByHostId(hostId);
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
        // mapper
        return UserMapper.MAPPER.toResponses(users);
    }

    @Override
    public ResponseQuizDTO toResponseQuizDTO(Quizz quizz) {
        List<QuestionDTO> questionItemDTOList = new ArrayList<>();

        quizz.getQuestions().forEach((question) -> {
            List<OptionAnswerDTO> optionAnswers = new ArrayList<>();
            question.getQuestionAnswers().forEach(questionAnswer -> optionAnswers.add(questionAnswerService.toOptionAnswerDTO(questionAnswer)));
            questionItemDTOList.add(
                     QuestionDTO.builder()
                            .question(question.getQuestionContent())
                            .optionAnswers(optionAnswers)
                            .answer(question.getCorrectedAnswer())
                            .build());
        });

        return ResponseQuizDTO.builder()
                .id(quizz.getId())
                .hostId(quizz.getHostId())
                .title(quizz.getTitle())
                .description(quizz.getDescription())
                .duration(quizz.getDuration())
                .type(quizz.getType())
                .subject(quizz.getSubject())
                .questionList(questionItemDTOList)
                .build();
    }

    @Override
    public Page<QuizzDTO> getQuizzesWithSortingAndPagingAndFilter(String field, String order, Integer page, Integer pageSize, Integer difficulty, String preDateOption) {
        if(page == null) page = UserServiceImpl.DEFAULT_PAGE;
        if(pageSize == null) pageSize = UserServiceImpl.DEFAULT_PAGE_SIZE;
        Pageable pageable;
        if(field != null && order != null)
        {
            Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Sort sort = Sort.by(direction, field);
            pageable = PageRequest.of(page, pageSize, sort);
        }
        else
        {
            pageable = PageRequest.of(page, pageSize);
        }
        Page<QuizzDTO> quizzes;
        Date startTime;
        List<QuizzDTO> dtos = new ArrayList<>();
        if(difficulty != null && preDateOption != null)
        {
            startTime = calculateStartTime(preDateOption);
            quizzes = quizzRepository.findByCreatedAtAfterAndDifficulty(startTime, difficulty, pageable)
                    .map(quizz -> QuizzMapper.MAPPER.quizz2QuizzDTO(quizz));
        }
        else if(difficulty != null)
        {
            quizzes = quizzRepository.findQuizzesByDifficulty(difficulty, pageable)
                    .map(quizz -> QuizzMapper.MAPPER.quizz2QuizzDTO(quizz));

        }
        else if(preDateOption != null)
        {
             startTime = calculateStartTime(preDateOption);
             quizzes = quizzRepository.findByCreatedAtAfter(startTime, pageable)
                     .map(quizz -> QuizzMapper.MAPPER.quizz2QuizzDTO(quizz));
        }
        else quizzes = quizzRepository.findAll(pageable).map(quizz -> QuizzMapper.MAPPER.quizz2QuizzDTO(quizz));
        return quizzes;
    }



    private Date calculateStartTime(String period) {
        Date now = new Date();
        long millisecondsInDay = 24 * 60 * 60 * 1000L; // Milliseconds in a day
        long startTimeMillis;

        switch (period) {
            case "day":
                startTimeMillis = now.getTime() - millisecondsInDay;
                break;
            case "week":
                startTimeMillis = now.getTime() - 7 * millisecondsInDay;
                break;
            case "month":
                startTimeMillis = now.getTime() - 30 * millisecondsInDay; // Approximation for a month
                break;
            default:
                startTimeMillis = now.getTime() - millisecondsInDay;
                break;
        }

        return new Date(startTimeMillis);
    }
}
