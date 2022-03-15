package ir.maktab.firstspringboot.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import ir.maktab.firstspringboot.api.offer.HomeServiceOfferCreateParam;
import ir.maktab.firstspringboot.api.offer.HomeServiceOfferCreateResult;
import ir.maktab.firstspringboot.api.offer.HomeServiceOfferModel;
import ir.maktab.firstspringboot.exception.HomeServiceOfferException;
import ir.maktab.firstspringboot.model.entity.HomeServiceOffer;
import ir.maktab.firstspringboot.model.entity.category.SubCategory;
import ir.maktab.firstspringboot.model.entity.order.HomeServiceOrder;
import ir.maktab.firstspringboot.model.entity.order.OrderStatus;
import ir.maktab.firstspringboot.model.entity.user.Proficient;
import ir.maktab.firstspringboot.model.repository.HomeServiceOfferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service

public class HomeServiceOfferService extends BaseService<HomeServiceOffer, Long> {
    private final HomeServiceOfferRepository homeServiceOfferRepository;
    private final HomeServiceOrderService homeServiceOrderService;
    private final ProficientService proficientService;

    public HomeServiceOfferService(HomeServiceOfferRepository homeServiceOfferRepository, HomeServiceOrderService homeServiceOrderService, ProficientService proficientService) {
        this.homeServiceOfferRepository = homeServiceOfferRepository;
        this.homeServiceOrderService = homeServiceOrderService;
        this.proficientService = proficientService;
        setJpaRepository(homeServiceOfferRepository);
    }


    @Transactional
    public HomeServiceOfferCreateResult sendOffer(HomeServiceOfferCreateParam createParam) {
        Proficient proficient = proficientService.loadById(createParam.getProficientId());
        Set<SubCategory> proficientSubCategories = proficient.getSubCategories();

        HomeServiceOrder homeServiceOrder = homeServiceOrderService.loadById(createParam.getOrderId());

        // Compare proficient and order category
        SubCategory orderSubCategory = homeServiceOrder.getSubCategory();
        if (!proficientSubCategories.contains(orderSubCategory)) {
            throw new HomeServiceOfferException("The proficient does not have the necessary expertise");
        }

        // Compare proficient SuggestedPrice and order SuggestedPrice
        Double proficientSuggestedPrice = createParam.getSuggestedPrice();
        Double orderSuggestedPrice = homeServiceOrder.getSuggestedPrice();
        if (proficientSuggestedPrice < orderSuggestedPrice) {
            throw new HomeServiceOfferException("Proficient suggested price most not be less than order suggested price");
        }

        HomeServiceOffer homeServiceOffer = createParam.convert2HomeServiceOffer(homeServiceOrder, proficient);

        homeServiceOrder.setOrderStatus(OrderStatus.WAITING_FOR_PROFICIENT_SELECTION);
        homeServiceOrder.addOffer(homeServiceOffer);

        homeServiceOffer.setHomeServiceOrder(homeServiceOrder);
        HomeServiceOffer saveResult = homeServiceOfferRepository.save(homeServiceOffer);
        return HomeServiceOfferCreateResult.builder()
                .homeServiceOfferId(saveResult.getId())
                .build();
    }

    public List<HomeServiceOfferModel> loadByOrderIdSortAsc(long orderId) {
        // TODO specification
        List<HomeServiceOffer> homeServiceOffers = homeServiceOfferRepository
                .findAllByHomeServiceOrder_IdOrderBySuggestedPriceAsc(orderId);

        List<HomeServiceOfferModel> homeServiceOfferModels = new ArrayList<>();
        homeServiceOffers.forEach(o -> homeServiceOfferModels.add(new HomeServiceOfferModel().convertOffer2Model(o)));
        return homeServiceOfferModels;
    }

    public List<HomeServiceOfferModel> loadByProficientId(long proficientId) {
        List<HomeServiceOffer> homeServiceOffers = homeServiceOfferRepository.findAllByProficient_Id(proficientId);

        List<HomeServiceOfferModel> homeServiceOfferModels = new ArrayList<>();
        homeServiceOffers.forEach(o -> homeServiceOfferModels.add(new HomeServiceOfferModel().convertOffer2Model(o)));
        return homeServiceOfferModels;
    }

    public Iterable<HomeServiceOfferModel> findAll(BooleanExpression exp) {
        List<HomeServiceOfferModel> offerModels = new ArrayList<>();
        homeServiceOfferRepository.findAll(exp).forEach(
                o -> offerModels.add(new HomeServiceOfferModel().convertOffer2Model(o))
        );
        return offerModels;
    }
}