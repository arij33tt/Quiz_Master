package com.QuizMaster.AdminServ.Controllers;

import com.QuizMaster.AdminServ.DBCalls.AttemptRepository;
import com.QuizMaster.AdminServ.DBCalls.AttemptServiceDB;
import com.QuizMaster.AdminServ.DBCalls.QuestionRepository;
import com.QuizMaster.AdminServ.DTO.AttemptDTO;
import com.QuizMaster.AdminServ.DTO.QuestionDTO;
import com.QuizMaster.AdminServ.DTO.QuizDTO;
import com.QuizMaster.AdminServ.Questions.Question;
import com.QuizMaster.AdminServ.Questions.QuestionCreationService;
import com.QuizMaster.AdminServ.Quizs.Quiz;
import com.QuizMaster.AdminServ.Quizs.QuizCreationService;
import com.QuizMaster.AdminServ.Quizs.QuizViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HomeController {

// These are uploading the quiz and questions services
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

//--------------------------------------------------------------------------------------------------
//These are the viewing and dashboard service

    @Autowired
    QuizViewService quizViewService;
    @PostMapping("/admin/dashboard")
    public List<Quiz> adminDashboard(){
        return quizViewService.view();
    }

    @Autowired
    AttemptServiceDB attemptServiceDB;
    @PostMapping("/admin/{quizID}")
    public List<AttemptDTO> attemptsInQuiz(@PathVariable Long quizID){

        return attemptServiceDB.loadAttempts(quizID);
    }

    @Autowired
    QuestionRepository questionRepository;
    @PostMapping("/admin/{attemptID}")
    public List<Question> questionsInThis(@PathVariable Long attemptID){
        return questionRepository.loadQuestions(attemptID);
    }



}
