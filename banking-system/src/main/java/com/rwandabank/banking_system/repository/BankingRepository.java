package com.rwandabank.banking_system.repository;


import com.rwandabank.banking_system.entity.Banking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankingRepository extends JpaRepository<Banking, Long> {
}
