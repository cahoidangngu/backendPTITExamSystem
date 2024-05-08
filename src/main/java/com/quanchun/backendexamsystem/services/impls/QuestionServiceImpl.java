package com.quanchun.backendexamsystem.services.impls;

import com.quanchun.backendexamsystem.entities.Question;
import com.quanchun.backendexamsystem.entities.QuestionAnswer;
import com.quanchun.backendexamsystem.entities.Quizz;
import com.quanchun.backendexamsystem.entities.User;
import com.quanchun.backendexamsystem.error.QuestionNotFoundException;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.mappers.UserMapper;
import com.quanchun.backendexamsystem.models.QuestionAnswerDTO;
import com.quanchun.backendexamsystem.models.QuestionDTO;
import com.quanchun.backendexamsystem.models.UserDTO;
import com.quanchun.backendexamsystem.repositories.QuestionAnswerRepository;
import com.quanchun.backendexamsystem.repositories.QuestionRepository;
import com.quanchun.backendexamsystem.repositories.QuizzRepository;
import com.quanchun.backendexamsystem.repositories.UserRepository;
import com.quanchun.backendexamsystem.services.QuestionService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/*
*  Nên thêm category cho ngân hàng câu hỏi rồi cho filter
* */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuizzRepository quizzRepository;
    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;
    @Autowired
    EntityManager entityManager;
    /* Gen data for test*/
    @PostConstruct
    public void initDB()
    {
        Random random = new Random();

        for (int i = 0; i < 200; i++) {
            Question question = new Question();
            question.setId(i + 1); // Assuming id starts from 1
            question.setQuestionContent("Question " + (i + 1));
            question.setMultianswer(4);
            question.setDifficulty(random.nextInt(10) + 1); // Random difficulty from 1 to 10
            question.setCorrectedAnswer(random.nextInt(4) + 1); // Random correct answer from 1 to 4
            question.setCategory("Category " + (random.nextInt(10) + 1)); // Random category
            question.setQuestionAnswers(generateAnswers());

            questionRepository.save(question);
        }
    }
    private List<QuestionAnswer> generateAnswers() {
        List<QuestionAnswer> answers = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            QuestionAnswer answer = new QuestionAnswer();
            answer.setQaId(i + 1); // Assuming id starts from 1
            answer.setAnswer("Answer " + (i + 1));
            answers.add(answer);
        }
        questionAnswerRepository.saveAll(answers);
        return answers;
    }
    @Override
    @Transactional
    public Question addQuestion(QuestionDTO theQuestion) {
        System.out.println(theQuestion.toString());
        Question question = Question.builder()
                .questionContent(theQuestion.getQuestionContent())
                .difficulty(theQuestion.getDifficulty())
                .multianswer(theQuestion.getMultianswer())
                .category(theQuestion.getCategory())
                .correctedAnswer(theQuestion.getCorrectedAnswer())
                .build();
        for(QuestionAnswerDTO answer : theQuestion.getQuestionAnswers())
        {
            QuestionAnswer answer2Add = QuestionAnswer.builder()
                    .answer(answer.getAnswer())
                    .build();
            question.addQuestionAnswer(answer2Add);
        }
        questionRepository.save(question);
        return question;
    }

    @Override
    public Question addQuestion2Quizz(int quizzId, Question theQuestion) throws QuizzNotFoundException {
        Question question = quizzRepository.findById(quizzId).map(quizz -> {
            int questionId = theQuestion.getId();

            // if question already existed
            if(questionId != 0)
            {
                try {
                    Question _question = questionRepository.findById(questionId)
                            .orElseThrow(() -> new QuestionNotFoundException("Question with id " + questionId + " not found!"));
                    quizz.addQuestion(_question);
                    quizzRepository.save(quizz);
                    return _question;
                } catch (QuestionNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            // add and create new question
            quizz.addQuestion(theQuestion);
            return questionRepository.save(theQuestion);
        }).orElseThrow(() -> new QuizzNotFoundException("Quizz with id " + quizzId + " not found!"));

        return question;
    }

    @Override
    public List<QuestionAnswer> updateAnswer(int id, List<QuestionAnswerDTO> answerDTOS) {
        Optional<Question> foundedQuestion = questionRepository.findById(id);
        if(foundedQuestion.isEmpty())
        {
            // throw exception
        }
        Question question = foundedQuestion.get();
        if(Objects.nonNull(answerDTOS))
        {
            question.getQuestionAnswers().clear();
            for(QuestionAnswerDTO answerDTO: answerDTOS)
            {
                QuestionAnswer answer = QuestionAnswer.builder()
                        .answer(answerDTO.getAnswer())
                        .build();
                question.getQuestionAnswers().add(answer);
            }
        }
        return null;
    }

    @Override
    @Transactional
    public Question updateQuestionById(int id, QuestionDTO theQuestion) throws QuestionNotFoundException {
        Optional<Question> foundedQuestion = questionRepository.findById(id);
        if(foundedQuestion.isEmpty())
        {
            throw new QuestionNotFoundException("Question with id " + id + "not found!");
        }
        Question question = foundedQuestion.get();
        if(Objects.nonNull(theQuestion.getQuestionContent()))
        {
            question.setQuestionContent(theQuestion.getQuestionContent());
        }
        if(Objects.nonNull(theQuestion.getCategory()))
        {
            question.setCategory(theQuestion.getCategory());
        }
        if(Objects.nonNull(theQuestion.getDifficulty()))
        {
            question.setDifficulty(theQuestion.getDifficulty());
        }
        // Multianswer: restore data for answer's quantity
        if(Objects.nonNull(theQuestion.getMultianswer()))
        {
            question.setMultianswer(theQuestion.getMultianswer());
        }
        if(Objects.nonNull(theQuestion.getQuestionAnswers()))
        {
            List<QuestionAnswer> originalList = question.getQuestionAnswers();
            List<QuestionAnswerDTO> updatedList = theQuestion.getQuestionAnswers();
            // if update size is smaller, it is ok but what if the update size is larger
            for(int i = 0; i < Math.max(updatedList.size(), originalList.size()); i++)
            {
                // handle case that user add more answers
                if(i >= originalList.size())
                {
                    question.addQuestionAnswer(QuestionAnswer.builder()
                                    .answer(updatedList.get(i).getAnswer())
                            .build());
                    continue;
                }
                // handle case that user remove some previous answers
                if(i >= updatedList.size())
                {
                    question.deleteQuestionAnswer(originalList.get(i));
                    continue;
                }
                QuestionAnswer answer = originalList.get(i);
                QuestionAnswerDTO dto = updatedList.get(i);
                answer.setAnswer(dto.getAnswer());

            }
        }
        return question;
    }

    @Override
    public Question findQuestionById(int id) {
        Optional<Question> result = questionRepository.findById(id);

        Question question = null;
        if(result.isPresent())
        {
            question = result.get();
        }else {
            // Exception handle later
            throw new RuntimeException("Did not find employee id - " + id);
        }
        return question;

    }

    @Override
    @Transactional
    public Question deleteQuestionById(int id) throws QuestionNotFoundException {
        Optional<Question> deletedQuestion = questionRepository.findById(id);
        if(deletedQuestion.isEmpty())
        {
            throw new QuestionNotFoundException("Question with id " + id + "not found!");
        }
        Question question = deletedQuestion.get();
        questionRepository.deleteById(id);
        return question;
    }

    @Override
    public List<Question> getQuestionsByCategory(String category) {
        TypedQuery<Question> query = entityManager.createQuery("select a from Question a where a.category = :data", Question.class);
        query.setParameter("data", category);
        List<Question> result = query.getResultList();
        return result;
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public List<Question> getQuestionsByQuizzId(int quizzId) throws QuizzNotFoundException {
        Optional<Quizz> quizz = quizzRepository.findById(quizzId);
        if(quizz.isEmpty())
        {
            throw new QuizzNotFoundException("Quizz with id " + quizzId + "not found");
        }
        List<Question> questions = questionRepository.findQuestionsByQuizzesId(quizzId);
        return questions;
    }

    @Override
    public Page<Question> getAllQuestionWithSortingAndPaginationAndFilter(String category, Integer difficulty, String field, Integer page, Integer pageSize, String order) {
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
        Page<Question> questions;
        if (category != null && difficulty != null) {
            questions = questionRepository.findByCategoryAndDifficulty(category, difficulty, pageable);
        } else if (category != null) {
            questions = questionRepository.findByCategory(category, pageable);
        } else if (difficulty != null) {
            questions = questionRepository.findByDifficulty(difficulty, pageable);
        } else {
            questions = questionRepository.findAll(pageable);
        }
        return questions;
    }


}
