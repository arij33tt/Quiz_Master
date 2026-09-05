package com.QuizMaster.UserServ.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;


public class AttemptDTO {

  long attemptID;
  int score;
  boolean completedQuiz;

    Instant startedAt;
    int timeLimit;

    public AttemptDTO(long attemptID, int score, boolean completedQuiz, Instant startedAt, int timeLimit) {
        this.attemptID = attemptID;
        this.score = score;
        this.completedQuiz = completedQuiz;
        this.startedAt = startedAt;
        this.timeLimit = timeLimit;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

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
