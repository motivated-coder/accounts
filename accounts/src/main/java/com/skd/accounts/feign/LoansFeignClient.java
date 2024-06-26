package com.skd.accounts.feign;

import com.skd.accounts.dto.CardsDto;
import com.skd.accounts.dto.LoansDto;
import com.skd.accounts.feign.fallback.LoansFallbackClient;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loans", fallback = LoansFallbackClient.class)
public interface LoansFeignClient {
    @GetMapping("/api/v1/loans/fetch")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam String mobileNumber);
}
