package com.skd.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponseDto {
    private  String apiPath;
    private HttpStatusCode errorCode;
    private  String errorMessage;
    private LocalDateTime errorTime;
}
