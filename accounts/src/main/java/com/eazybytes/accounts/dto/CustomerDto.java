package com.eazybytes.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {


    @NotEmpty(message = "Customer Name is required")
    @Size(min = 2,max = 30, message = "Customer Name should have atleast 2 characters")
    private String customerName;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid value")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number should be 10 digits")
    private String phoneNumber;


    private AccountsDto accountsDto;

}
