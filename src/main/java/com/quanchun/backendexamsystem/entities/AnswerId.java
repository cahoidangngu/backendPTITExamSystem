package com.quanchun.backendexamsystem.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerId implements Serializable {

    private int qaId; // answer_id
    private int quId; // question_id

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerId answerId = (AnswerId) o;
        return (quId == answerId.getQuId()) &&
                (qaId == answerId.getQaId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(qaId, quId);
    }

}
