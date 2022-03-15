package ir.maktab.firstspringboot.api.order;

import ir.maktab.firstspringboot.api.address.AddressCreateParam;
import ir.maktab.firstspringboot.model.entity.category.SubCategory;
import ir.maktab.firstspringboot.model.entity.order.HomeServiceOrder;
import ir.maktab.firstspringboot.model.entity.user.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeServiceOrderCreateParam {
    private long customerId;
    private long subCategoryId;
    private Double suggestedPrice;
    private String comment;
    private AddressCreateParam address;

    public HomeServiceOrder convert2HomeServiceOrder(Customer customer, SubCategory subCategory) {
        return HomeServiceOrder.builder()
                .customer(customer)
                .subCategory(subCategory)
                .suggestedPrice(this.suggestedPrice)
                .comment(this.comment)
                .address(address.convert2Address())
                .build();
    }
}
