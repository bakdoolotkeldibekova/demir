package practice.demirbank.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import practice.demirbank.dto.UserDTO;
import practice.demirbank.entity.Role;
import practice.demirbank.entity.Transaction;
import practice.demirbank.entity.User;
import practice.demirbank.exception.ResourceNotFoundException;
import practice.demirbank.repository.UserRepository;
import practice.demirbank.service.RoleService;
import practice.demirbank.service.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<User> create(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setCurrentBalance(new BigDecimal(8));
        Role role = roleService.getById(1L);
        user.setRole(role);
        List<Transaction> transactionList = new ArrayList<>();
        user.setTransactionList(transactionList);

        return ResponseEntity.ok().body(userRepository.save(user));
    }

    @Override
    public ResponseEntity<User> update(User user) {
        return ResponseEntity.ok().body(userRepository.save(user));
    }

    @Override
    public ResponseEntity<User> getByLogin(String login) {
        User user = userRepository.findByLogin(login);
        if(user == null)
            throw new ResourceNotFoundException("User login " + login + " not found!");
        return ResponseEntity.ok().body(user);
    }

    @Override
    public ResponseEntity<List<Transaction>> getAllTransactionsByLogin(String login) {
        return ResponseEntity.ok().body(userService.getByLogin(login).getBody().getTransactionList());
    }

    @Override
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok().body(userRepository.findAll());
    }

    @Override
    public ResponseEntity<List<User>> getAllByBalanceBiggerThan(BigDecimal balance) {
        return ResponseEntity.ok().body(userRepository.findAllByCurrentBalanceGreaterThanEqual(balance));
    }

    @Override
    public ResponseEntity<List<User>> getAllByLogin(String login) {
        return ResponseEntity.ok().body(userRepository.findAllByLogin(login));
    }
}
