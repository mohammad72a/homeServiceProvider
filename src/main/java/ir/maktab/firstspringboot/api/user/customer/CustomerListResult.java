package ir.maktab.firstspringboot.api.user.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerListResult {
    private List<CustomerModel> customers;

    public void addCustomerModel(CustomerModel customerModel) {
        if (customers == null) {
            customers = new ArrayList<>();
        }
        customers.add(customerModel);
    }
}
