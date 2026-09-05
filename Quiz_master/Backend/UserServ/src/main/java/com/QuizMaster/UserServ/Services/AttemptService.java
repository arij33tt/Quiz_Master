package com.QuizMaster.UserServ.Services;

import com.QuizMaster.UserServ.DB.AttemptRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttemptService {

    @Autowired
    AttemptRepository attemptRepository;

    @Transactional
    public void handleTime(){
        attemptRepository.closeExp();
    }

}
