package com.QuizMaster.AdminServ.Quizs;

import com.QuizMaster.AdminServ.DBCalls.QuizServiceDB;
import com.QuizMaster.AdminServ.DTO.QuizDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizCreationService {

    @Autowired
    QuizServiceDB quizService;
    public ResponseEntity<Object> createQuiz(QuizDTO quizDTO){
        return quizService.saveThisQuiz(new Quiz(quizDTO) );
    }


}
