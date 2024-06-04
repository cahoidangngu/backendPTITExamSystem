package com.quanchun.backendexamsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.cdi.Eager;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "question_bank")
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Integer id;
    @Column(name = "question_content")
    private String questionContent;
    @Column(name = "difficulty")
    private Integer difficulty;
    @Column(name = "multi_answer")
    private Integer multianswer;
    @Column(name = "correct_answer")
    private Integer correctedAnswer;

    @ManyToMany(
            fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}
    )
    @JoinTable(
            name ="quizzes_questions",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "quizz_id")
    )
    @JsonIgnore
    private List<Quizz> quizzes;

    // nên tách thành 1 entity. Tạm thời cho thành 1 trường
    @Column(name = "category")
    private String category;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(
            name = "question_id"
    )
    private List<QuestionAnswer> questionAnswers;

    public void addQuizz(Quizz quizz)
    {
        if(quizzes == null)
        {
            quizzes = new ArrayList<>();
        }
        quizzes.add(quizz);
    }

    public void addQuestionAnswer(QuestionAnswer answer)
    {
        if(questionAnswers == null)
        {
            questionAnswers = new ArrayList<>();
        }
        questionAnswers.add(answer);
    }
    public void deleteQuestionAnswer(QuestionAnswer answer)
    {
        if(questionAnswers == null)
        {
            questionAnswers = new ArrayList<>();
            return;
        }
        if(questionAnswers.size() >= 1)
        {
            questionAnswers.remove(answer);
        }

    }
    public List<QuestionAnswer> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(List<QuestionAnswer> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }
}
