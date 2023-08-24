package com.serpro.spring.sqlite.exceptions;

public class AlunoEmailMustBeNonRepeatedException extends RuntimeException {

    private static final Long serialVersionUID = 1L;

    public AlunoEmailMustBeNonRepeatedException(String message) {
        super(message);
    }
}
