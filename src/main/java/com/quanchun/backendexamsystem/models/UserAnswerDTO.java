package com.quanchun.backendexamsystem.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.processing.Generated;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "questionId",
        "userAnswer"
})
@Generated("jsonschema2pojo")
public class UserAnswerDTO {
    @NotNull
    private int questionId;
    @NotNull
    private int userAnswer;

}
