package com.QuizMaster.AdminServ.DBCalls;

import com.QuizMaster.AdminServ.Questions.Question;
import com.QuizMaster.AdminServ.Quizs.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceDB {

    @Autowired
    QuestionRepository questionRepo;

    public ResponseEntity<Object> saveThisQuestion(Question question){
        System.out.println(question.toString());
            Question savedQuestion = questionRepo.save(question);
            // Return 201 Created along with the saved question
        System.out.println(savedQuestion.getQuestionID());

        return ResponseEntity.status(HttpStatus.CREATED).body(savedQuestion);


    }



}
