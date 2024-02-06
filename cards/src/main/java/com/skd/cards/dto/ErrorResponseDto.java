package com.skd.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response"
)
public class ErrorResponseDto {

    @Schema(
            description = "represents api path for which request failed",
            example = "/api/v1/cards/create"
    )
    private  String apiPath;

    @Schema(
            description = "represents Http Status of error happened",
            example = "400"
    )
    private HttpStatusCode errorCode;

    @Schema(
            description = "represents message of error",
            example = "Cards doesn't exist for given Mobile Number 123456789"
    )
    private  String errorMessage;

    @Schema(
            description = "represents time when error happened"
    )
    private LocalDateTime errorTime;
}
