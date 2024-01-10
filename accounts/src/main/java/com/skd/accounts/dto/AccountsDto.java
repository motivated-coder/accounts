package com.skd.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AccountsDto {

    @NotEmpty(message = "accountNumber can't be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})")
    private Long accountNumber;

    @NotEmpty(message = "accountType can't be null or empty")
    private String accountType;

    @NotEmpty(message = "branchAddress can't be null or empty")
    private String branchAddress;
}
