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
@Builder
@AllArgsConstructor
public class ResponseRegisterQuizzDTO {
   private int numberRegisterQuizz;
}
