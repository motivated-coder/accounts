package com.skd.cards.exception;

import com.skd.cards.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CardAlreadyExitsException.class)
    public ErrorResponseDto handleCardAlreadyExitsException(CardAlreadyExitsException e, WebRequest webRequest) {
        return ErrorResponseDto.builder()
                .apiPath(webRequest.getDescription(false))
                .errorMessage(e.getMessage())
                .errorCode(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponseDto handleResourceNotFoundException(ResourceNotFoundException e, WebRequest webRequest) {
        return ErrorResponseDto.builder()
                .apiPath(webRequest.getDescription(false))
                .errorMessage(e.getMessage())
                .errorCode(HttpStatus.BAD_REQUEST)
                .build();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> errorList = ex.getBindingResult().getAllErrors();
        errorList.forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);
    }
}



















