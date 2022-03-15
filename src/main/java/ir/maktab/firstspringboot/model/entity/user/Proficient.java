package ir.maktab.firstspringboot.model.entity.user;


import ir.maktab.firstspringboot.exception.ResourceNotFoundException;
import ir.maktab.firstspringboot.model.entity.HomeServiceOffer;
import ir.maktab.firstspringboot.model.entity.Review;
import ir.maktab.firstspringboot.model.entity.Transaction;
import ir.maktab.firstspringboot.model.entity.category.SubCategory;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Proficient extends User {

    private UserStatus proficientStatus = UserStatus.NEW;

    @CreationTimestamp
    private Instant registerDate;

    @Builder.Default
    private Double credit = 0.0;

    @Builder.Default
    private Double ratingAvg = 0.0;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String profileImage;

    @OneToMany(mappedBy = "proficient", cascade = CascadeType.ALL)
    private Set<HomeServiceOffer> homeServiceOffers;

    @OneToMany(mappedBy = "proficient", cascade = CascadeType.ALL)
    private Set<Review> reviews;

    @OneToMany(mappedBy = "proficient", cascade = CascadeType.ALL)
    private Set<Transaction> transactions;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<SubCategory> subCategories;

    public void addHomeServiceOffer(HomeServiceOffer homeServiceOffer) {
        if (homeServiceOffers == null) {
            homeServiceOffers = new HashSet<>();
        }
        homeServiceOffers.add(homeServiceOffer);
        homeServiceOffer.setProficient(this);
    }

    public void addReview(Review review) {
        if (reviews == null) {
            reviews = new HashSet<>();
        }
        reviews.add(review);
        double ratingSum = reviews.stream().mapToDouble(Review::getRating).sum();

        DecimalFormat df = new DecimalFormat("0.00");
        ratingAvg = Double.parseDouble(df.format(ratingSum / reviews.size()));
        review.setProficient(this);
    }

    public void addTransaction(Transaction transaction) {
        if (transactions == null) {
            transactions = new HashSet<>();
        }
        transactions.add(transaction);
        transaction.setProficient(this);
    }

    public void addSubCategory(SubCategory subCategory) {
        if (subCategories == null) {
            subCategories = new HashSet<>();
        }
        subCategories.add(subCategory);
    }

    public void removeSubCategory(SubCategory subCategory) {
        if (subCategories == null) {
            throw new ResourceNotFoundException("subCategory", "id", subCategory.getId());
        }
        subCategories.remove(subCategory);
    }
}
