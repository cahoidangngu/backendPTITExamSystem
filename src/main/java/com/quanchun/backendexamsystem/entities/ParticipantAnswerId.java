package com.quanchun.backendexamsystem.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ParticipantAnswerId implements Serializable {

    @Column(name = "participant_attempt_id")
    private int participantAttemptId;
    @Column(name = "question_bank_id")
    private int questionId;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantAnswerId participantAnswerId = (ParticipantAnswerId) o;
        return (participantAttemptId == (((ParticipantAnswerId) o).getParticipantAttemptId())) &&
                (questionId == ((ParticipantAnswerId) o).getQuestionId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(participantAttemptId, questionId);
    }

}
