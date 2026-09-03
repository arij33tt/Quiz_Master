package com.QuizMaster.AdminServ.DBCalls;

import com.QuizMaster.AdminServ.Questions.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

}
