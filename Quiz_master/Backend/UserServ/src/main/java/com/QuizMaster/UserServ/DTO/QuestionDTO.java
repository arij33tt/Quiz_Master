package com.QuizMaster.UserServ.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;


public class QuestionDTO {
    Long attemptID;
    Long questionID;
    String question;
    String option1;
    String option2;
    String option3;
    String option4;
    Boolean isMCQ=true;
    int seed=0;
    int selectedAns=-1;
    int seq=0;
//    ArrayList<String> correct;

    public QuestionDTO() {
    }



    public QuestionDTO(String question, String option1, String option2, String option3, String option4, Boolean isMCQ) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.isMCQ = isMCQ;

    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public Long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Long questionID) {
        this.questionID = questionID;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public int getSelectedAns() {
        return selectedAns;
    }

    public void setSelectedAns(int selectedAns) {
        this.selectedAns = selectedAns;
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

    public QuestionDTO(Long attemptID, Long questionID, String question, String option1, String option2, String option3, String option4, Boolean isMCQ, int seed, int selectedAns, int seq) {
        this.attemptID = attemptID;
        this.questionID = questionID;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.isMCQ = isMCQ;
        this.seed = seed;
        this.selectedAns = selectedAns;
        this.seq = seq;
    }

    public Long getAttemptID() {
        return attemptID;
    }

    public void setAttemptID(Long attemptID) {
        this.attemptID = attemptID;
    }
}

