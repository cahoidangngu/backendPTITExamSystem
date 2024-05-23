package com.quanchun.backendexamsystem.models.responses;


import com.quanchun.backendexamsystem.models.QuestionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseQuizDTO {
    private int id;
    private int hostId;
    private String title;
    private String description;
    private int type;
    private String subject;
    private int duration;
    private List<QuestionDTO> questionList;

    public void addResponseQuestionItem (QuestionDTO questionDTO){
        if(questionList == null)  questionList = new ArrayList<>();
        questionList.add(questionDTO);
    }
}
