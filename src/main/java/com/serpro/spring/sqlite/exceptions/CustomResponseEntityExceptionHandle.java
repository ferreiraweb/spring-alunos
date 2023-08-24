package com.serpro.spring.sqlite.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class CustomResponseEntityExceptionHandle extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponseMessage> handleGlobalExceptions(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(getCustomResponseEntityException(ex, HttpStatus.INTERNAL_SERVER_ERROR.toString()));
    }


    @ExceptionHandler(AlunoNotFoundException.class)
    public ResponseEntity<CustomResponseMessage> handleAlunoNotExistsException(AlunoNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(getCustomResponseEntityException(ex, HttpStatus.NOT_FOUND.toString()));
    }






    /* ----------------------------------------------- */

    private CustomResponseMessage getCustomResponseEntityException(Exception ex, String status) {

        return new CustomResponseMessage(
                status,
                new Date(),
                ex.getMessage(),
                null

        );
    }

}
