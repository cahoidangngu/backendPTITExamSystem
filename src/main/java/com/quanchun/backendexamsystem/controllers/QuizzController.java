package com.quanchun.backendexamsystem.controllers;

import com.quanchun.backendexamsystem.entities.Quizz;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.models.QuestionDTO;
import com.quanchun.backendexamsystem.models.QuizzDTO;
import com.quanchun.backendexamsystem.services.QuizzService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class QuizzController {
    @Autowired
    private QuizzService quizzService;

    // test ok for admin only
    @GetMapping("/quizzes")
    public ResponseEntity<List<Quizz>> getAllQuizzes()
    {
        List<Quizz> quizzes = new ArrayList<>();
        quizzService.getAllQuizzes().forEach(quizzes::add);
        if(quizzes.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    // test ok
    @GetMapping("/quizzes/{id}")
    public ResponseEntity<Quizz> getQuizzById(@PathVariable("id") int id)
    {
        return new ResponseEntity<>(quizzService.findQuizzById(id), HttpStatus.OK) ;
    }

    // test ok
    // for each teacher
    @GetMapping("/quizzes/filter")
    public List<Quizz> getQuizzesByFilter(@RequestParam(required = false) @Min(1) @Max(3) Integer difficulty, @RequestParam Integer hostId)
    {
        List<Quizz> quizzes = new ArrayList<>();
        if (difficulty != null)
        {
            Map<Quizz, Integer> mp = new LinkedHashMap<>();
            quizzService.getQuizzesByHostId(hostId).forEach(quizz -> {
                if(!mp.containsKey(quizz)) mp.put(quizz, 1);
                else {
                    mp.put(quizz, mp.get(quizz) + 1);
                }
            });
            quizzService.getQuizzesByDifficulty(difficulty).forEach(quizz -> {
                if(mp.get(quizz) >= 1)
                {
                    quizzes.add(quizz);
                }
            });
        }
        else
        {
            quizzService.getQuizzesByHostId(hostId).forEach(quizzes::add);
        }
        return quizzes;
    }

    // test ok
    // role teacher & admin
    @PostMapping("/quizzes")
    public ResponseEntity<Quizz> addNewQuizz(@RequestBody QuizzDTO quizzDTO)
    {
        return new ResponseEntity<>(quizzService.addQuizz(quizzDTO), HttpStatus.CREATED);
    }

    // test ok
    @PutMapping("/quizzes/{id}")
    public ResponseEntity<Quizz> updatedQuizz(@PathVariable("id") int id,@RequestBody QuizzDTO updatedQuizz) throws QuizzNotFoundException {
        return new ResponseEntity<>(quizzService.updateQuizzById(id, updatedQuizz), HttpStatus.OK);
    }

    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity<Quizz> deletedQuizz(@PathVariable("id") int id) throws QuizzNotFoundException {
        quizzService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
