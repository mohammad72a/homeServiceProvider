package ir.maktab.firstspringboot.api.order;

import ir.maktab.firstspringboot.model.entity.order.HomeServiceOrder;
import ir.maktab.firstspringboot.model.entity.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeServiceOrderModel {
    private long id;
    private long customerId;
    private long subCategoryId;
    private Double suggestedPrice;
    private String comment;
    private Instant orderCreateDate;
    private Instant orderFinishedDate;
    private OrderStatus orderStatus;

    public HomeServiceOrderModel convertOrder2Model(HomeServiceOrder homeServiceOrder) {
        return HomeServiceOrderModel.builder()
                .id(homeServiceOrder.getId())
                .customerId(homeServiceOrder.getCustomer().getId())
                .subCategoryId(homeServiceOrder.getSubCategory().getId())
                .suggestedPrice(homeServiceOrder.getSuggestedPrice())
                .comment(homeServiceOrder.getComment())
                .orderCreateDate(homeServiceOrder.getOrderCreateDate())
                .orderFinishedDate(homeServiceOrder.getOrderFinishedDate())
                .orderStatus(homeServiceOrder.getOrderStatus())
                .build();
    }
}

