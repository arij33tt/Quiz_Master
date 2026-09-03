package com.QuizMaster.UserServ.Quizs;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Quiz")
public class Quiz {

    @Id
    long quizID;

    String topicId;
    int numberOfQuestion;
    int timeLimit; //in minutes
    int correct=1;
    int wrong=0;
    int notAttended=0;
    int attempts;

    public Quiz(int attempts,String topicId, int numberOfQuestion, int timeLimit, int correct, int wrong, int notAttended) {
        this.topicId = topicId;
        this.numberOfQuestion = numberOfQuestion;
        this.timeLimit = timeLimit;
        this.correct = correct;
        this.wrong = wrong;
        this.notAttended = notAttended;
        this.attempts=attempts;
    }

    public Quiz() {
    }
    //
//    public Quiz(QuizDTO quizDTO) {
//        this.topicId=quizDTO.getTopicId();
//
//        this.numberOfQuestion =quizDTO.getNumberOfQuestion();
//        this.timeLimit =quizDTO.getTimeLimit();
//        this.correct = quizDTO.getCorrect();
//        this.wrong = quizDTO.getWrong();
//        this.notAttended = quizDTO.getNotAttended();
//    }

    public long getQuizID() {
        return quizID;
    }

    public void setQuizID(long quizID) {
        this.quizID = quizID;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public int getNumberOfQuestion() {
        return numberOfQuestion;
    }

    public void setNumberOfQuestion(int numberOfQuestion) {
        this.numberOfQuestion = numberOfQuestion;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getWrong() {
        return wrong;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }

    public int getNotAttended() {
        return notAttended;
    }

    public void setNotAttended(int notAttended) {
        this.notAttended = notAttended;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }
}
