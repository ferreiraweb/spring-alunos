package com.serpro.spring.sqlite.exceptions;

public class AlunoNotFoundException extends RuntimeException{

    private static final Long serialVersionUID = 1L;

    public AlunoNotFoundException(String message){
        super(message);
    }

}
