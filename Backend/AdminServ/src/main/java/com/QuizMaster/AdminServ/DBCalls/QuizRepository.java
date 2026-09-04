package com.QuizMaster.AdminServ.DBCalls;

import com.QuizMaster.AdminServ.Quizs.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {


    @Query(value="SELECT\n" +
            "    q.*,\n" +
            "    COUNT(a.attemptid) AS attempt_count\n" +
            "FROM quiz q\n" +
            "LEFT JOIN attempt a\n" +
            "    ON q.quizid = a.quizid\n" +
            "GROUP BY q.quizid\n" +
            "ORDER BY attempt_count ASC\n" +
            "LIMIT 10;",
            nativeQuery=true)
    List<Quiz> loadTen();


}
