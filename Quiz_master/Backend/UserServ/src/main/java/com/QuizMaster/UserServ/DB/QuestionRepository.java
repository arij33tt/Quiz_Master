package com.QuizMaster.UserServ.DB;

import com.QuizMaster.UserServ.DTO.QuestionDTO;
import com.QuizMaster.UserServ.Questions.Question;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {
    @Query(value="SELECT qu.*" +
            "FROM quiz q " +
            "JOIN question qu" +
            "    ON qu.topicid = q.topicid " +
            "WHERE q.quizid = :q " +
            "ORDER BY RANDOM() " +
            "LIMIT q.numberofquestion;",
    nativeQuery = true)
    List<Question> questionPool(
            @Param("q") Long quizID
    );
    @Query(value = """
    SELECT
        q.questionid,
        q.question,
        q.option1,
        q.option2,
        q.option3,
        q.option4,
        q.ismcq,
        a.seed,
        h.seq,
        h.attemptid
    FROM history h
    JOIN question q
        ON h.questionid = q.questionid
    JOIN attempt a
        ON h.attemptid = a.attemptid
    WHERE h.attemptid = :a
      AND h.solved = false
    ORDER BY h.seq ASC
    LIMIT 1
    """,
            nativeQuery = true)
    List<Object[]> nextQuestion(@Param("a") Long attemptID);

}
