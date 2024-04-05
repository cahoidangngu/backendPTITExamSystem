package com.quanchun.backendexamsystem.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmitQuizzDTO {
    @NotNull
    private Date beginTime;
    @NotNull
    private Date finishTime;
    private List<UserAnswerDTO> userAnswerDTOList = new ArrayList();
}
