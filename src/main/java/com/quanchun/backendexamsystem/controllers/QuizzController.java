package com.quanchun.backendexamsystem.controllers;

import com.quanchun.backendexamsystem.entities.Quizz;
import com.quanchun.backendexamsystem.entities.RegisterQuizz;
import com.quanchun.backendexamsystem.error.QuizzNotFoundException;
import com.quanchun.backendexamsystem.error.UserNotFoundException;
import com.quanchun.backendexamsystem.models.QuestionDTO;
import com.quanchun.backendexamsystem.models.QuizzDTO;
import com.quanchun.backendexamsystem.models.UserDTO;
import com.quanchun.backendexamsystem.models.responses.SubmittedQuizzDetailResponse;
import com.quanchun.backendexamsystem.services.QuizzService;
import com.quanchun.backendexamsystem.services.StatisticsService;
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
@RequestMapping("/api/quizz")
public class QuizzController {
    @Autowired
    private QuizzService quizzService;
    @Autowired
    private StatisticsService statisticsService;

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
    // maybe when create a quizz just set some basic feature
    @PostMapping("/add-question/{id}")
    public ResponseEntity<Quizz> addQuestion2Quizz(@PathVariable("id") int id, @RequestBody List<QuestionDTO> questionDTOS) throws QuizzNotFoundException {
        return new ResponseEntity<>(quizzService.addQuestions(id, questionDTOS), HttpStatus.OK) ;
    }

    // test ok
    @GetMapping("/{quizzId}/users")
    public ResponseEntity<List<UserDTO>> getUserByQuizzId(@PathVariable("quizzId") int quizzId) throws QuizzNotFoundException {
        return new ResponseEntity<>(quizzService.getUsersByQuizzesId(quizzId), HttpStatus.OK);
    }

    // test ok
    @GetMapping("/{quizzId}/statistics")
    public ResponseEntity<SubmittedQuizzDetailResponse> getQuizzStatistics(@PathVariable("quizzId") int quizzId) throws QuizzNotFoundException {
        return new ResponseEntity<>(statisticsService.getDetailResult(quizzId), HttpStatus.OK);
    }

}
