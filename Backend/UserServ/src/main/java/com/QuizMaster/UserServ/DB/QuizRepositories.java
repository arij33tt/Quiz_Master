package com.QuizMaster.UserServ.DB;

import com.QuizMaster.UserServ.Quizs.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepositories extends JpaRepository<Quiz, Long> {
    @Query(value = """
    SELECT q.*
    FROM quiz q
    LEFT JOIN attempt a
        ON q.quizid = a.quizid
        AND a.userid = :userId
    GROUP BY q.quizid
    HAVING q.attempts > COUNT(a.attemptid)
    """,
            nativeQuery = true)
    List<Quiz> loadUpcoming(@Param("userId") String userId);
}
