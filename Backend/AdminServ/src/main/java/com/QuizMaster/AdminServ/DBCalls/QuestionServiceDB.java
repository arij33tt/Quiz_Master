package com.QuizMaster.AdminServ.DBCalls;

import com.QuizMaster.AdminServ.Questions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceDB {

    @Autowired
    QuestionRepository questionRepo;

    public ResponseEntity<Object> saveThisQuestion(Question question){
            Question savedQuestion = questionRepo.save(question);
            // Return 201 Created along with the saved question
            return ResponseEntity.status(HttpStatus.CREATED).body(savedQuestion);


    }

}
