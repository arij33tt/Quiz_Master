package com.QuizMaster.UserServ.Services;

import com.QuizMaster.UserServ.DB.AttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttemptService {

    @Autowired
    AttemptRepository attemptRepository;

    public void handleTime(){
        attemptRepository.closeExp();
    }

}
