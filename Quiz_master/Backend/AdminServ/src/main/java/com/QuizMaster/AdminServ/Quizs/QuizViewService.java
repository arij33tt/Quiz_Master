package com.QuizMaster.AdminServ.Quizs;

import com.QuizMaster.AdminServ.DBCalls.QuizRepository;
import com.QuizMaster.AdminServ.DBCalls.QuizServiceDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizViewService {

    @Autowired
    QuizServiceDB quizServiceDB;
    @Autowired
    QuizRepository qr;
    public List<Quiz> view(){
        return qr.findAll();
    }
}
