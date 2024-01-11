package com.skd.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Schema(
        name = "Accounts",
        description = "Schema to hold account information"
)
public class AccountsDto {

    @Schema(
            description = "Account Number of customer",
            example = "0123456789"
    )
    @NotEmpty(message = "accountNumber can't be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})")
    private Long accountNumber;

    @Schema(
            description = "Account Type",
            example = "SAVINGS"
    )
    @NotEmpty(message = "accountType can't be null or empty")
    private String accountType;

    @Schema(
            description = "Branch Address of Bank where customer has account",
            example = "HDFC Bank, PHASE 5 Gurgaon"
    )
    @NotEmpty(message = "branchAddress can't be null or empty")
    private String branchAddress;
}
