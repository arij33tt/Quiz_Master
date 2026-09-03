package com.QuizMaster.UserServ.DTO;

public class AttemptDTO {

  long attemptID;
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
