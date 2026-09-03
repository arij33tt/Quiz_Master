package com.QuizMaster.UserServ.DTO;

public class AttemptDTO {

    long quizID;


    public AttemptDTO(long quizID) {
        this.quizID = quizID;
    }

    public long getQuizID() {
        return quizID;
    }

    public void setQuizID(long quizID) {
        this.quizID = quizID;
    }
}
