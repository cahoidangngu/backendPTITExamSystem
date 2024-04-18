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
@AllArgsConstructor
@Builder
public class SubmittedUserDTO {
    @NotBlank
    private String userName;
    @NotBlank
    @NotNull
    private Date beginTime;
    @NotNull
    @NotNull
    private Date finishedTime;
    @NotBlank
    private int score;
}
