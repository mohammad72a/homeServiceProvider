package ir.maktab.firstspringboot.api.user.customer;

import ir.maktab.firstspringboot.model.entity.user.Customer;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerCreateParam {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public Customer convert2Customer() {
        return Customer.builder()
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
