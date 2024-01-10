package com.skd.accounts.service.impl;

import com.skd.accounts.constants.AccountsConstant;
import com.skd.accounts.dto.AccountsDto;
import com.skd.accounts.dto.CustomerDto;
import com.skd.accounts.entity.Accounts;
import com.skd.accounts.entity.Customer;
import com.skd.accounts.exception.CustomerAlreadyExistsException;
import com.skd.accounts.exception.ResourceNotFoundException;
import com.skd.accounts.mapper.AccountsMapper;
import com.skd.accounts.mapper.CustomerMapper;
import com.skd.accounts.repository.AccountRepository;
import com.skd.accounts.repository.CustomerRepository;
import com.skd.accounts.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Override
    public CustomerDto create(CustomerDto customerDto) {
        Optional<Customer> customer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (customer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exists with mobile number " + customerDto.getMobileNumber());
        }
        Customer newCustomer = CustomerMapper.toCustomer(customerDto);

        Customer savedCustomer = customerRepository.saveAndFlush(newCustomer);
        Accounts newAccount = generateAccountForCustomer(savedCustomer.getCustomerId());

        Accounts savedAccount = accountRepository.save(newAccount);
        CustomerDto response = CustomerMapper.toCustomerDto(savedCustomer);
        response.setAccountsDto(AccountsMapper.toAccountsDto(savedAccount));
        return response;
    }

    @Override
    public CustomerDto fetch(String mobileNumber) {
        Optional<Customer> fetchedCustomer = customerRepository.findByMobileNumber(mobileNumber);
        if (fetchedCustomer.isEmpty()) {
            throw new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber);
        }
        Optional<Accounts> fetchedAccount = accountRepository.findByCustomerId(fetchedCustomer.get().getCustomerId());
        CustomerDto response = CustomerMapper.toCustomerDto(fetchedCustomer.get());
        fetchedAccount.ifPresent(accounts -> response.setAccountsDto(AccountsMapper.toAccountsDto(accounts)));
        return response;
    }

    @Override
    public boolean update(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (Objects.nonNull(accountsDto)) {
            Accounts fetchedAccount = accountRepository.findById(customerDto.getAccountsDto().getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Accounts", "accountNumber", customerDto.getAccountsDto().getAccountNumber().toString()));
            Customer fetchedCustomer = customerRepository.findById(fetchedAccount.getCustomerId()).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "customerId", fetchedAccount.getCustomerId().toString()));
            CustomerMapper.map(customerDto, fetchedCustomer);
            AccountsMapper.map(accountsDto, fetchedAccount);

            customerRepository.save(fetchedCustomer);
            accountRepository.save(fetchedAccount);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean delete(String mobileNumber) {
        Customer fetchedCustomer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","mobileNumber",mobileNumber));
        accountRepository.deleteByCustomerId(fetchedCustomer.getCustomerId());
        customerRepository.deleteById(fetchedCustomer.getCustomerId());
        return true;
    }

    private Accounts generateAccountForCustomer(Long customerId) {
        return Accounts.builder()
                .accountType(AccountsConstant.SAVINGS)
                .accountNumber(1000000000L + new Random().nextInt(900000000))
                .customerId(customerId)
                .branchAddress(AccountsConstant.BANK_ADDRESS_1)
                .build();
    }
}
