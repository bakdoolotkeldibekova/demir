package practice.demirbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.demirbank.dto.TransactionDTO;
import practice.demirbank.entity.Transaction;
import practice.demirbank.service.TransactionService;

import java.security.Principal;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/income")
    public ResponseEntity<Transaction> income(@RequestBody TransactionDTO transactionDTO, Principal principal){
        return transactionService.income(transactionDTO, principal.getName());
    }

    @PostMapping("/expense")
    public ResponseEntity<Transaction> expense(@RequestBody TransactionDTO transactionDTO, Principal principal){
        return transactionService.expense(transactionDTO, principal.getName());
    }
}
