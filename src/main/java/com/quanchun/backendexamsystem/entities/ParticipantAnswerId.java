package com.quanchun.backendexamsystem.entities;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantAnswerId implements Serializable {

    @Column(name = "register_question_id")
    private int registerQuizzId;
    @Column(name = "question_bank_id")
    private int questionId;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantAnswerId participantAnswerId = (ParticipantAnswerId) o;
        return (registerQuizzId == (((ParticipantAnswerId) o).getRegisterQuizzId())) &&
                (questionId == ((ParticipantAnswerId) o).getQuestionId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(registerQuizzId, questionId);
    }

}
