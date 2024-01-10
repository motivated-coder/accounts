package com.skd.accounts.dto;

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
public class CustomerDto {
    @NotEmpty(message = "name can't be null or empty")
    @Size(min = 5, message = "min characters should be 5 in name")
    private String name;

    @NotEmpty(message = "email can't be null or empty")
    @Email(message = "invalid email format")
    private String email;

    @NotEmpty(message = "mobileNumber can't be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    private AccountsDto accountsDto;
}
