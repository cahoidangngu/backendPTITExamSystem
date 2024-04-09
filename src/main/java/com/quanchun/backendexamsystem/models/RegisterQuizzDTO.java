package com.quanchun.backendexamsystem.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RegisterQuizzDTO {
    @NotNull
    @NotBlank
    private String quizzTitle;
    private Date startedTime;
    private Date finishedTime;
    private Date endTime;
    private Date beginTime;

    @NotNull
    @NotBlank
    private String userFullName;
    private String studyClass;
    private int status;
    private int score;
}
