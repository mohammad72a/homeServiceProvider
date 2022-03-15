package ir.maktab.firstspringboot.api.review;

import ir.maktab.firstspringboot.model.entity.HomeServiceOffer;
import ir.maktab.firstspringboot.model.entity.Review;
import ir.maktab.firstspringboot.model.entity.user.Customer;
import ir.maktab.firstspringboot.model.entity.user.Proficient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewCreateParam {
    private String comment;
    private Integer rating;
    private long customerId;
    private long homeServiceOrderId;

    public Review convert2Review(Customer customer, Proficient proficient, HomeServiceOffer homeServiceOffer) {
        return Review.builder()
                .customer(customer)
                .proficient(proficient)
                .homeServiceOffer(homeServiceOffer)
                .rating(this.rating)
                .comment(this.comment)
                .build();
    }
}
