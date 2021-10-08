package practice.demirbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.demirbank.entity.User;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
    List<User> findAllByCurrentBalanceGreaterThanEqual(BigDecimal balance);
    List<User> findAllByLogin(String login);

}
