package com.quanchun.backendexamsystem.models;


import com.quanchun.backendexamsystem.entities.Question;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizzDTO {
    private int id;
    @NotBlank
    private int hostId;
    @NotBlank
    private String title;
    @NotBlank
    private String description;

    private Integer difficulty;
    @NotBlank
    private Date createdAt;
    private Date startedAt;
    private Date endedAt;
    private Integer score;
    private Integer type;
    private List<Question> questions;
}
