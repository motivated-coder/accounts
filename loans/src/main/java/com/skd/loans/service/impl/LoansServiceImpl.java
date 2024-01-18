package com.skd.loans.service.impl;

import com.skd.loans.constants.LoansConstants;
import com.skd.loans.dto.LoansDto;
import com.skd.loans.entity.Loans;
import com.skd.loans.exception.LoanAlreadyExistsException;
import com.skd.loans.exception.ResourceNotFoundException;
import com.skd.loans.mapper.LoansMapper;
import com.skd.loans.repository.LoansRepository;
import com.skd.loans.service.ILoansService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LoansServiceImpl implements ILoansService {

    private final LoansRepository loansRepository;

    /**
     * @param mobileNumber - Mobile Number of the Customer
     */
    @Override
    public LoansDto createLoan(String mobileNumber) {
        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
        if (optionalLoans.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber " + mobileNumber);
        }
        return LoansMapper.mapToLoansDto(loansRepository.save(createNewLoan(mobileNumber)));
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new loan details
     */
    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    /**
     * @param mobileNumber - Input mobile Number
     * @return Loan Details based on a given mobileNumber
     */
    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        return LoansMapper.mapToLoansDto(loans);
    }

    /**
     * @param loansDto - LoansDto Object
     * @return boolean indicating if the update of loan details is successful or not
     */
    @Override
    public boolean updateLoan(LoansDto loansDto) {
        boolean isUpdated = false;
        if (Objects.nonNull(loansDto.getLoanNumber())) {
            Loans fetchedLoans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
            LoansMapper.mapToLoans(loansDto, fetchedLoans);
            loansRepository.save(fetchedLoans);
            isUpdated = true;
        }
        return isUpdated;
    }

    /**
     * @param mobileNumber - Input MobileNumber
     * @return boolean indicating if the delete of loan details is successful or not
     */
    @Override
    public boolean deleteLoan(String mobileNumber) {
        boolean isDeleted = false;
        if (Objects.nonNull(mobileNumber)) {
            Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                    () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
            );
            loansRepository.deleteById(loans.getLoanId());
            isDeleted = true;
        }
        return isDeleted;
    }
}
