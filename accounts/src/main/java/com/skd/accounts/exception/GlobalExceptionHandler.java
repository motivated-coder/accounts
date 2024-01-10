package com.skd.accounts.exception;

import com.skd.accounts.dto.ErrorResponseDto;
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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorsList = ex.getBindingResult().getAllErrors();
        validationErrorsList.forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            validationErrors.put(field,message);
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponseDto handleGlobalException(Exception exception, WebRequest webRequest){
        return ErrorResponseDto.builder()
                .apiPath(webRequest.getDescription(false))
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorMessage(exception.getMessage())
                .errorTime(LocalDateTime.now())
                .build();
    }
    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ErrorResponseDto handleCustomerAlreadyExistsException(CustomerAlreadyExistsException exception, WebRequest webRequest){
        return ErrorResponseDto.builder()
                .apiPath(webRequest.getDescription(false))
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(exception.getMessage())
                .errorTime(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponseDto ResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
        return ErrorResponseDto.builder()
                .apiPath(webRequest.getDescription(false))
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(exception.getMessage())
                .errorTime(LocalDateTime.now())
                .build();
    }
}
