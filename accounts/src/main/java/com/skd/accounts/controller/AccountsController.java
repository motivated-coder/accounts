package com.skd.accounts.controller;

import com.skd.accounts.dto.AccountsContactDto;
import com.skd.accounts.dto.CustomerDto;
import com.skd.accounts.dto.ErrorResponseDto;
import com.skd.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
@Tag(name = "Rest APIs for accounts service"
        ,description = "CRUD operations for accounts service")
public class AccountsController {

    @Value("${build.info}")
    private String buildInfo;

    private final AccountsContactDto accountsContactDto;
    private final IAccountService accountService;

    @Operation(
            summary = "api to create account",
            description = "This api provides functionality to create an account for a provided new customer"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
    )
    @PostMapping("/create")
    public ResponseEntity<CustomerDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        CustomerDto responseBody = accountService.create(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseBody);
    }

    @Operation(
            summary = "api to fetch account details",
            description = "This api provides functionality to fetch account details of existing customer by mobileNumber"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status SUCCESSFUL"
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccount(@RequestParam
                                                    @Pattern(regexp = "(^$|[0-9]{10})", message = "mobileNumber must be 10 digits")
                                                    String mobileNumber) {
        CustomerDto responseBody = accountService.fetch(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }

    @Operation(
            summary = "api to update account details",
            description = "This api provides functionality to update account details of existing customer"
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
    @PutMapping("/update")
    public ResponseEntity<Boolean> updateAccount(@Valid @RequestBody CustomerDto customerDto) {
        boolean responseBody = accountService.update(customerDto);
        if(responseBody) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(responseBody);
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(false);
        }
    }

    @Operation(
            summary = "api to delete account details",
            description = "This api provides functionality to delete account of existing customer by mobileNumber"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status SUCCESSFUL"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status INTERNAL SERVER ERROR"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status BAD REQUEST",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteAccount(@RequestParam
                                                 @Pattern(regexp = "(^$|[0-9]{10})", message = "mobileNumber must be 10 digits")
                                                 String mobileNumber) {
        boolean responseBody = accountService.delete(mobileNumber);
        if(responseBody) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(responseBody);
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(false);
        }
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
    public ResponseEntity<AccountsContactDto> getSupportContact(){
        return ResponseEntity.ok(accountsContactDto);
    }
}
