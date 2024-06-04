package com.quanchun.backendexamsystem.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.quanchun.backendexamsystem.models.UserAnswerDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"startTime", "finishTime", "userAnswers"})
public class ParticipantAttemptRequestDTO {

    @JsonProperty("startTime")
    @NotNull
    private Date startTime;

    @JsonProperty("finishTime")
    @NotNull
    private Date finishTime;

    @JsonProperty("userAnswers")
    @Valid
    private List<UserAnswerDTO> userAnswerDTOList = new ArrayList<>();
}
