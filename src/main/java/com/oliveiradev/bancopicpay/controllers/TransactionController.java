package com.oliveiradev.bancopicpay.controllers;

import com.oliveiradev.bancopicpay.domain.transaction.Transaction;
import com.oliveiradev.bancopicpay.dtos.TransactionDTO;
import com.oliveiradev.bancopicpay.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transactionDTO) throws Exception {
        Transaction newTransaction = this.transactionService.crateTransaction(transactionDTO);
        return new ResponseEntity<>(newTransaction, HttpStatus.OK);
    }
}
