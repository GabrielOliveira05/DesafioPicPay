package com.oliveiradev.bancopicpay.services;

import com.oliveiradev.bancopicpay.domain.transaction.Transaction;
import com.oliveiradev.bancopicpay.domain.user.User;
import com.oliveiradev.bancopicpay.dtos.TransactionDTO;
import com.oliveiradev.bancopicpay.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    public void crateTransaction(TransactionDTO trasactionDTO) throws Exception {

        User sender = this.userService.findUserById(trasactionDTO.senderId());
        User receiver = this.userService.findUserById(trasactionDTO.receiverId());

        userService.validateTransaction(sender, trasactionDTO.value());

        boolean isAuthotized = this.authorizeTransaction(sender, trasactionDTO.value());

        if (!isAuthotized) {
            throw new Exception("Transação não autorizada");
        }

        Transaction newTransaction = new Transaction();

        newTransaction.setAmount(trasactionDTO.value());
        newTransaction.setReceiver(receiver);
        newTransaction.setSender(sender);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(trasactionDTO.value()));
        receiver.setBalance(receiver.getBalance().add(trasactionDTO.value()));

        this.transactionRepository.save(newTransaction);
        this.userService.saveUser(receiver);
        this.userService.saveUser(sender);

    }

    public boolean authorizeTransaction(User sender, BigDecimal value) {
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

        if (authorizationResponse.getStatusCode() == HttpStatus.OK) {

            String status = (String) authorizationResponse.getBody().get("status");

            return "success".equalsIgnoreCase(status);
        } else
            return false;
    }


}
