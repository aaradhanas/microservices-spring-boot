package com.microservices.app.exception;

import com.microservices.app.model.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request){
        String errorMessage = ex.getLocalizedMessage();
        if(errorMessage == null) {
            errorMessage = ex.toString();
        }
        ErrorResponse errorResponse = new ErrorResponse(new Date(), errorMessage);
        return new ResponseEntity<>(
                errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    // When business exceptions are categorized under one parent exception class, the parent class can be specified in value
    // of annotation and also in method argument
    @ExceptionHandler(value = { UserServiceException.class, NullPointerException.class })
    public ResponseEntity<Object> handleSpecificExceptions(Exception ex, WebRequest request){
        String errorMessage = ex.getLocalizedMessage();
        if(errorMessage == null) {
            errorMessage = ex.toString();
        }
        ErrorResponse errorResponse = new ErrorResponse(new Date(), errorMessage);
        // The error response can be customized based on business logic
        return new ResponseEntity<>(
                errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
