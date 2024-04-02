package com.quanchun.backendexamsystem.controllers;

import com.quanchun.backendexamsystem.entities.Question;
import com.quanchun.backendexamsystem.error.QuestionNotFoundException;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.models.QuestionDTO;
import com.quanchun.backendexamsystem.services.QuestionService;
import com.quanchun.backendexamsystem.services.QuizzService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Question>  getAllQuestions()
    {
        return  questionService.getAllQuestions();
    }
    @PostMapping("/questions")
    public ResponseEntity<Question> addQuestion(@RequestBody QuestionDTO questionDTO)
    {
        return  new ResponseEntity<>(questionService.addQuestion(questionDTO), HttpStatus.CREATED);
    }
    @GetMapping("/quizzes/{quizzId}/questions")
    public ResponseEntity<List<Question>> getAllQuestionsByQuizzId(@PathVariable("quizzId") int id) throws QuizzNotFoundException {
        List<Question> questions = new ArrayList<>();
        questionService.getQuestionsByQuizzId(id).forEach(questions::add);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
    @PostMapping("/quizzes/{quizzId}/questions")
    public ResponseEntity<Question> addQuestion2Quizz(@PathVariable("quizzId") int quizzId, @RequestBody Question question) throws QuizzNotFoundException {
        return new ResponseEntity<>(questionService.addQuestion2Quizz(quizzId, question), HttpStatus.CREATED);
    }
    // test ok
    @GetMapping("/filter")
    public List<Question> getQuestionsByFilter(@RequestParam String category)
    {
        return questionService.getQuestionsByCategory(category);
    }
    // test ok
    @GetMapping("questions/{id}")
    public Question getQuestionById(@PathVariable("id") int id)
    {
        return questionService.findQuestionById(id);
    }
    // test ok
    @DeleteMapping("/deleted/{id}")
    public ResponseEntity<Question> deleteQuestionById(@PathVariable("id") int id) throws QuestionNotFoundException {
        return new ResponseEntity<>(questionService.deleteQuestionById(id), HttpStatus.OK);
    }
    // test ok
    @PostMapping("/new-question")
    public Question addNewQuestion(@RequestBody QuestionDTO questionDTO)
    {
        return questionService.addQuestion(questionDTO);
    }
    // test ok
    @PutMapping("/questions/{id}")
    public ResponseEntity<Question> updateQuestionById(@PathVariable("id") int id, @RequestBody QuestionDTO questionDTO) throws QuestionNotFoundException {
        return new ResponseEntity<>(questionService.updateQuestionById(id, questionDTO), HttpStatus.OK);
    }
}
