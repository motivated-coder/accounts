package com.skd.accounts.service;

import com.skd.accounts.dto.CustomerDetailsDto;

public interface ICustomerDetailsService {
    public CustomerDetailsDto getCustomerDetails(String mobileNumber);
}
