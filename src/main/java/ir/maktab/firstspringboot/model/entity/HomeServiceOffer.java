package ir.maktab.firstspringboot.model.entity;

import ir.maktab.firstspringboot.model.entity.order.HomeServiceOrder;
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
public class HomeServiceOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Proficient proficient;

    @CreationTimestamp
    private Instant offerCreateDate;

    private Double suggestedPrice;

    private String workDuration;

    private Instant startTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private HomeServiceOrder homeServiceOrder;

    @Builder.Default
    private Boolean isAccepted = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HomeServiceOffer that = (HomeServiceOffer) o;
        return id.equals(that.id);
    }
}

