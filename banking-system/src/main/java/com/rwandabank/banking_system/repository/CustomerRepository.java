package com.rwandabank.banking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rwandabank.banking_system.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
