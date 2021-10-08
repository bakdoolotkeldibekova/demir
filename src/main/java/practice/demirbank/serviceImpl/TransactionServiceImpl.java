package practice.demirbank.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import practice.demirbank.dto.TransactionDTO;
import practice.demirbank.entity.Transaction;
import practice.demirbank.entity.User;
import practice.demirbank.exception.NotEnoughBalanceException;
import practice.demirbank.repository.TransactionRepository;
import practice.demirbank.repository.UserRepository;
import practice.demirbank.service.TransactionService;
import practice.demirbank.service.UserService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<Transaction> income(TransactionDTO transactionDTO, String login) {
        if(transactionDTO.getBalance().compareTo(BigDecimal.ZERO) < 0)
                throw new NotEnoughBalanceException("you can only put the number bigger than 0!");

        Transaction transaction = new Transaction();
        transaction.setBalance(transactionDTO.getBalance());
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setAction("income");
        transactionRepository.save(transaction);

        User user = userService.getByLogin(login).getBody();
        BigDecimal balance = user.getCurrentBalance().add(transactionDTO.getBalance());
        user.setCurrentBalance(balance);
        List<Transaction> transactionList = user.getTransactionList();
        transactionList.add(transaction);
        user.setTransactionList(transactionList);
        userService.update(user);

        return ResponseEntity.ok().body(transaction);
    }

    @Override
    public ResponseEntity<Transaction> expense(TransactionDTO transactionDTO, String login) {
        User user = userService.getByLogin(login).getBody();

        if(transactionDTO.getBalance().compareTo(BigDecimal.ZERO) < 0)
            throw new NotEnoughBalanceException("you can only put the number bigger than 0!");

        if((transactionDTO.getBalance().add(BigDecimal.valueOf(1.1))).compareTo(user.getCurrentBalance()) >= 0)
            throw new NotEnoughBalanceException("not enough money!");

        Transaction transaction = new Transaction();
        transaction.setBalance(transactionDTO.getBalance().add(BigDecimal.valueOf(1.1)));
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setAction("expense");
        transactionRepository.save(transaction);

        BigDecimal balance = user.getCurrentBalance().subtract(transactionDTO.getBalance().add(BigDecimal.valueOf(1.1)));
        user.setCurrentBalance(balance);
        List<Transaction> transactionList = user.getTransactionList();
        transactionList.add(transaction);
        user.setTransactionList(transactionList);
        userService.update(user);

        return ResponseEntity.ok().body(transaction);
    }
}
