package com.QuizMaster.UserServ.DB;

import com.QuizMaster.UserServ.Attempts.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt,Long> {


    @Query(value="SELECT q.attempts - COUNT(a.attemptid) AS attempts_left " +
            "FROM quiz q " +
            "LEFT JOIN attempt a" +
            "    ON q.quizid = a.quizid" +
            "   AND a.userid = :u " +
            "WHERE q.quizid = :q " +
            "GROUP BY q.attempts;",nativeQuery = true)
    Integer noOfAttemptsLeft(
            @Param("u")String userID,
            @Param("q")Long quizID
    );



    @Query(value = """
    SELECT *
    FROM attempt
    WHERE quizid = :q
      AND userid = :u
      AND has_submitted = false
    ORDER BY started_at DESC
    LIMIT 1
    """,
            nativeQuery = true)
    Optional<Attempt> lastOpenAttempt(
            @Param("q") Long quizID,
            @Param("u") String userID
    );

    @Query(value = """
    SELECT
        a.attemptid,
        COALESCE(
            SUM(
                CASE
                    WHEN h.solved = true AND h.answer = qn.correct
                        THEN q.correct
                    WHEN h.solved = true
                        THEN q.wrong
                    ELSE
                        q.not_attended
                END
            ),
            0
        ) AS score,
        a.has_submitted,
        a.started_at,
        q.time_limit
    FROM attempt a
    JOIN quiz q
        ON a.quizid = q.quizid
    LEFT JOIN history h
        ON a.attemptid = h.attemptid
    LEFT JOIN question qn
        ON h.questionid = qn.questionid
    WHERE a.userid = :userId
    GROUP BY
        a.attemptid,
        a.has_submitted,
        a.started_at,
        q.time_limit
    ORDER BY a.started_at DESC
    """,
            nativeQuery = true)
    List<Object[]> loadAttempts(@Param("userId") String userId);

    @Modifying
    @Query(value = """
    UPDATE attempt
    SET last_activity = CURRENT_TIMESTAMP
    WHERE attemptid = :id
      AND has_submitted = false
    """,
            nativeQuery = true)
    void update(@Param("id") Long attemptID);

    @Modifying
    @Query(value= """
    UPDATE attempt a
    SET has_submitted = true
    FROM quiz q
    WHERE a.quizid = q.quizid
      AND a.has_submitted = false
      AND CURRENT_TIMESTAMP >
          a.started_at + (q.time_limit * INTERVAL '1 minute')
    """,
            nativeQuery = true)
    void closeExp();

}
