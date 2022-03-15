package ir.maktab.firstspringboot.model.entity;

import ir.maktab.firstspringboot.model.entity.order.HomeServiceOrder;
import ir.maktab.firstspringboot.model.entity.user.Customer;
import ir.maktab.firstspringboot.model.entity.user.Proficient;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Instant createDate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Customer customer;

    private String customerCreditCardNumber;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Proficient proficient;

    @OneToOne(cascade = CascadeType.PERSIST)
    private HomeServiceOrder homeServiceOrder;

    @OneToOne
    private HomeServiceOffer homeServiceOffer;

    private Double amount;
}

