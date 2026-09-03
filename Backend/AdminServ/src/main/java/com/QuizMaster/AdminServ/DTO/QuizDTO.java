package com.QuizMaster.AdminServ.DTO;

public class QuizDTO {

    String topicId;
    int numberOfQuestion;
    int timeLimit; //in minutes
    int correct=1;
    int wrong=0;
    int notAttended=0;

    public QuizDTO(String topicId, int numberOfQuestion, int timeLimit, int correct, int wrong, int notAttended) {
        this.topicId = topicId;
        this.numberOfQuestion = numberOfQuestion;
        this.timeLimit = timeLimit;
        this.correct = correct;
        this.wrong = wrong;
        this.notAttended = notAttended;
    }

    public QuizDTO() {
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
}
