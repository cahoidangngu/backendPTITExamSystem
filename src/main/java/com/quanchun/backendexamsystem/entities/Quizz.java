package com.quanchun.backendexamsystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quizz")
@Builder
public class Quizz {
    @Column(name = "quizz_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "host_id")
    private int hostId;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "difficulty")
    private int difficulty;
    @Column(name = "created_at")
    @NotNull
    private Date createdAt;
    @Column(name = "started_at")
    private Date startedAt;
    @Column(name = "ended_at")
    private Date endedAt;
    @Column(name = "score")
    private int score;
    @Column(name = "type")
    private int type;

    @OneToMany(mappedBy = "quizz")
    private Set<RegisterQuizz> registerQuizzes;

    @ManyToMany(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}
    )
    @JoinTable(
            name = "quizzes_questions",
            joinColumns = @JoinColumn(
                    name = "quizz_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "question_id")
    )
    private List<Question> questions;

    public void addQuestion(Question question)
    {
        if(questions == null)
        {
            questions = new ArrayList<>();
        }
        questions.add(question);
    }
}
