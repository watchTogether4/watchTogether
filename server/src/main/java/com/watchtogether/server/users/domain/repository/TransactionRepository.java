package com.watchtogether.server.users.domain.repository;

import com.watchtogether.server.users.domain.entitiy.Transaction;
import com.watchtogether.server.users.domain.entitiy.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserOrderByIdDesc(User user);

    List<Transaction> findByUserOrderByTransactionDtDesc(User user);
}
