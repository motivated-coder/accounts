package com.skd.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Schema(
        name = "Customer",
        description = "Schema to hold customer and account information"
)
public class CustomerDto {

    @Schema(
            description = "Name of the customer",
            example = "Sumeet Dwivedi"
    )
    @NotEmpty(message = "name can't be null or empty")
    @Size(min = 5, message = "min characters should be 5 in name")
    private String name;

    @Schema(
            description = "Email of the customer",
            example = "sumeet@skdbank.com"
    )
    @NotEmpty(message = "email can't be null or empty")
    @Email(message = "invalid email format")
    private String email;

    @Schema(
            description = "Mobile Number of the customer",
            example = "123456789"
    )
    @NotEmpty(message = "mobileNumber can't be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    private AccountsDto accountsDto;
}
