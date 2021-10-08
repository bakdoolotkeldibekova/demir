package practice.demirbank.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import practice.demirbank.entity.Role;
import practice.demirbank.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        practice.demirbank.entity.User user = userRepository.findByLogin(login);
        List<Role> roles = new ArrayList<>();
        roles.add(user.getRole());
        return new User(user.getLogin(), user.getPassword(), roles);
    }

}