package com.QuizMaster.AdminServ.DBCalls;

import com.QuizMaster.AdminServ.Quizs.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QuizServiceDB {

    @Autowired
    QuizRepository quizRepository;

    public ResponseEntity<Object> saveThisQuiz(Quiz quiz){
        Quiz savedQuiz=quizRepository.save(quiz);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedQuiz);
    }

}
