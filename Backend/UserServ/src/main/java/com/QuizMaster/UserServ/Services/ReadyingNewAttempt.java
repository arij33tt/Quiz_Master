package com.QuizMaster.UserServ.Services;

import com.QuizMaster.UserServ.Attempts.Attempt;
import com.QuizMaster.UserServ.DB.AttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReadyingNewAttempt {

    // we would need quizID, userID, attemptID will auto generate
    //returns the attemptid
    public Long newAttemptInit(Long quizID){

        Attempt attempt=new Attempt(quizID,"username");
        Long attemptID=savingFunc(attempt);
        return attemptID;

    }

    @Autowired
    AttemptRepository attemptRepo;
    public Long savingFunc(Attempt attempt){
        Attempt savedAttempt=attemptRepo.save(attempt);
        return savedAttempt.getAttemptID();
        //looking for mutability of Optional
    }


}
