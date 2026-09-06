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
    AttemptRepository attemptRepo;

    public List<AttemptDTO> loadAttempts(Long quizID) {

        List<Object[]> rows = attemptRepo.attemptsOnThis(quizID);

        return rows.stream()
                .map(row -> new AttemptDTO(
                        ((Number) row[0]).longValue(),
                        ((Number) row[1]).intValue(),
                        (Boolean) row[2]
                ))
                .toList();
    }

}
