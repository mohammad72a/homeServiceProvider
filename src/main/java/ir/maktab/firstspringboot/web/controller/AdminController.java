package ir.maktab.firstspringboot.web.controller;

import ir.maktab.firstspringboot.api.user.customer.CustomerListResult;
import ir.maktab.firstspringboot.api.user.proficient.ProficientListResult;
import ir.maktab.firstspringboot.model.entity.user.UserStatus;
import ir.maktab.firstspringboot.service.CustomerServiceImpl;
import ir.maktab.firstspringboot.service.ProficientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {

    private final CustomerServiceImpl customerServiceImpl;
    private final ProficientService proficientService;

    @GetMapping("/{id}/customers")
    public ResponseEntity<CustomerListResult> showAllCustomers(@PathVariable Long id) {
        CustomerListResult customerListResult = customerServiceImpl.loadAllCustomers();
        return ResponseEntity.ok(customerListResult);
    }

    @GetMapping("/{id}/customers/{status}")
    public ResponseEntity<CustomerListResult> showAllCustomersByStatus(@PathVariable UserStatus status, @PathVariable Long id) {
        CustomerListResult customerListResult = customerServiceImpl.loadAllCustomersByStatus(status);
        return ResponseEntity.ok(customerListResult);
    }

    @GetMapping("/{id}/proficients")
    public ResponseEntity<ProficientListResult> showAllProficients(@PathVariable Long id) {
        ProficientListResult proficientListResult = proficientService.loadAllProficients();
        return ResponseEntity.ok(proficientListResult);
    }

    @GetMapping("/{id}/proficients/{status}")
    public ResponseEntity<ProficientListResult> showAllProficientsByStatus(@PathVariable UserStatus status, @PathVariable Long id) {
        ProficientListResult proficientListResult = proficientService.loadAllProficientsByStatus(status);
        return ResponseEntity.ok(proficientListResult);
    }

    @GetMapping("/{id}/proficients/subCategory/{subCategoryId}")
    public ResponseEntity<ProficientListResult> showAllProficientsBySubCategoryId(@PathVariable long subCategoryId, @PathVariable Long id) {
        ProficientListResult proficientListResult = proficientService.loadAllProficientsBySubCategoryId(subCategoryId);
        return ResponseEntity.ok(proficientListResult);
    }
}

