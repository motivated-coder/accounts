package com.skd.accounts.mapper;

import com.skd.accounts.dto.CustomerDetailsDto;
import com.skd.accounts.dto.CustomerDto;
import com.skd.accounts.entity.Customer;

public class CustomerDetailsMapper {
    private CustomerDetailsMapper(){}

    public static CustomerDetailsDto toCustomerDetails(CustomerDto customerDto){
        return CustomerDetailsDto.builder()
                .name(customerDto.getName())
                .mobileNumber(customerDto.getMobileNumber())
                .email(customerDto.getEmail())
                .accountsDto(customerDto.getAccountsDto())
                .build();

    }
}
