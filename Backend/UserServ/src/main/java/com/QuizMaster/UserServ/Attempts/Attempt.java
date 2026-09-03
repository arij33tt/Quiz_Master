package com.QuizMaster.UserServ.Attempts;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name="Attempt")
public class Attempt {

    @Id
    long attemptID;
    long quizID;
    String userID;
    private Instant lastActivity;//in minutes
    Boolean hasSubmitted=false;
    int idle;//in minutes
    int seed;//value of range(1,24)

    private Instant startedAt;

    @PrePersist
    protected void onCreate() {
        this.startedAt = Instant.now();
    }



    public Instant getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    //for getting from the db
    public Attempt(long attemptID, long quizID, String userID, Boolean hasSubmitted, int idle, int seed) {
        this.attemptID = attemptID;
        this.quizID = quizID;
        this.userID = userID;
//        this.lastActivity = lastActivity;
        this.hasSubmitted = hasSubmitted;
        this.idle = idle;
        this.seed = seed;

    }

// for saving in the db
    public Attempt(long quizID, String userID) {
        this.quizID = quizID;
        this.userID = userID;

        this.hasSubmitted = false;
        this.idle = 0;//mins
        this.seed = 1+(int)(Math.rint (23*Math.random()));

    }

    public long getAttemptID() {
        return attemptID;
    }

    public void setAttemptID(long attemptID) {
        this.attemptID = attemptID;
    }

    public long getQuizID() {
        return quizID;
    }

    public void setQuizID(long quizID) {
        this.quizID = quizID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Instant getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Instant lastActivity) {
        this.lastActivity = lastActivity;
    }

    public Boolean getHasSubmitted() {
        return hasSubmitted;
    }

    public void setHasSubmitted(Boolean hasSubmitted) {
        this.hasSubmitted = hasSubmitted;
    }

    public int getIdle() {
        return idle;
    }

    public void setIdle(int idle) {
        this.idle = idle;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }
}
