package com.QuizMaster.UserServ.Controllers;

import com.QuizMaster.UserServ.DTO.AttemptDTO;
import com.QuizMaster.UserServ.DTO.QuestionDTO;
import com.QuizMaster.UserServ.Questions.Question;
import com.QuizMaster.UserServ.Services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    QuizService quizService;


    @PostMapping("/start/{quizID}")
    public AttemptDTO startTheQuiz(@PathVariable Long quizID){// a dto is expected here
// the dto we are using here is purely to pass the quizID
        // a service to start the quiz
        return quizService.startTheQuiz(quizID);
        // if attemptID is -1 then the test is completed
        // to be handled by frontend
    }

}
