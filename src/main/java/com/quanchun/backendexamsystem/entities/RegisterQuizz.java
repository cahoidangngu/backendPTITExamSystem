package com.quanchun.backendexamsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.yaml.snakeyaml.events.Event;

import java.util.Date;

@Entity
@Table(name = "register_quizzes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterQuizz {
    @Id
    @Column(name = "register_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int registerId;

    @ManyToOne
    @JoinColumn(name = "quizz_id")
    Quizz quizz;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "status")
    private int status;
    @Column(name = "score")
    private int score;

    @Column(name = "started_time")
    private Date startedTime;

    @Column(name = "finished_time")
    private Date finishedTime;

    public RegisterQuizz(Quizz quizz, User user, int status, int score, Date startedTime, Date finishedTime)
    {
        this.status = status;
        this.score = score;
        this.startedTime = startedTime;
        this.finishedTime = finishedTime;
    }
}
