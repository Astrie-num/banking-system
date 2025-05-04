package com.rwandabank.banking_system.service;

import com.rwandabank.banking_system.entity.Banking;
import com.rwandabank.banking_system.entity.Customer;
import com.rwandabank.banking_system.repository.BankingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rwandabank.banking_system.repository.CustomerRepository;


import java.time.LocalDateTime;

@Service
public class BankingService {

    @Autowired
    private CustomerRepository customerRepository;


    @Autowired
    private BankingRepository bankingRepository;

    @Autowired
    private EmailService emailService;

    public Customer registerCustomer(Customer customer){
        customer.setBalance(0.0);
        customer.setLastUpdateDateTime(LocalDateTime.now());
        return customerRepository.save(customer);
    }

    @Transactional
    public Banking saveMoney(Long customerId, Double amount){
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

//        validateCustomerData(customer, customerId);

        customer.setBalance(customer.getBalance() + amount);
        customer.setLastUpdateDateTime(LocalDateTime.now());
        customerRepository.save(customer);


        Banking  banking  =  new Banking();
        banking.setCustomer(customer);
        banking.setAccount(customer.getAccount());
        banking.setAmount(amount);
        banking.setType("saving");
        banking.setBankingDateTime(LocalDateTime.now());
        bankingRepository.save(banking);

        String messageText = String.format(
                "Dear %s %s,\n\nYour SAVING of $%.2f to account %s was successful.\nTransaction Date: %s",
                customer.getFirstname() != null? customer.getFirstname() : "Customer",
                customer.getLastname() != null?  customer.getLastname() : "",
                amount,
                customer.getAccount() != null? customer.getAccount():"N/A",
                LocalDateTime.now());
        emailService.sendEmail(customer.getEmail(), "Saving Transaction Confirmation", messageText);

        return banking;

    }

    @Transactional
    public Banking withdrawMoney(Long customerId, Double amount){
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if(customer.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

//        validateCustomerData(customer, customerId);

        customer.setBalance((customer.getBalance() - amount));
        customer.setLastUpdateDateTime(LocalDateTime.now());
        customerRepository.save(customer);


        Banking banking = new Banking();
        banking.setCustomer(customer);
        banking.setAccount(customer.getAccount());
        banking.setAmount(amount);
        banking.setType("withdrawal");
        banking.setBankingDateTime(LocalDateTime.now());
        bankingRepository.save(banking);

        String messageText = String.format(
                "Dear %s %s,\n\nYour WITHDRWAL of $%.2f from account %s was successful.\nTransaction Date: %s",
                customer.getFirstname() != null? customer.getFirstname() : "Customer",
                customer.getLastname() != null?  customer.getLastname() : "",
                amount,
                customer.getAccount() != null? customer.getAccount():"N/A",
                LocalDateTime.now());
        emailService.sendEmail(customer.getEmail(), "Saving Transaction Confirmation", messageText);

        return banking;
    }


    @Transactional
    public Banking transferMoney(Long fromCustomerId, Long toCustomerId, Double amount){
        Customer fromCustomer = customerRepository.findById(fromCustomerId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        Customer toCustomer = customerRepository.findById(toCustomerId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if(fromCustomer.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        fromCustomer.setBalance(fromCustomer.getBalance() - amount);
        toCustomer.setBalance(toCustomer.getBalance() + amount);
        fromCustomer.setLastUpdateDateTime(LocalDateTime.now());
        toCustomer.setLastUpdateDateTime(LocalDateTime.now());
        customerRepository.save(fromCustomer);
        customerRepository.save(toCustomer);


        Banking banking =  new Banking();
        banking.setCustomer(fromCustomer);
        banking.setAccount(fromCustomer.getAccount());
        banking.setAmount(amount);
        banking.setType("transfer");
        banking.setBankingDateTime(LocalDateTime.now());
        bankingRepository.save(banking);

        String senderMessage = String.format(
                "Dear %s %s,\n\nYour TRANSFER of $%.2f to account %s was successful.\nTransaction Date: %s",
                fromCustomer.getFirstname() != null ? fromCustomer.getFirstname() : "Customer",
                fromCustomer.getLastname() != null ? fromCustomer.getLastname() : "" ,
                amount,
                toCustomer.getAccount() != null ? toCustomer.getAccount(): "N/A",
                LocalDateTime.now());
        emailService.sendEmail(fromCustomer.getEmail(), "Transfer Transaction Confirmation", senderMessage);

        String recipientMessage = String.format(
                "Dear %s %s,\n\nYou have received a TRANSFER of $%.2f from account %s.\nTransaction Date: %s",
                toCustomer.getFirstname() != null? toCustomer.getFirstname() : "Customer" ,
                toCustomer.getLastname() !=null ? toCustomer.getLastname(): "",
                amount,
                fromCustomer.getAccount() != null? fromCustomer.getAccount() : "N/A",
                LocalDateTime.now());
        emailService.sendEmail(toCustomer.getEmail(), "Transfer Receipt Confirmation", recipientMessage);

        return banking;
    }
}
