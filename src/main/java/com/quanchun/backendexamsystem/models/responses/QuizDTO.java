package com.quanchun.backendexamsystem.models.responses;


import com.quanchun.backendexamsystem.models.QuestionDTO;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class QuizDTO {
    private int id;
    private int hostId;
    private String title;
    private String description;
    private int type;
    private String subject;
    private Date createdAt;
    private Date startedAt;
    private Date endedAt;
    private int difficulty;
    private int duration;
    private List<QuestionDTO> questionList;

    public void addResponseQuestionItem (QuestionDTO questionDTO){
        if(questionList == null)  questionList = new ArrayList<>();
        questionList.add(questionDTO);
    }
}
