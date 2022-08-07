package com.travel.traveladmin.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(TravelException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(TravelException e){
        ExceptionResponse response = new ExceptionResponse(e.getMessage(), LocalDateTime.now());
        //log.info("response: {}", response.toString());
        return new ResponseEntity<ExceptionResponse>(response,HttpStatus.BAD_REQUEST);
    }

}
