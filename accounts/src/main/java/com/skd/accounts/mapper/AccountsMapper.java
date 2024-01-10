package com.skd.accounts.mapper;

import com.skd.accounts.dto.AccountsDto;
import com.skd.accounts.entity.Accounts;

public class AccountsMapper {
    private AccountsMapper(){}

    public static AccountsDto toAccountsDto(Accounts accounts){
        return AccountsDto.builder()
                .accountType(accounts.getAccountType())
                .accountNumber(accounts.getAccountNumber())
                .branchAddress(accounts.getBranchAddress())
                .build();
    }

    public static void map(AccountsDto accountsDto, Accounts accounts) {
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
    }
}
