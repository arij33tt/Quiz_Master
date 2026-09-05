package com.QuizMaster.AdminServ.Questions;

import com.QuizMaster.AdminServ.DBCalls.QuestionServiceDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QuestionCreationService {

    @Autowired
    QuestionServiceDB questionService;
    public ResponseEntity<Object> createQuestion(Question question){
        // here we call the db service
        return questionService.saveThisQuestion(question);
    }
}
