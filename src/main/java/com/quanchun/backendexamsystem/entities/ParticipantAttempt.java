package com.quanchun.backendexamsystem.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantAttempt {
    @Id
    @Column(name = "participant_attempt_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int participantAttemptId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "register_quiz_id")
    private RegisterQuizz registerQuiz;

    @NotNull
    @Column(name = "score")
    private double score;


    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "finish_time")
    private Date finishTime;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "participant_attempt_id")
    private List<ParticipantAnswer> participantAnswerList;

    public void addParticipantAnswer(ParticipantAnswer participantAnswer){
        if(participantAnswerList==null) participantAnswerList = new ArrayList<>();
        participantAnswerList.add(participantAnswer);
    }
}
