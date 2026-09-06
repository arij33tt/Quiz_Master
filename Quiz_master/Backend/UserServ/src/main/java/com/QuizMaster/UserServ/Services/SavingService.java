package com.QuizMaster.UserServ.Services;

import com.QuizMaster.UserServ.AttemptHistory.History;
import com.QuizMaster.UserServ.DB.AttemptHistoryRepository;
import com.QuizMaster.UserServ.DB.QuestionRepository;
import com.QuizMaster.UserServ.DTO.QuestionDTO;
import com.QuizMaster.UserServ.Questions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SavingService {
    @Autowired
    AttemptHistoryRepository historyRepository;


    public void saveThis(QuestionDTO prevQuestion,Long attemptID){
        //(long attemptID, long questionID, boolean solved, int answer, int seq)
        if (prevQuestion.getQuestionID() == null) {
            return;
        }
        if(prevQuestion==null){
            return;
        }
        History prevHist= new History(attemptID, prevQuestion.getQuestionID(), true,
                prevQuestion.getSelectedAns(), prevQuestion.getSeq());
        //it will not create a new entry as i have introduced composite keys in the History Entity
        historyRepository.save(prevHist);

       }
}
