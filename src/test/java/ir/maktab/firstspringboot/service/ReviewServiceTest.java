package ir.maktab.firstspringboot.service;

import ir.maktab.firstspringboot.api.review.ReviewCreateParam;
import ir.maktab.firstspringboot.api.review.ReviewCreateResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;


    @Test
    void test_save() {
        ReviewCreateParam createParam = ReviewCreateParam.builder()
                .comment("review 2")
                .rating(2)
                .customerId(2)
                .homeServiceOrderId(6)
                .build();

        ReviewCreateResult result = reviewService.save(createParam);
        assertNotNull(result);
    }
}
