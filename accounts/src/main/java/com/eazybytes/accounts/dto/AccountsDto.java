package com.eazybytes.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {

    @NotEmpty(message = "Account Number is required")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number should be 10 digits")
    private Long accountNumber;

    @NotEmpty(message = "Account Type is required")
    private String accountType;

    @NotEmpty(message = "Branch Address is required")
    private String branchAddress;

}
