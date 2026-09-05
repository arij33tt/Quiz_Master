package com.QuizMaster.AdminServ.ErrorHandler;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handles connection failures right when a transaction tries to start
    @ExceptionHandler({CannotCreateTransactionException.class, DataAccessResourceFailureException.class})
    public ResponseEntity<Object> handleDatabaseDownException(Exception ex) {

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", System.currentTimeMillis());
        responseBody.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        responseBody.put("error", "Service Unavailable");
        responseBody.put("message", "The database is currently down or unreachable. Please try again later.");

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE) // HTTP 503
                .body(responseBody);
    }

}
