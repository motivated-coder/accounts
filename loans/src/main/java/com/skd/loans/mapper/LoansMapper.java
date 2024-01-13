package com.skd.loans.mapper;

import com.skd.loans.dto.LoansDto;
import com.skd.loans.entity.Loans;

public class LoansMapper {

    private LoansMapper() {
    }

    public static LoansDto mapToLoansDto(Loans loans) {
        return LoansDto.builder()
                .loanNumber(loans.getLoanNumber())
                .loanType(loans.getLoanType())
                .totalLoan(loans.getTotalLoan())
                .outstandingAmount(loans.getOutstandingAmount())
                .amountPaid(loans.getAmountPaid())
                .mobileNumber(loans.getMobileNumber())
                .build();
    }

    public static Loans mapToLoans(LoansDto loansDto, Loans loans) {
        loans.setLoanNumber(loansDto.getLoanNumber());
        loans.setLoanType(loansDto.getLoanType());
        loans.setMobileNumber(loansDto.getMobileNumber());
        loans.setTotalLoan(loansDto.getTotalLoan());
        loans.setAmountPaid(loansDto.getAmountPaid());
        loans.setOutstandingAmount(loansDto.getOutstandingAmount());
        return loans;
    }
}
