package ir.maktab.firstspringboot.model.entity.order;

import ir.maktab.firstspringboot.model.entity.Address;
import ir.maktab.firstspringboot.model.entity.HomeServiceOffer;
import ir.maktab.firstspringboot.model.entity.category.SubCategory;
import ir.maktab.firstspringboot.model.entity.user.Customer;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HomeServiceOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Customer customer;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private SubCategory subCategory;

    private Double suggestedPrice;
    private String comment;

    @CreationTimestamp
    private Instant orderCreateDate;

    private Instant orderFinishedDate;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Address address;

    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.WAITING_FOR_PROFICIENT_SUGGESTION;

    @OneToMany(mappedBy = "homeServiceOrder", fetch = FetchType.EAGER)
    private Set<HomeServiceOffer> homeServiceOffers;

    @OneToOne(cascade = CascadeType.ALL)
    private HomeServiceOffer acceptedOffer;

    public void addOffer(HomeServiceOffer homeServiceOffer) {
        if (homeServiceOffers == null) {
            homeServiceOffers = new HashSet<>();
        }
        homeServiceOffers.add(homeServiceOffer);
        homeServiceOffer.setHomeServiceOrder(this);
    }

    public void acceptOffer(HomeServiceOffer homeServiceOffer) {
        if (homeServiceOffers.contains(homeServiceOffer)) {
            acceptedOffer = homeServiceOffer;
            homeServiceOffer.setIsAccepted(true);
        }
    }
}
