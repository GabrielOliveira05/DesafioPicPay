package com.oliveiradev.bancopicpay.repositories;

import com.oliveiradev.bancopicpay.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {



}
