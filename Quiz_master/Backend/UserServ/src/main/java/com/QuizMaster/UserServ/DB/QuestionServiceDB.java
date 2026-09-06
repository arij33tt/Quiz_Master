package com.QuizMaster.UserServ.DB;

import com.QuizMaster.UserServ.Questions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceDB {
    @Autowired
    QuestionRepository questionRepository;

    public List<Question> generateQuestionPool(Long quizID){
        return questionRepository.questionPool(quizID);
    }

}
