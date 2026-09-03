package com.QuizMaster.AdminServ.DBCalls;

import com.QuizMaster.AdminServ.Quizs.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {
}
