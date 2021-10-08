package practice.demirbank.controller;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.demirbank.dto.UserDTO;
import practice.demirbank.entity.Transaction;
import practice.demirbank.entity.User;
import practice.demirbank.service.UserService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<User> registration(@RequestBody UserDTO userDTO){
        return userService.create(userDTO);
    }

    @GetMapping("/transaction/{login}")
    public ResponseEntity<List<Transaction>> getAllTransactionsByLogin(Principal principal){
        return userService.getAllTransactionsByLogin(principal.getName());
    }

    @GetMapping("/get")
    public ResponseEntity<List<User>> getAllByParam(@RequestParam(required = false) String login,
                                                    @ApiParam(value="get all users that have balance bigger than N") @RequestParam(required = false) BigDecimal balance){
        Set<User> fooSet = new LinkedHashSet<>(userService.getAll().getBody());

        if (login != null)
            fooSet.retainAll(userService.getAllByLogin(login).getBody());
        if (balance != null)
            fooSet.retainAll(userService.getAllByBalanceBiggerThan(balance).getBody());

        List<User> userList = new ArrayList<>(fooSet);
        return ResponseEntity.ok().body(userList);
    }
}
