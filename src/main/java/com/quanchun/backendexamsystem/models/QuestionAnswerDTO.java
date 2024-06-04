package com.quanchun.backendexamsystem.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.quanchun.backendexamsystem.entities.Question;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionAnswerDTO {
    private String answer;
}
