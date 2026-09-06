package com.QuizMaster.UserServ.Services;

import com.QuizMaster.UserServ.AttemptHistory.History;
import com.QuizMaster.UserServ.Attempts.Attempt;
import com.QuizMaster.UserServ.Auth.SecurityService;
import com.QuizMaster.UserServ.DB.AttemptHistoryRepository;
import com.QuizMaster.UserServ.DB.AttemptRepository;
import com.QuizMaster.UserServ.DB.QuestionServiceDB;
import com.QuizMaster.UserServ.Questions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadyingNewAttempt {

    // we would need quizID, userID, attemptID will auto generate
    //returns the attemptid
    @Autowired
    SecurityService securityService;
    @Autowired
    AttemptHistoryRepository historyRepository;
    @Autowired
    QuestionServiceDB questionServiceDB;


    public Long newAttemptInit(Long quizID) {

        Attempt attempt =
                new Attempt(quizID, securityService.getCurrentUserId());

        Attempt savedAttempt = attemptRepo.save(attempt);

        Long attemptID = savedAttempt.getAttemptID();

        List<Question> questions =
                questionServiceDB.generateQuestionPool(quizID);

        int seq = 1;

        for (Question question : questions) {

            History history =
                    new History(
                            attemptID,
                            question.getQuestionID(),
                            false,
                            -1,
                            seq
                    );

            historyRepository.save(history);

            seq++;
        }

        return attemptID;
    }



    @Autowired
    AttemptRepository attemptRepo;
    public Long savingFunc(Attempt attempt){
        Attempt savedAttempt=attemptRepo.save(attempt);
        return savedAttempt.getAttemptID();
        //looking for mutability of Optional
    }


}
