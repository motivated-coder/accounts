package com.skd.accounts.feign.fallback;

import com.skd.accounts.dto.CardsDto;
import com.skd.accounts.feign.CardsFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallbackClient implements CardsFeignClient {
    @Override
    public ResponseEntity<CardsDto> fetchCardDetails(String mobileNumber) {
        return null;
    }
}
