package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     * Method to create a new account
     * @param customerDto - CutsomerDto object
     */
     CustomerDto creteAccount(CustomerDto customerDto);

    /**
     *
     * @param phoneNumber - phone number of the customer
     * @return Account  details based on a given  phone number
     */

    CustomerDto fetchAccount(String phoneNumber);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String phoneNumber);
}
