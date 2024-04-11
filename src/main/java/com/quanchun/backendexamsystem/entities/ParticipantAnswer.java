package com.quanchun.backendexamsystem.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(ParticipantAnswerId.class)
public class ParticipantAnswer {
    @Id
    @Column(name = "question_id")
    private int questionId;

    @Id
    @Column(name = "register_quizz_id")
    private int registerQuizzId;

    @NotNull
    private int userAnswer;
}
