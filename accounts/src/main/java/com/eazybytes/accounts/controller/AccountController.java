package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountConstants;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ResposeDto;
import com.eazybytes.accounts.service.IAccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class AccountController {


    private IAccountsService accountsService;


    @GetMapping("/sample")
    public String sample() {
        return "Sample";
    }


    @PostMapping("/create")
    public ResponseEntity<ResposeDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {

        accountsService.creteAccount(customerDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResposeDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));

    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
                                                               @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number should be 10 digits")
                                                               String phoneNumber) {

        CustomerDto customerDto = accountsService.fetchAccount(phoneNumber);

        return ResponseEntity.status(HttpStatus.OK).body(customerDto);

    }

    @PutMapping("/update")
    public ResponseEntity<ResposeDto> updateAccount(@Valid @RequestBody CustomerDto customerDto) {

        boolean isUpdated = accountsService.updateAccount(customerDto);

        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResposeDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResposeDto(AccountConstants.STATUS_500, AccountConstants.MESSAGE_500));
        }

    }


    @DeleteMapping("/delete")
    public ResponseEntity<ResposeDto> deleteAccount(@RequestParam
                                                     @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number should be 10 digits")
                                                        String phoneNumber) {

        boolean isDeleted = accountsService.deleteAccount(phoneNumber);

        System.out.println("isDeleted = " + isDeleted);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResposeDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResposeDto(AccountConstants.STATUS_500, AccountConstants.MESSAGE_500));
        }

    }
}