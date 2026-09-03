package com.QuizMaster.AdminServ.Controllers;

import com.QuizMaster.AdminServ.DTO.QuestionDTO;
import com.QuizMaster.AdminServ.Questions.Question;
import com.QuizMaster.AdminServ.Questions.QuestionCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {


    @Autowired
    QuestionCreationService questionCreationService;
    @PostMapping("/user/{topicId}")
    public ResponseEntity<Object> addQuestion(@RequestBody QuestionDTO questionDTO, @PathVariable String topicId){

        // possible db error
        questionCreationService.createQuestion(new Question(topicId,questionDTO));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
