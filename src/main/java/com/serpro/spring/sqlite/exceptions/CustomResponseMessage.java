package com.serpro.spring.sqlite.exceptions;

import java.util.Date;

public record CustomResponseMessage(
        String status,
        Date timestamp,
        String details,
        String[] messages
){
}


