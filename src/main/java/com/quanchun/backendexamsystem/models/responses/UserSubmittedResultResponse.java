package com.quanchun.backendexamsystem.models.responses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSubmittedResultResponse {
    @NotBlank
    private String quizzTitle;
    private Date startedTime;
    private Date finishedTime;
    @NotNull
    private int score;
}
