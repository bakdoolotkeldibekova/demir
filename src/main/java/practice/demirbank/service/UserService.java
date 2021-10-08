package practice.demirbank.service;

import org.springframework.http.ResponseEntity;
import practice.demirbank.dto.UserDTO;
import practice.demirbank.entity.Transaction;
import practice.demirbank.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    ResponseEntity<User> create(UserDTO userDTO);
    ResponseEntity<User> update(User user);
    ResponseEntity<User> getByLogin(String login);
    ResponseEntity<List<Transaction>> getAllTransactionsByLogin(String login);
    ResponseEntity<List<User>> getAll();
    ResponseEntity<List<User>> getAllByBalanceBiggerThan(BigDecimal balance);
    ResponseEntity<List<User>> getAllByLogin(String login);
}
