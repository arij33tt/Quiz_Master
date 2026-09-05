package com.QuizMaster.UserServ.DB;
//no of attempts is not added in this class yet
public interface QuizProjection {
    long getQuizID();
    String getTopicId();
    int getNumberOfQuestion();
    int getTimeLimit();
    int getCorrect();
    int getWrong();
    int getNotAttended();
}
