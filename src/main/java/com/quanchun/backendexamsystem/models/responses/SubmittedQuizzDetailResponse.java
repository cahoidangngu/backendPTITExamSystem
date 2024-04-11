package com.quanchun.backendexamsystem.models.responses;

import com.quanchun.backendexamsystem.models.SubmittedUserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmittedQuizzDetailResponse {
    private double avgScore;
    private double avgTakeExam;
    private List<SubmittedUserDTO> submittedUserDTOS;

    public void addSumittedUser(SubmittedUserDTO userDTO)
    {
        if(submittedUserDTOS == null) submittedUserDTOS = new ArrayList<>();
        submittedUserDTOS.add(userDTO);
    }
}
