package com.QuizMaster.UserServ.AttemptHistory;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="History")
public class History {

    long attemptID;
    long questionID;
    boolean solved;
    int answer;// range 1,4 inclusive
    int seq;//during saving this i will store the sequence

    public History(long attemptID, long questionID, boolean solved, int answer, int seq) {
        this.attemptID = attemptID;
        this.questionID = questionID;
        this.solved = solved;
        this.answer = answer;
        this.seq = seq;
    }

    public History() {
    }

    public long getAttemptID() {
        return attemptID;
    }

    public void setAttemptID(long attemptID) {
        this.attemptID = attemptID;
    }

    public long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(long questionID) {
        this.questionID = questionID;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
