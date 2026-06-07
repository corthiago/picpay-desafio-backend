package com.thiago.picpaybackendchallenge.repository;

import com.thiago.picpaybackendchallenge.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
