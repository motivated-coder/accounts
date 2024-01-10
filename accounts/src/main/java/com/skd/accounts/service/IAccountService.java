package com.skd.accounts.service;

import com.skd.accounts.dto.CustomerDto;

public interface IAccountService {
    public CustomerDto create(CustomerDto customerDto);
    public CustomerDto fetch(String mobileNumber);
    public boolean update(CustomerDto customerDto);
    public boolean delete(String mobileNumber);
}
