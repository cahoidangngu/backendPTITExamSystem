package com.quanchun.backendexamsystem.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.yaml.snakeyaml.events.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "register_quizzes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterQuizz {
    @Id
    @Column(name = "register_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int registerId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quizz_id")
    private Quizz quizz;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "status")
    private int status;
    @Column(name = "score")
    private int score;

    @Column(name = "started_time")
    private Date startedTime;

    @Column(name = "finished_time")
    private Date finishedTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "begin_time")
    private Date beginTime;

    @OneToMany(
           cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "register_quizz_id")
    private List<ParticipantAnswer> participantAnswerList;

    public RegisterQuizz(Quizz quizz, User user, int status, int score, Date startedTime, Date finishedTime)
    {
        this.status = status;
        this.score = score;
        this.startedTime = startedTime;
        this.finishedTime = finishedTime;
        participantAnswerList = new ArrayList<>();
    }

    public void addParticipantAnswer(ParticipantAnswer participantAnswer){
        if(participantAnswerList==null) participantAnswerList = new ArrayList<>();
        participantAnswerList.add(participantAnswer);
    }

}
