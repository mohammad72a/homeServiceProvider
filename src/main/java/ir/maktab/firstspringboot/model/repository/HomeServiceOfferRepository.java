package ir.maktab.firstspringboot.model.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import ir.maktab.firstspringboot.model.entity.HomeServiceOffer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HomeServiceOfferRepository extends JpaRepository<HomeServiceOffer, Long>,
        QuerydslPredicateExecutor<HomeServiceOffer> {

    default void customize(final QuerydslBindings bindings) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

    List<HomeServiceOffer> findAllByHomeServiceOrder_IdOrderBySuggestedPriceAsc(long orderId);

    List<HomeServiceOffer> findAllByProficient_Id(long proficientId);

    @Override
    List<HomeServiceOffer> findAll(Sort sort);
}
