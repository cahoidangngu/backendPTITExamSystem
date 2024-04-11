package com.quanchun.backendexamsystem.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({"beginTime", "finishTime", "userAnswers"})
public class SubmitQuizzDTO {
    @JsonProperty("beginTime")
    @NotNull
    private Date beginTime;

    @JsonProperty("finishTime")
    @NotNull
    private Date finishTime;

    @JsonProperty("userAnswers")
    @Valid
    private List<UserAnswerDTO> userAnswerDTOList = new ArrayList<>();
}
