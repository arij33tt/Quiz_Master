package com.QuizMaster.UserServ.Services;

import com.QuizMaster.UserServ.DB.AttemptRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AttemptSchedular {

    private final AttemptService attemptService;
    public AttemptSchedular(AttemptService attemptService){
    this.attemptService=attemptService;
    }

    @Scheduled(fixedRate = 1000)
    public void checkHeartBeat(){
        attemptService.handleTime();// this is supposed to be updating the lastActivity value, and if the
    }

}
