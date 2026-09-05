package com.QuizMaster.UserServ.Services;

import com.QuizMaster.UserServ.DB.QuestionRepository;
import com.QuizMaster.UserServ.DTO.QuestionDTO;
import com.QuizMaster.UserServ.Questions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SendingNextQuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public Optional<QuestionDTO> sendNext(Long attemptID) {

        List<Object[]> result = questionRepository.nextQuestion(attemptID);

        if (result.isEmpty()) {
            return Optional.empty();
        }

        Object[] row = result.get(0);

        QuestionDTO dto = new QuestionDTO();

        dto.setQuestionID(((Number) row[0]).longValue());
        dto.setQuestion((String) row[1]);
        dto.setOption1((String) row[2]);
        dto.setOption2((String) row[3]);
        dto.setOption3((String) row[4]);
        dto.setOption4((String) row[5]);
        dto.setMCQ((Boolean) row[6]);
        dto.setSeed(((Number) row[7]).intValue());
        dto.setSeq(((Number) row[8]).intValue());
        dto.setAttemptID(((Number) row[9]).longValue());

        return Optional.of(dto);
    }
}
