package com.skd.accounts.feign.fallback;

import com.skd.accounts.dto.CardsDto;
import com.skd.accounts.dto.LoansDto;
import com.skd.accounts.feign.CardsFeignClient;
import com.skd.accounts.feign.LoansFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallbackClient implements LoansFeignClient {
    @Override
    public ResponseEntity<LoansDto> fetchLoanDetails(String mobileNumber) {
        return null;
    }
}
