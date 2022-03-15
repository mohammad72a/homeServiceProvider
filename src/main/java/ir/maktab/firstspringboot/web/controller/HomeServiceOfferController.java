package ir.maktab.firstspringboot.web.controller;

import ir.maktab.firstspringboot.api.offer.HomeServiceOfferCreateParam;
import ir.maktab.firstspringboot.api.offer.HomeServiceOfferCreateResult;
import ir.maktab.firstspringboot.api.offer.HomeServiceOfferModel;
import ir.maktab.firstspringboot.service.HomeServiceOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/offers")
public class HomeServiceOfferController {

    private final HomeServiceOfferService offerService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_PROFICIENT')")
    public ResponseEntity<HomeServiceOfferCreateResult> sendOffer(@RequestBody HomeServiceOfferCreateParam createParam) {
        HomeServiceOfferCreateResult homeServiceOfferCreateResult = offerService.sendOffer(createParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(homeServiceOfferCreateResult);
    }

    //    http://localhost:8080/offers/filter?orderId={orderId}
    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('offer:read')")
    public ResponseEntity<List<HomeServiceOfferModel>> getByOrderIdAsc(@RequestParam long orderId) {
        List<HomeServiceOfferModel> homeServiceOfferModels = offerService.loadByOrderIdSortAsc(orderId);
        return ResponseEntity.ok(homeServiceOfferModels);
    }

    //    http://localhost:8080/orders/filterByProficientId?proficientId={proficientId}
    @GetMapping("/filterByProficientId")
    @PreAuthorize("hasAuthority('offer:read')")
    public ResponseEntity<List<HomeServiceOfferModel>> getAllByProficientId(@RequestParam long proficientId) {
        List<HomeServiceOfferModel> result = offerService.loadByProficientId(proficientId);
        return ResponseEntity.ok(result);
    }

}

