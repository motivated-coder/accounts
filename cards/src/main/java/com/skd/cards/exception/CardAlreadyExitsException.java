package com.skd.cards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CardAlreadyExitsException extends RuntimeException{
    public CardAlreadyExitsException(String message){
        super(message);
    }
}
