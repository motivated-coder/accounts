package com.skd.cards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String resource, String field, String fieldValue){
        super(String.format("%s not found for %s : %s",resource,field,fieldValue));
    }
}
