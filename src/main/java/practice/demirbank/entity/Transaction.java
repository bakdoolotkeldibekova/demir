package practice.demirbank.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity{
    @Column(name = "action")
    private String action;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "description")
    private String description;
}
