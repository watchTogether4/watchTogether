package com.watchtogether.server.users.domain.repository;

import com.watchtogether.server.users.domain.entitiy.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
