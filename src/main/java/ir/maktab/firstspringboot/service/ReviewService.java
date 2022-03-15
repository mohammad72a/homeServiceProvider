package ir.maktab.firstspringboot.service;

import ir.maktab.firstspringboot.api.review.ReviewCreateParam;
import ir.maktab.firstspringboot.api.review.ReviewCreateResult;
import ir.maktab.firstspringboot.exception.ResourceNotFoundException;
import ir.maktab.firstspringboot.exception.ReviewException;
import ir.maktab.firstspringboot.model.entity.HomeServiceOffer;
import ir.maktab.firstspringboot.model.entity.Review;
import ir.maktab.firstspringboot.model.entity.order.HomeServiceOrder;
import ir.maktab.firstspringboot.model.entity.order.OrderStatus;
import ir.maktab.firstspringboot.model.entity.user.Customer;
import ir.maktab.firstspringboot.model.entity.user.Proficient;
import ir.maktab.firstspringboot.model.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CustomerServiceImpl customerServiceImpl;

    public ReviewService(ReviewRepository reviewRepository, CustomerServiceImpl customerServiceImpl) {
        this.reviewRepository = reviewRepository;
        this.customerServiceImpl = customerServiceImpl;
    }

    @Transactional
    public ReviewCreateResult save(ReviewCreateParam createParam) {
        Customer customer = customerServiceImpl.loadById(createParam.getCustomerId());

        HomeServiceOrder homeServiceOrder = customer.getHomeServiceOrders().stream()
                .filter(o -> o.getId() == createParam.getHomeServiceOrderId())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("HomeServiceOrder", "id", createParam.getHomeServiceOrderId()));

        if (OrderStatus.FINISHED != homeServiceOrder.getOrderStatus()){
            throw new ReviewException("Reviews are only sent for orders with finished status");
        }

        HomeServiceOffer acceptedOffer = homeServiceOrder.getAcceptedOffer();
        Proficient proficient = acceptedOffer.getProficient();

        Review review = createParam.convert2Review(customer, proficient, acceptedOffer);
        proficient.addReview(review);

        Review saveResult = reviewRepository.save(review);

        return new ReviewCreateResult(saveResult.getId());
    }
}

