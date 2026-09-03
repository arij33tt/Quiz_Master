package com.QuizMaster.UserServ.DB;

import com.QuizMaster.UserServ.Quizs.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepositories extends JpaRepository<Quiz, Long> {
}
