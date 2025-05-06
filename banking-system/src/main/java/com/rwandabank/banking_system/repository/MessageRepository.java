package com.rwandabank.banking_system.repository;

import com.rwandabank.banking_system.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
