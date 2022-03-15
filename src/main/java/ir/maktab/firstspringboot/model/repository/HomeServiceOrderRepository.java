package ir.maktab.firstspringboot.model.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import ir.maktab.firstspringboot.model.entity.order.HomeServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HomeServiceOrderRepository extends JpaRepository<HomeServiceOrder, Long>, QuerydslPredicateExecutor<HomeServiceOrder>
         {

    default void customize(final QuerydslBindings bindings) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

    List<HomeServiceOrder> findAllByCustomer_Id(long customerId);
}
