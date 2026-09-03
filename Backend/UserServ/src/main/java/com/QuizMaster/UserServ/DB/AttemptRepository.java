package com.QuizMaster.UserServ.DB;

import com.QuizMaster.UserServ.Attempts.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt,Long> {


    @Query(value="SELECT q.attempts - COUNT(a.attemptid) AS attempts_left" +
            "FROM quiz q" +
            "LEFT JOIN attempt a" +
            "    ON q.quizid = a.quizid" +
            "   AND a.useid = :u" +
            "WHERE q.quizid = :q" +
            "GROUP BY q.attempts;",nativeQuery = true)
    Integer noOfAttemptsLeft(
            @Param("u")Long userID,
            @Param("q")Long quizID
    );




    @Query(value="SELECT *" +
            "FROM attempt" +
            "WHERE quizid = :q" +
            "  AND userid = :u" +
            "  AND hassubmitted = false" +
            "ORDER BY startedat DESC;",nativeQuery = true)
    Optional<Attempt> lastOpenAttempt(
            @Param("q") Long quizID,
            @Param("u") String userID
    );
}
