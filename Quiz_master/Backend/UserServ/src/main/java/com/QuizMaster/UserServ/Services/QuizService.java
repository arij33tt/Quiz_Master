package com.QuizMaster.UserServ.Services;

import com.QuizMaster.UserServ.Attempts.Attempt;
import com.QuizMaster.UserServ.Auth.SecurityService;
import com.QuizMaster.UserServ.DB.AttemptRepository;
import com.QuizMaster.UserServ.DB.AttemptServiceDB;
import com.QuizMaster.UserServ.DB.QuizServiceDB;
import com.QuizMaster.UserServ.DTO.AttemptDTO;
import com.QuizMaster.UserServ.DTO.QuestionDTO;
import com.QuizMaster.UserServ.Quizs.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Service
public class QuizService {

    private boolean testCompleted=false;
    public Long startTheQuiz(Long quizID){

        // userId and question id will scan the attempt db and check of time left >0
        //and attempt >0, if not found return true, if found but two cond are true
        // return true
        //else return false
        Long curr= isThisFresh(quizID);
        return curr;
    }


    @Autowired
    ReadyingNewAttempt readyingNewAttempt;

    @Autowired
    AttemptRepository attemptRepo;
    @Autowired
    AttemptServiceDB attemptServ;
@Autowired
    SecurityService securityService;
    Long isThisFresh(long quizID){
        if(!attemptServ.isActive(quizID, securityService.getCurrentUserId())){
            // redirect to quiz page
            // do we have any attempts left
            testCompleted=true;
            return (long) -1;//*TEST COMPLETED
        }
        //other null means start a new test,
        Optional<Attempt>lastAttempt=attemptServ.isLastExp(quizID, securityService.getCurrentUserId());
        if(lastAttempt.isEmpty())return readyingNewAttempt.newAttemptInit(quizID);
        Instant last=lastAttempt.get().getLastActivity();
        Instant curr= Instant.now();
        Duration duration= Duration.between(last,curr);
        int idleFor= Math.toIntExact(duration.toMinutes());
        if(idleFor>10){
            lastAttempt.ifPresent(
                    attempt ->{
                            attempt = lastAttempt.get();
                        attempt.setHasSubmitted(true);
                        attemptRepo.save(attempt);

            });
//            Attempt temp=new Attempt(quizID,"username");
            return (readyingNewAttempt.newAttemptInit(quizID));

        }
        return lastAttempt.get().getAttemptID();

    }

}
