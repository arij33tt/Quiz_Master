package com.QuizMaster.UserServ.Controllers;

import com.QuizMaster.UserServ.DTO.AttemptDTO;
import com.QuizMaster.UserServ.DTO.QuestionDTO;
import com.QuizMaster.UserServ.Questions.Question;
import com.QuizMaster.UserServ.Services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    QuizService quizService;


    @PostMapping("/start")
    public QuestionDTO startTheQuiz(@RequestBody AttemptDTO attemptDTO){// a dto is expected here

        QuestionDTO questionDTO=quizService.startTheQuiz(attemptDTO);
        // a service to start the quiz
        return null;
    }

}
