package com.quanchun.backendexamsystem.models.responses;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantAttemptResponseDTO {
    @NotNull
    private int participantAttemptId;

    @NotNull
    private String userFullName;

    @NotNull
    private String studyClass;

    @NotNull
    @NotBlank
    private String quizTitle;
    private int quizDifficulty;
    private int quizType;
    private Date beginTime;
    private Date endTime;

    private Date startedTime;
    private Date finishedTime;

    @NotNull
    @NotBlank
    private int status;
    private double score;
    private List<ResponseAnswerQuestionDTO> responseAnswerQuestionDTOList;

    public void addQuizQuestion(ResponseAnswerQuestionDTO responseAnswerQuestionDTO){
        if(responseAnswerQuestionDTOList == null) responseAnswerQuestionDTOList = new ArrayList<>();
        responseAnswerQuestionDTOList.add(responseAnswerQuestionDTO);
    }
}

