package com.skd.cards.controller;

import com.skd.cards.dto.CardsContactDto;
import com.skd.cards.dto.CardsDto;
import com.skd.cards.dto.ErrorResponseDto;
import com.skd.cards.service.ICardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(value = "/api/v1/cards", produces =  {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
@Tag(
        name = "Rest APIs for Cards service",
        description = "CRUD Rest APIs for Cards service in SKD BANK"
)
public class CardsController {

    private final ICardsService cardService;
    private final CardsContactDto cardsContactDto;

    @Value("${build.info}")
    private String buildInfo;

    @Operation(
            summary = "Rest API to create new card",
            description = "Rest API to create new card for customer of given mobileNumber")
    @ApiResponses({
            @ApiResponse(description = "HTTP Status BADREQUEST",
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(description = "HTTP Status CREATED",
                    responseCode = "201"
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<CardsDto> issueNewCard(@RequestParam
                                                 @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits")
                                                 String mobileNumber) {
        CardsDto responseBody = cardService.issueNewCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseBody);
    }

    @Operation(
            summary = "Rest API to get details of a card",
            description = "Rest API get details of a card of customer with given mobileNumber")
    @ApiResponses({
            @ApiResponse(description = "HTTP Status BADREQUEST",
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(description = "HTTP Status SUCCESS",
                    responseCode = "200"
            )
    }
    )
    @GetMapping("/details")
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestParam
                                                     @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits")
                                                     String mobileNumber) {
        log.info("fetching card details");
        CardsDto responseBody = cardService.fetchCardDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @Operation(
            summary = "Rest API to update details of a card",
            description = "Rest API to update details of a card")
    @ApiResponses({
            @ApiResponse(description = "HTTP Status BADREQUEST",
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(description = "HTTP Status SUCCESS",
                    responseCode = "200"
            )
    }
    )
    @PutMapping("/update")
    public ResponseEntity<Boolean> updateCardDetails(@Valid @RequestBody CardsDto cardsDto) {
        Boolean b = cardService.updateCardDetails(cardsDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(b);
    }

    @Operation(
            summary = "Rest API to delete card details",
            description = "Rest API to delete details of a card of a customer")
    @ApiResponses({
            @ApiResponse(description = "HTTP Status BADREQUEST",
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(description = "HTTP Status SUCCESS",
                    responseCode = "200"
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteCardDetails(@RequestParam
                                                     @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits")
                                                     String mobileNumber) {
        Boolean b = cardService.deleteCardDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(b);
    }

    @Operation(
            summary = "api to get build info details",
            description = "This api provides build info details of application"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status SUCCESSFUL"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status INTERNAL SERVER ERROR"
            )
    }
    )
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo(){
        return ResponseEntity.ok(buildInfo);
    }

    @Operation(
            summary = "api to get contact info details ",
            description = "This api provides contact details of developer and support team"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status SUCCESSFUL"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status INTERNAL SERVER ERROR"
            )
    })
    @GetMapping("/contact-info")
    public ResponseEntity<CardsContactDto> getSupportContact(){
        return ResponseEntity.ok(cardsContactDto);
    }

}














