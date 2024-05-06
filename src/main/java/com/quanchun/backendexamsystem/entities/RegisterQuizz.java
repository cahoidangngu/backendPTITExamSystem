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
@ToString
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

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "begin_time")
    private Date beginTime;

    @OneToMany(
           cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "register_quizz_id")
    private List<ParticipantAttempt> participantAttemptList;


    public void addParticipantAttempt(ParticipantAttempt participantAttempt){
        if(participantAttemptList==null) participantAttemptList = new ArrayList<>();
        participantAttemptList.add(participantAttempt);
    }

}
