package com.QuizMaster.UserServ.Services;

import com.QuizMaster.UserServ.DB.QuestionRepository;
import com.QuizMaster.UserServ.DTO.QuestionDTO;
import com.QuizMaster.UserServ.Questions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SendingNextQuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public Optional<QuestionDTO> sendNext(Long attemptID){
        Optional<QuestionDTO> nextQuestion=questionRepository.nextQuestion(attemptID);
        return nextQuestion;
    }
}
