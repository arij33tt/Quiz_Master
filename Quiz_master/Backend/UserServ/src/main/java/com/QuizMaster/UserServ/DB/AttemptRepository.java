package com.QuizMaster.UserServ.DB;

import com.QuizMaster.UserServ.Attempts.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
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
