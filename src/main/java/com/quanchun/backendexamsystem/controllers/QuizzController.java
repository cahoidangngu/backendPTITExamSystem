package com.quanchun.backendexamsystem.controllers;

import com.quanchun.backendexamsystem.entities.Question;
import com.quanchun.backendexamsystem.entities.Quizz;
import com.quanchun.backendexamsystem.error.QuizzExistsException;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.models.QuestionDTO;
import com.quanchun.backendexamsystem.models.UserDTO;
import com.quanchun.backendexamsystem.models.responses.QuizDTO;
import com.quanchun.backendexamsystem.models.responses.SubmittedQuizzDetailResponse;
import com.quanchun.backendexamsystem.services.QuestionService;
import com.quanchun.backendexamsystem.services.QuizzService;
import com.quanchun.backendexamsystem.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuizzController {
    @Autowired
    QuestionService questionService;
    @Autowired
    private QuizzService quizzService;
    @Autowired
    private StatisticsService statisticsService;

    // test ok for admin only
    @GetMapping("/quizzes")
    public ResponseEntity<List<QuizDTO>> getAllQuizzes(@RequestParam(required = false) Integer page,
                                                       @RequestParam(required = false) Integer pageSize,
                                                       @RequestParam(required = false) String field,
                                                       @RequestParam(required = false) Integer difficulty,
                                                       @RequestParam(required = false) String dateOrder,
                                                       @RequestParam(required = false) String sort)
    {

//        return new ResponseEntity<>(quizzService.getQuizzesWithSortingAndPagingAndFilter(field, sort, page, pageSize, difficulty, dateOrder), HttpStatus.OK);
        return new ResponseEntity<>( quizzService.getAllQuizzes(), HttpStatus.OK);
    }

    // test ok
    @GetMapping("/quizzes/{id}")
    public ResponseEntity<QuizDTO> getQuizzById(@PathVariable("id") Integer id) throws QuizzNotFoundException
    {
        return new ResponseEntity<>(quizzService.toQuizDTO( quizzService.findQuizzById(id)), HttpStatus.OK) ;
    }



    // test ok
    @PostMapping("quizzes/add")
    public ResponseEntity<Quizz> addNewQuizz(@RequestBody QuizDTO quizzDTO) throws QuizzExistsException
    {
        return new ResponseEntity<>(quizzService.addQuizz(quizzDTO), HttpStatus.CREATED);
    }

    // test ok
    @PutMapping("/quizzes/{id}/update")
    public ResponseEntity<QuizDTO> updatedQuizz(@PathVariable("id") Integer id,@RequestBody QuizDTO updatedQuiz) throws QuizzNotFoundException {
        return new ResponseEntity<>(quizzService.updateQuizzById(id, updatedQuiz), HttpStatus.OK);
    }

    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable("id") Integer id) throws QuizzNotFoundException {
            quizzService.deleteById(id);
            return ResponseEntity.ok().body("Quiz successfully deleted.");
    }
    // the same :))
    @PostMapping("/add-question/{id}")
    public ResponseEntity<Quizz> addQuestion2Quizz(@PathVariable("id") Integer id, @RequestBody List<QuestionDTO> questionDTOS) throws QuizzNotFoundException {
        return new ResponseEntity<>(quizzService.addQuestions(id, questionDTOS), HttpStatus.OK) ;
    }
    @PostMapping("/quizzes/{quizzId}/add-question")
    public ResponseEntity<Question> addQuestion2Quizz(@PathVariable("quizzId") Integer quizzId, @RequestBody Question question) throws QuizzNotFoundException {
        return new ResponseEntity<>(questionService.addQuestion2Quizz(quizzId, question), HttpStatus.CREATED);
    }
    //
    // test ok
    @GetMapping("quizzes/{quizzId}/users")
    public ResponseEntity<List<UserDTO>> getUserByQuizzId(@PathVariable("quizzId") Integer quizzId) throws QuizzNotFoundException {
        return new ResponseEntity<>(quizzService.getUsersByQuizzesId(quizzId), HttpStatus.OK);
    }

    // test ok
    @GetMapping("quizzes/{quizzId}/statistics")
    public ResponseEntity<SubmittedQuizzDetailResponse> getQuizzStatistics(@PathVariable("quizzId") Integer quizzId) throws QuizzNotFoundException {
        return new ResponseEntity<>(statisticsService.getDetailResult(quizzId), HttpStatus.OK);
    }

}
