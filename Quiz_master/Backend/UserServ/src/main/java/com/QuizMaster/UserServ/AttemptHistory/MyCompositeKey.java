package com.QuizMaster.UserServ.AttemptHistory;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MyCompositeKey implements Serializable {
    long attemptID;
    long questionID;


}
