package com.thiago.picpaybackendchallenge.service;

import com.thiago.picpaybackendchallenge.domain.transaction.Transaction;
import com.thiago.picpaybackendchallenge.domain.user.User;
import com.thiago.picpaybackendchallenge.dto.TransactionDTO;
import com.thiago.picpaybackendchallenge.repository.TransactionRepository;
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
    private NotificationService notificationService;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public Transaction createTransaction(TransactionDTO transaction) throws Exception{
        User sender = this.userService.findUserByID(transaction.senderId());
        User receiver = this.userService.findUserByID(transaction.receiverId());

        this.userService.validateTransaction(sender, transaction.value());

        boolean isAuthorized = this.authorizeTransaction(sender, transaction.value());
        if(!isAuthorized){
            throw new Exception("Transaction not authorized");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setAmount(transaction.value());
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        this.repository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotification(sender, "Transaction completed successfully");
        this.notificationService.sendNotification(receiver, "Transaction received successfully");

        return newTransaction;
    }

    public boolean authorizeTransaction(User sender, BigDecimal value){
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("http://localhost:8080/mock/authorize", Map.class);
        if (authorizationResponse.getStatusCode() == HttpStatus.OK){
            Map responseData = (Map) authorizationResponse.getBody().get("data");
            return Boolean.TRUE.equals(responseData.get("authorization"));
        }
        return false;
    }
}
