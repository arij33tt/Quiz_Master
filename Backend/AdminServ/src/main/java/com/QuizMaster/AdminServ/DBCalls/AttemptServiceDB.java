package com.QuizMaster.AdminServ.DBCalls;

import com.QuizMaster.AdminServ.Attempts.Attempt;
import com.QuizMaster.AdminServ.DTO.AttemptDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttemptServiceDB {
    @Autowired
    AttemptRepository attemptRepository;

    public List<AttemptDTO> loadAttempts(Long quizID){
        List<AttemptDTO> attemptDTOList=new ArrayList<>();
        List<Object[]> dbo=attemptRepository.attemptsOnThis(quizID);
        for(Object[] row: dbo){
            Attempt attempt = (Attempt) row[0];
            Integer score = ((Number) row[1]).intValue();
            AttemptDTO b=new AttemptDTO(attempt.getAttemptID(),score,true);
            attemptDTOList.add(b);
        }
        return attemptDTOList;
    }


}
