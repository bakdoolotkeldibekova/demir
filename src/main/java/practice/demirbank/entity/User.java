package practice.demirbank.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "users")
public class User extends BaseEntity{
    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "currentBalance", nullable = false)
    private BigDecimal currentBalance;

    @OneToMany
    @JoinColumn(name = "transaction_id")
    private List<Transaction> transactionList;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

}
