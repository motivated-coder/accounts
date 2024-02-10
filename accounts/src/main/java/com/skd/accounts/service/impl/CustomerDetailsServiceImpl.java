package com.skd.accounts.service.impl;

import com.skd.accounts.dto.CardsDto;
import com.skd.accounts.dto.CustomerDetailsDto;
import com.skd.accounts.dto.CustomerDto;
import com.skd.accounts.dto.LoansDto;
import com.skd.accounts.entity.Accounts;
import com.skd.accounts.entity.Customer;
import com.skd.accounts.exception.ResourceNotFoundException;
import com.skd.accounts.feign.CardsFeignClient;
import com.skd.accounts.feign.LoansFeignClient;
import com.skd.accounts.mapper.AccountsMapper;
import com.skd.accounts.mapper.CustomerDetailsMapper;
import com.skd.accounts.mapper.CustomerMapper;
import com.skd.accounts.repository.AccountRepository;
import com.skd.accounts.repository.CustomerRepository;
import com.skd.accounts.service.ICustomerDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerDetailsServiceImpl implements ICustomerDetailsService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;
    @Override
    public CustomerDetailsDto getCustomerDetails(String mobileNumber) {
        log.info("fetching customer details for mobile number {}", mobileNumber);
        Optional<Customer> fetchedCustomer = customerRepository.findByMobileNumber(mobileNumber);
        if (fetchedCustomer.isEmpty()) {
            throw new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber);
        }
        Optional<Accounts> fetchedAccount = accountRepository.findByCustomerId(fetchedCustomer.get().getCustomerId());
        CustomerDto customerDto = CustomerMapper.toCustomerDto(fetchedCustomer.get());
        fetchedAccount.ifPresent(accounts -> customerDto.setAccountsDto(AccountsMapper.toAccountsDto(accounts)));

        CustomerDetailsDto customerDetailsDto =  CustomerDetailsMapper.toCustomerDetails(customerDto);
        log.info("calling cards service to provide card details for mobile number {}", mobileNumber);
        ResponseEntity<CardsDto> cardsDtoResponseEntity =  cardsFeignClient.fetchCardDetails(mobileNumber);
        log.info("calling loans service to provide loan details for mobile number {}", mobileNumber);
        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
        if (Objects.nonNull(cardsDtoResponseEntity) && Objects.nonNull(cardsDtoResponseEntity.getBody())) {
            customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        }
        if (Objects.nonNull(loansDtoResponseEntity) && Objects.nonNull(loansDtoResponseEntity.getBody())) {
            customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
        }
        return customerDetailsDto;
    }
}
