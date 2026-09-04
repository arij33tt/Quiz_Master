package com.QuizMaster.UserServ.Services;

import com.QuizMaster.UserServ.Attempts.Attempt;
import com.QuizMaster.UserServ.DB.AttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ClockService {

    @Autowired
    AttemptRepository attemptRepository;
    void clockForAttempt(long attemptID){
        // here we will implement @Schedular and will update the lastActivity of attempt class, withing duration
        // here we have to trigger auto submit, a flag bit , submitted make it true, thats all
        //auto submit can be triggered by when last activity-first activity, equals time limit in mins

    }

}
