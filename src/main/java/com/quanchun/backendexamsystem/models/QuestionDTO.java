package com.quanchun.backendexamsystem.models;

import com.quanchun.backendexamsystem.entities.QuestionAnswer;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDTO {
    @NotBlank
    private String questionContent;
    private int difficulty;
    private int multianswer;
    private int correctedAnswer;
    @NotBlank
    private String category;
    private List<QuestionAnswerDTO> questionAnswers;

}
