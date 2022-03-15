package ir.maktab.firstspringboot.model.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import ir.maktab.firstspringboot.model.entity.user.Proficient;
import ir.maktab.firstspringboot.model.entity.user.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProficientRepository extends JpaRepository<Proficient, Long>, QuerydslPredicateExecutor<Proficient>
         {

    default void customize(final QuerydslBindings bindings) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.excluding();
    }

    Proficient findByEmail(String email);

    List<Proficient> findAllByProficientStatus(UserStatus status);

    List<Proficient> findAllBySubCategoriesId(long subCategoryId);
}
