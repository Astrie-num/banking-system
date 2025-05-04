package com.rwandabank.banking_system.controller;

import com.rwandabank.banking_system.entity.Banking;
import com.rwandabank.banking_system.entity.Customer;
import com.rwandabank.banking_system.service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rwandabank")
public class BankingController {

    @Autowired
    private BankingService bankingService;

    @PostMapping("/registerCustomer")
    public Customer registerCustomer(@RequestBody Customer customer){
        return bankingService.registerCustomer(customer);
    }


    @PostMapping("/save/{customerId}")
    public Banking saveMoney(@PathVariable Long customerId, @RequestParam Double amount){
        return bankingService.saveMoney(customerId, amount);
    }

    @PostMapping("/withdraw/{customerId}")
    public Banking withdrawMoney(@PathVariable Long customerId, @RequestParam Double amount){
        return bankingService.withdrawMoney(customerId, amount);
    }

    @PostMapping("/transfer")
    public Banking transferMoney(@RequestParam Long fromCustomerId,
                                 @RequestParam Long toCustomerId,
                                 @RequestParam Double amount){
        return bankingService.transferMoney(fromCustomerId, toCustomerId, amount);
    }
}
