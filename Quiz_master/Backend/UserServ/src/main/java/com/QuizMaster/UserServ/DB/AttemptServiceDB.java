package com.QuizMaster.UserServ.DB;

import com.QuizMaster.UserServ.Attempts.Attempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttemptServiceDB {

    @Autowired
    AttemptRepository attemptRepo;

    public boolean isActive(Long quizID, String userID){
        if(attemptRepo.noOfAttemptsLeft(userID,quizID)==0)
            return false;
        return true;
    }
    public Optional<Attempt> isLastExp(Long quizID, String userID){
        return attemptRepo.lastOpenAttempt(quizID, userID);


    }
}
