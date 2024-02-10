package com.skd.accounts.feign;

import com.skd.accounts.dto.CardsDto;
import com.skd.accounts.feign.fallback.CardsFallbackClient;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cards", fallback = CardsFallbackClient.class)
public interface CardsFeignClient {
    @GetMapping("/api/v1/cards/details")
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestParam String mobileNumber);
}
