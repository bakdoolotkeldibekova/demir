package practice.demirbank.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import practice.demirbank.entity.Role;
import practice.demirbank.entity.Transaction;
import practice.demirbank.entity.User;
import practice.demirbank.repository.UserRepository;
import practice.demirbank.service.RoleService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

@Component
public class Boot implements CommandLineRunner {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        roleService.create(Role.builder().roleName("ROLE_USER").build());
        roleService.create(Role.builder().roleName("ROLE_ADMIN").build());

        //register admin
        userRepository.save(new User("admin",  passwordEncoder.encode("admin"), new BigDecimal(8), new ArrayList<Transaction>(), roleService.getById(2L)));
    }
}
