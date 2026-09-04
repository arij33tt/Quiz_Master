package com.QuizMaster.UserServ.Controllers;

import com.QuizMaster.UserServ.DB.AttemptRepository;
import com.QuizMaster.UserServ.DTO.AttemptDTO;
import com.QuizMaster.UserServ.DTO.QuestionDTO;
import com.QuizMaster.UserServ.Questions.Question;
import com.QuizMaster.UserServ.Services.AttemptService;
import com.QuizMaster.UserServ.Services.QuizService;
import com.QuizMaster.UserServ.Services.SavingService;
import com.QuizMaster.UserServ.Services.SendingNextQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;

@RestController
public class HomeController {

    @Autowired
    QuizService quizService;


    @PostMapping("/start/{quizID}")
    public Long startTheQuiz(@PathVariable Long quizID){// a dto is expected here
// the dto we are using here is purely to pass the quizID
        // a service to start the quiz
        return quizService.startTheQuiz(quizID);
        // if attemptID is -1 then the test is completed
        // to be handled by frontend
    }

    @Autowired
    SavingService savingService;
    @Autowired
    SendingNextQuestionService sendingNextQuestionService;

    @PostMapping("/quiz/{attemptID}")
    public QuestionDTO theQuiz(@PathVariable Long attemptID,@RequestBody QuestionDTO previousQuestion){

        savingService.saveThis(previousQuestion,attemptID);
        Optional<QuestionDTO> nextQuestion=sendingNextQuestionService.sendNext(attemptID);
        if(nextQuestion.isEmpty())return null;
        return nextQuestion.get();
    }


    @Autowired
    AttemptRepository attemptRepository;

    @GetMapping("/quiz/{attemptID}/heartbeat")
    public void senseHeartbeat(@PathVariable Long attemptID){
        attemptRepository.update(attemptID);
    }
}
