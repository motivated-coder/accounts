package com.skd.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {

    @GetMapping("/fallback/accounts")
    public Mono<String> accountFallBack(){
        return Mono.just("Some problem occured in accounts service, please try later");
    }
    @GetMapping("/fallback/loans")
    public Mono<String> loansFallBack(){
        return Mono.just("Some problem occured in loans service, please try later");
    }
}
