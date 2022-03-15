package ir.maktab.firstspringboot.api.user.customer;

import ir.maktab.firstspringboot.model.entity.user.Customer;
import ir.maktab.firstspringboot.model.entity.user.UserStatus;
import ir.maktab.firstspringboot.web.security.ApplicationUserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Double credit;
    private UserStatus customerStatus;
    private ApplicationUserRole userRole;
    private Instant registerDate;

    public CustomerModel convertCustomer2Model(Customer customer) {
        return CustomerModel.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .password(customer.getPassword())
                .credit(customer.getCredit())
                .customerStatus(customer.getCustomerStatus())
                .userRole(customer.getApplicationUserRole())
                .registerDate(customer.getRegisterDate())
                .build();
    }
}
