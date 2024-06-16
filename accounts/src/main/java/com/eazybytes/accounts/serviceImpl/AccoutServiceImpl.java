package com.eazybytes.accounts.serviceImpl;

import com.eazybytes.accounts.Exceptions.CustomerAlreadyExistsException;
import com.eazybytes.accounts.Exceptions.ResourceNotFoundException;
import com.eazybytes.accounts.constants.AccountConstants;
import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor

public class AccoutServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;

    private CustomerRepository customerRepository;

    private ModelMapper modelMapper;

    /**
     * Method to create a new account
     * @param customer - Cutsomer object
     * @return the new Accounts details
     */

    private Accounts createNewAccount(Customer customer) {

        Accounts accounts = new Accounts();
        accounts.setCustomerId(customer.getCustomerid());
        long random = 1000000000L + new Random().nextInt(900000000);

        accounts.setAccountNumber(random);
        accounts.setAccountType(AccountConstants.SAVINGS);
        accounts.setBranchAddress(AccountConstants.ADDRESS);
        return accounts;
    }



    @Override
    @Transactional
    public CustomerDto creteAccount(CustomerDto customerDto) {

        //Convert the DTO to Entity
        Customer customer = modelMapper.map(customerDto, Customer.class);

        Optional<Customer> customerOptional = customerRepository.findByphoneNumber(customer.getPhoneNumber());

        if(customerOptional.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number"+customerDto.getPhoneNumber());
        }

        //Save the entity to the database
        Customer savedCustomer = customerRepository.save(customer);

        accountsRepository.save(createNewAccount(savedCustomer));

        //Convert the Entity to DTO
        CustomerDto savedCustomerDto = modelMapper.map(savedCustomer, CustomerDto.class);

        return savedCustomerDto;
    }


    @Override
    public CustomerDto fetchAccount(String phoneNumber) {

        Customer customer = customerRepository.findByphoneNumber(phoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "phoneNumber", phoneNumber));

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerid())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerid().toString()));


        CustomerDto savedCustomerDto = modelMapper.map(customer, CustomerDto.class);
        savedCustomerDto.setAccountsDto(modelMapper.map(accounts, AccountsDto.class));

        return savedCustomerDto;
    }

    /**
     * Method to update an existing account
     * @param customerDto - CutsomerDto object
     * @return true if the account is updated successfully or not
     */

    @Override
    @Transactional
    public boolean updateAccount(CustomerDto customerDto) {

        boolean isUpdated = false;

        AccountsDto accountsDto = customerDto.getAccountsDto();

        if(accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber", accountsDto.getAccountNumber().toString()));

            modelMapper.map(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);


            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerId.toString()));

            modelMapper.map(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }



        return isUpdated;
    }

    @Override
    @Transactional
    public boolean deleteAccount(String phoneNumber) {


       Customer customer = customerRepository.findByphoneNumber(phoneNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "phoneNumber", phoneNumber));


        accountsRepository.deleteByCustomerId(customer.getCustomerid());
        customerRepository.deleteById(customer.getCustomerid());

        System.out.println("Customer deleted successfully");

        return true;
    }
}
