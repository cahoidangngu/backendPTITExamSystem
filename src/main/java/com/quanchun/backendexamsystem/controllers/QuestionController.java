package com.quanchun.backendexamsystem.controllers;

import com.quanchun.backendexamsystem.entities.Question;
import com.quanchun.backendexamsystem.error.QuestionExistsException;
import com.quanchun.backendexamsystem.error.QuestionNotFoundException;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.models.QuestionDTO;
import com.quanchun.backendexamsystem.services.QuestionService;
import com.quanchun.backendexamsystem.services.QuizzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    // test ok
    @GetMapping("/questions")
    public ResponseEntity<Page<Question>> getAllQuestions(@RequestParam(required = false) String field,
                                                          @RequestParam(defaultValue = "asc", required = false) String order,
                                                          @RequestParam(required = false) Integer page,
                                                          @RequestParam(required = false) Integer pageSize,
                                                          @RequestParam(required = false) String category,
                                                          @RequestParam(required = false) Integer difficulty
    )
    {
        return new ResponseEntity<>(questionService.getAllQuestionWithSortingAndPaginationAndFilter(category, difficulty, field, page, pageSize, order), HttpStatus.OK);
    }
    // test ok
    @PostMapping("/questions/add")
    public ResponseEntity<Question> addQuestion(@RequestBody QuestionDTO questionDTO) throws QuestionExistsException
    {
        return  new ResponseEntity<>(questionService.addQuestion(questionDTO), HttpStatus.CREATED);
    }

    @GetMapping("/questions/quizzes/{quizzId}")
    public ResponseEntity<List<Question>> getAllQuestionsByQuizzId(@PathVariable("quizzId") Integer id) throws QuizzNotFoundException {
        List<Question> questions = new ArrayList<>();
        questionService.getQuestionsByQuizzId(id).forEach(questions::add);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }




    // test ok
    @GetMapping("questions/{id}")
    public Question getQuestionById(@PathVariable("id") Integer id) throws QuestionNotFoundException
    {
        return questionService.findQuestionById(id);
    }

    // test ok
    @DeleteMapping("questions/{id}/delete")
    public ResponseEntity<Question> deleteQuestionById(@PathVariable("id") Integer id) throws QuestionNotFoundException {
        return new ResponseEntity<>(questionService.deleteQuestionById(id), HttpStatus.OK);
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<Question> updateQuestionById(@PathVariable("id") Integer id, @RequestBody QuestionDTO questionDTO) throws QuestionNotFoundException {
        return new ResponseEntity<>(questionService.updateQuestionById(id, questionDTO), HttpStatus.OK);
    }
}
