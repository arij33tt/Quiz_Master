package com.QuizMaster.UserServ.DB;

import com.QuizMaster.UserServ.Quizs.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizServiceDB {

    @Autowired
    QuizRepositories quizRepositories;

    public Optional<Quiz> returnQuizDetails(long quizID){
        return quizRepositories.findById(quizID);

    }

}
