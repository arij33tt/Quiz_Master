package com.QuizMaster.UserServ.Questions;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="question")
public class Question {

    @Id
    long questionID;
    //    @ForeignKey()
    String topicId;
    String question;
    String option1;
    String option2;
    String option3;
    String option4;
    Boolean isMCQ=true;
    Integer correct;

    // this will help in db calls
    public Question(String question, String option1, String option2, String option3, String option4, Boolean isMCQ, Integer correct) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.isMCQ = isMCQ;
        this.correct = correct;
    }

    // to pick from db

    public Question(long questionID, String topicId, String question, String option1, String option2, String option3, String option4, Boolean isMCQ, Integer correct) {
        this.questionID = questionID;
        this.topicId = topicId;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.isMCQ = isMCQ;
        this.correct = correct;
    }

    //for empty init , in test or creating lists
    public Question() {
    }

    public Question(Long questionID, Object o) {
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public Boolean getMCQ() {
        return isMCQ;
    }

    public void setMCQ(Boolean MCQ) {
        isMCQ = MCQ;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }
}
