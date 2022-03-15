package ir.maktab.firstspringboot.service;

import ir.maktab.firstspringboot.api.offer.HomeServiceOfferCreateParam;
import ir.maktab.firstspringboot.api.offer.HomeServiceOfferCreateResult;
import ir.maktab.firstspringboot.api.offer.HomeServiceOfferModel;
import ir.maktab.firstspringboot.exception.ResourceNotFoundException;
import ir.maktab.firstspringboot.model.entity.HomeServiceOffer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HomeServiceOfferServiceTest {

    @Autowired
    private HomeServiceOfferService homeServiceOfferService;

    @Test
    void test_save() {
        HomeServiceOffer homeServiceOffer = new HomeServiceOffer();
    }

    @Test
    void test_update() {
        HomeServiceOffer homeServiceOffer = homeServiceOfferService.loadById(1L);
        homeServiceOffer.setSuggestedPrice(21000.0);
        HomeServiceOffer result = homeServiceOfferService.update(homeServiceOffer);
        assertEquals(21000.0, result.getSuggestedPrice());
    }

    @Test
    void test_delete() {
        homeServiceOfferService.deleteById(1L);
        assertThrows(ResourceNotFoundException.class, () -> homeServiceOfferService.loadById(1L));
    }

    @Test
    void test_send_offer_isOk() {
        HomeServiceOfferCreateParam createParam = HomeServiceOfferCreateParam.builder()
                .proficientId(1)
                .orderId(5)
                .suggestedPrice(16500.0)
                .workDuration("8 Hours")
                .startTime("08:30 AM, Sat 01/22/2022")
                .build();

        HomeServiceOfferCreateResult homeServiceOfferCreateResult = homeServiceOfferService.sendOffer(createParam);
        assertNotNull(homeServiceOfferCreateResult);
    }

    @Test
    void test_load_by_order_Id_isOk() {
        List<HomeServiceOfferModel> homeServiceOfferModels = homeServiceOfferService.loadByOrderIdSortAsc(4);
        homeServiceOfferModels.forEach(ho -> {
            System.out.println(ho.getId() + ": " + ho.getSuggestedPrice());
        });

        assertEquals(4, homeServiceOfferModels.size());
    }

    @Test
    void test_findAllByProficientId_isOk() {
        List<HomeServiceOfferModel> result = homeServiceOfferService.loadByProficientId(9);
        assertEquals(2, result.size());
    }
}
