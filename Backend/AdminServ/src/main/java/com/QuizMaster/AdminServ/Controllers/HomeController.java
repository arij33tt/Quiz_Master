package com.QuizMaster.AdminServ.Controllers;

import com.QuizMaster.AdminServ.DTO.QuestionDTO;
import com.QuizMaster.AdminServ.DTO.QuizDTO;
import com.QuizMaster.AdminServ.Questions.Question;
import com.QuizMaster.AdminServ.Questions.QuestionCreationService;
import com.QuizMaster.AdminServ.Quizs.QuizCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {


    @Autowired
    QuestionCreationService questionCreationService;
    @PostMapping("/admin/{topicId}")
    public ResponseEntity<Object> addQuestion(@RequestBody QuestionDTO questionDTO, @PathVariable String topicId){
        // possible db error
        //noinspection UnnecessaryLocalVariable
        ResponseEntity<Object> response=questionCreationService.createQuestion(new Question(topicId,questionDTO));
        return response;
    }


    @Autowired
    QuizCreationService quizCreationService;
    @PostMapping("/admin/quiz")
    public ResponseEntity<Object> addQuiz(@RequestBody QuizDTO quizDTO){
        return quizCreationService.createQuiz(quizDTO);
    }


}
