package com.quanchun.backendexamsystem.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.quanchun.backendexamsystem.models.QuestionAnswerDTO;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"questionContent", "difficulty", "multianswer", "correctAnswer", "userAnswer", "category", "questionAnswers"})
public class ResponseAnswerQuestionDTO {
    @JsonProperty("questionContent")
    @NotBlank
    private String questionContent;

    @JsonProperty("difficulty")
    private int difficulty;

    @JsonProperty("multianswer")
    private int multianswer;

    @JsonProperty("correctAnswer")
    @NotBlank
    private int correctedAnswer;

    @JsonProperty("userAnswer")
    @NotBlank
    private int userAnswer;

    @JsonProperty("category")
    @NotBlank
    private String category;

    @JsonProperty("questionAnswers")
    private List<QuestionAnswerDTO> questionAnswers;
}
