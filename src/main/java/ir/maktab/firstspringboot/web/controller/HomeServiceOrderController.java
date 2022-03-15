package ir.maktab.firstspringboot.web.controller;


import ir.maktab.firstspringboot.api.order.*;
import ir.maktab.firstspringboot.service.HomeServiceOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class HomeServiceOrderController {

    private final HomeServiceOrderService orderService;

    //    http://localhost:8080/orders/filter?customerId={customerId}
    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('order:read')")
    public ResponseEntity<List<HomeServiceOrderModel>> getAllByCustomerId(@RequestParam long customerId) {
        List<HomeServiceOrderModel> result = orderService.findAllByCustomerId(customerId);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<HomeServiceOrderCreateResult> addOrder(@RequestBody HomeServiceOrderCreateParam createParam) {
        HomeServiceOrderCreateResult homeServiceOrderCreateResult = orderService.saveHomeServiceOrder(createParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(homeServiceOrderCreateResult);
    }

    @PutMapping("/{orderId}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<OrderUpdateResult> acceptOffer(@RequestBody OfferAcceptParam offerAcceptParam, @PathVariable long orderId) {
        offerAcceptParam.setOrderId(orderId);
        OrderUpdateResult orderUpdateResult = orderService.acceptOffer(offerAcceptParam);
        return ResponseEntity.ok(orderUpdateResult);
    }

    //    http://localhost:8080/orders/finish?orderId={orderId}
    @PutMapping("/finish")
    @PreAuthorize("hasRole('ROLE_PROFICIENT')")
    public ResponseEntity<OrderUpdateResult> finishOrder(@RequestParam long orderId) {
        OrderUpdateResult orderUpdateResult = orderService.finishOrder(orderId);
        return ResponseEntity.ok(orderUpdateResult);
    }


}

