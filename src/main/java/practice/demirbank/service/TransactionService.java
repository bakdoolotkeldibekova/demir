package practice.demirbank.service;

import org.springframework.http.ResponseEntity;
import practice.demirbank.dto.TransactionDTO;
import practice.demirbank.entity.Transaction;

public interface TransactionService {
    ResponseEntity<Transaction> income(TransactionDTO transactionDTO, String login);
    ResponseEntity<Transaction> expense(TransactionDTO transactionDTO, String login);
}
