package com.patika.kredinbizdeservice.exceptions;

import com.patika.kredinbizdeservice.exceptions.dto.ExceptionResponse;
import com.patika.kredinbizdeservice.producer.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @Autowired
    private KafkaProducer kafkaProducer;

    @ExceptionHandler(KredinbizdeException.class)
    public ResponseEntity<ExceptionResponse> handleKredinbizdeException(KredinbizdeException exception) {
        log.error("exception occurred. {0}", exception.getCause());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(prepareExceptionResponse(exception, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllException(Exception exception) {
        kafkaProducer.sendMessage("exception", exception.getMessage());
        log.error("exception occurred. {0}", exception.getCause());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(prepareExceptionResponse(exception, HttpStatus.BAD_REQUEST));
    }

    private ExceptionResponse prepareExceptionResponse(Exception exception, HttpStatus httpStatus) {
        return ExceptionResponse.builder()
                .message(exception.getMessage())
                .httpStatus(httpStatus)
                .build();
    }

}
