package com.skd.accounts.mapper;

import com.skd.accounts.dto.CustomerDto;
import com.skd.accounts.entity.Customer;

import java.util.Optional;

public class CustomerMapper {
    private CustomerMapper(){}

    public static Customer toCustomer(CustomerDto customerDto){
        return Customer.builder()
                .name(customerDto.getName())
                .mobileNumber(customerDto.getMobileNumber())
                .email(customerDto.getEmail())
                .build();

    }

    public static CustomerDto toCustomerDto(Customer customer){
        return CustomerDto.builder()
                .name(customer.getName())
                .mobileNumber(customer.getMobileNumber())
                .email(customer.getEmail())
                .build();

    }

    public static void map(CustomerDto customerDto, Customer fetchedCustomer) {
        fetchedCustomer.setName(customerDto.getName());
        fetchedCustomer.setEmail(customerDto.getEmail());
        fetchedCustomer.setMobileNumber(customerDto.getMobileNumber());
    }
}
