package com.QuizMaster.UserServ.Controllers;

import com.QuizMaster.UserServ.Auth.SecurityService;
import com.QuizMaster.UserServ.Auth.User;
import com.QuizMaster.UserServ.Auth.UserRepository;
import com.QuizMaster.UserServ.DB.AttemptRepository;
import com.QuizMaster.UserServ.DB.AttemptServiceDB;
import com.QuizMaster.UserServ.DB.QuizRepositories;
import com.QuizMaster.UserServ.DTO.AttemptDTO;
import com.QuizMaster.UserServ.DTO.QuestionDTO;
import com.QuizMaster.UserServ.Questions.Question;
import com.QuizMaster.UserServ.Quizs.Quiz;
import com.QuizMaster.UserServ.Services.AttemptService;
import com.QuizMaster.UserServ.Services.QuizService;
import com.QuizMaster.UserServ.Services.SavingService;
import com.QuizMaster.UserServ.Services.SendingNextQuestionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
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
    AttemptServiceDB attemptServiceDB;
    @Autowired
    SecurityService securityService;
    @PostMapping("/user/attempts")
    public List<AttemptDTO> userAttempts() {
        return attemptServiceDB.loadAttempts(
                securityService.getCurrentUserId()
        );
    }

    @Autowired
    AttemptRepository attemptRepository;

    @Transactional
    @GetMapping("/quiz/{attemptID}/heartbeat")
    public void senseHeartbeat(@PathVariable Long attemptID){

        attemptRepository.update(attemptID);
    }



    //view controlls

    @Autowired
    QuizRepositories quizRepositories;

    @PostMapping("/user/dashboard")
    public List<Quiz> userDashboard(@AuthenticationPrincipal UserDetails userDetails){
        List<Quiz>quizzes= quizRepositories.loadUpcoming(userDetails.getUsername());

        System.out.println("QUIZZES RETURNED = " + quizzes.size());

        for (Quiz q : quizzes) {
            System.out.println(
                    "Quiz ID: " + q.getQuizID() +
                            ", Topic: " + q.getTopicId() +
                            ", Attempts: " + q.getAttempts()
            );
        }
        return quizzes;
    }


    //auth controller
    @Autowired
    UserRepository userRepository;
    @PostMapping("/register")
    public void newUser(@RequestBody User newUser){
        userRepository.save(newUser);
    }
}
