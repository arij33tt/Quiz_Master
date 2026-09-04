package com.QuizMaster.AdminServ.DBCalls;

import com.QuizMaster.AdminServ.Attempts.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt,Long> {

    @Query(value = """
    SELECT
        a.*,
        COUNT(*) FILTER (WHERE h.answer = q.correct) AS score
    FROM attempt a
    JOIN history h
        ON a.attemptid = h.attemptid
    JOIN question q
        ON h.questionid = q.questionid
    WHERE a.quizid = :q
    GROUP BY a.attemptid
    ORDER BY score DESC
    LIMIT 20
    """, nativeQuery = true)
    List<Object[]> attemptsOnThis(
            @Param("q") Long quizID
    );

}
