package ir.maktab.firstspringboot.web.controller;

import ir.maktab.firstspringboot.api.user.UserChangePasswordParam;
import ir.maktab.firstspringboot.api.user.UserChangePasswordResult;
import ir.maktab.firstspringboot.api.user.customer.CustomerCreateParam;
import ir.maktab.firstspringboot.api.user.customer.CustomerCreateResult;
import ir.maktab.firstspringboot.api.user.customer.CustomerUpdateParam;
import ir.maktab.firstspringboot.api.user.customer.CustomerUpdateResult;
import ir.maktab.firstspringboot.service.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerServiceImpl customerServiceImpl;

    @PostMapping
    public ResponseEntity<CustomerCreateResult> save(@RequestBody CustomerCreateParam createParam) {
        CustomerCreateResult customerCreateResult = customerServiceImpl.saveCustomer(createParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerCreateResult);
    }

    @PutMapping("/{id}/password")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<UserChangePasswordResult> changePassword(@RequestBody UserChangePasswordParam changePasswordParam,
                                                                   @PathVariable Long id) {
        changePasswordParam.setUserId(id);
        UserChangePasswordResult userChangePasswordResult = customerServiceImpl.changePassword(changePasswordParam);
        return ResponseEntity.ok(userChangePasswordResult);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<CustomerUpdateResult> update(@PathVariable long id, @RequestBody CustomerUpdateParam updateParam) {

        updateParam.setId(id);

        CustomerUpdateResult customerUpdateResult = customerServiceImpl.update(updateParam);
        return ResponseEntity.ok(customerUpdateResult);
    }

    //    http://localhost:8080/customers/confirm?token
    @GetMapping(path = {"confirm"})
    public String confirmToken(@RequestParam("token") String token) {
        return customerServiceImpl.confirmToken(token);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("{customerId}/confirm")
    public ResponseEntity<CustomerUpdateResult> confirmCustomerByAdmin(@PathVariable long customerId) {
        CustomerUpdateResult customerUpdateResult = customerServiceImpl.confirmCustomerByAdmin(customerId);
        return ResponseEntity.ok(customerUpdateResult);
    }


}