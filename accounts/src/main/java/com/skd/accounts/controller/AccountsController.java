package com.skd.accounts.controller;

import com.skd.accounts.dto.CustomerDto;
import com.skd.accounts.service.IAccountService;
import com.skd.accounts.service.impl.AccountServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Validated
public class AccountsController {

    private final IAccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<CustomerDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        CustomerDto responseBody = accountService.create(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseBody);
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccount(@RequestParam
                                                    @Pattern(regexp = "(^$|[0-9]{10})", message = "mobileNumber must be 10 digits")
                                                    String mobileNumber) {
        CustomerDto responseBody = accountService.fetch(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> updateAccount(@Valid @RequestBody CustomerDto customerDto) {
        boolean responseBody = accountService.update(customerDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteAccount(@RequestParam
                                                 @Pattern(regexp = "(^$|[0-9]{10})", message = "mobileNumber must be 10 digits")
                                                 String mobileNumber) {
        boolean responseBody = accountService.delete(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }
}
