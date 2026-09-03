package com.QuizMaster.UserServ.Services;

import com.QuizMaster.UserServ.Attempts.Attempt;
import com.QuizMaster.UserServ.DB.QuizServiceDB;
import com.QuizMaster.UserServ.DTO.AttemptDTO;
import com.QuizMaster.UserServ.DTO.QuestionDTO;
import com.QuizMaster.UserServ.Quizs.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuizService {

    public QuestionDTO startTheQuiz(AttemptDTO attemptDTO){

        // userId and question id will scan the attempt db and check of time left >0
        //and attempt >0, if not found return true, if found but two cond are true
        // return true
        //else return false
        isThisFresh(attemptDTO.getQuizID());
        return null;
    }


    Optional<Attempt> isThisFresh(long quizID){
        return
    }

}
