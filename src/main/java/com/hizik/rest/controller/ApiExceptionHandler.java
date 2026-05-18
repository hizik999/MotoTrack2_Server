package com.hizik.rest.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class, EmptyResultDataAccessException.class})
    public ProblemDetail handleNotFound(Exception exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Moto point not found");
        problemDetail.setDetail(exception.getMessage());
        return problemDetail;
    }
}
