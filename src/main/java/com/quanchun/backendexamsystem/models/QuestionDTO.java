package com.quanchun.backendexamsystem.models;

import com.quanchun.backendexamsystem.entities.QuestionAnswer;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDTO {
    private int id;
    private String question;
    private int difficulty;
    private int multianswer;
    private int answer;
    private String category;
    private List<OptionAnswerDTO> optionAnswers;

    public void addOptionAnswer(OptionAnswerDTO optionAnswer){
        if(optionAnswers==null) optionAnswers = new ArrayList<>();
        optionAnswers.add(optionAnswer);
    }

}
