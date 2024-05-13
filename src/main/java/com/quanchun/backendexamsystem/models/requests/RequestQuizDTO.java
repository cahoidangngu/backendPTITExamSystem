package com.quanchun.backendexamsystem.models.requests;

import com.quanchun.backendexamsystem.models.QuestionDTO;

import java.util.ArrayList;
import java.util.List;

public class RequestQuizDTO {
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
