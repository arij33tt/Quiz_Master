package com.QuizMaster.UserServ.DB;

import com.QuizMaster.UserServ.AttemptHistory.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AttemptHistoryRepository extends JpaRepository<History, Long> {




}
