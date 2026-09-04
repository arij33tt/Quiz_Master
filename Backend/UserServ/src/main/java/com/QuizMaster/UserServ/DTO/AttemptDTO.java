package com.QuizMaster.UserServ.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class AttemptDTO {

  long attemptID;
  int score;
  boolean completedQuiz;

    public AttemptDTO(long attemptID, boolean completedQuiz) {
        this.attemptID = attemptID;
        this.completedQuiz = completedQuiz;
    }

    public boolean isCompletedQuiz() {
        return completedQuiz;
    }

    public void setCompletedQuiz(boolean completedQuiz) {
        this.completedQuiz = completedQuiz;
    }

    public long getAttemptID() {
        return attemptID;
    }

    public void setAttemptID(long attemptID) {
        this.attemptID = attemptID;
    }
}
