package ir.maktab.firstspringboot.model.repository;

import ir.maktab.firstspringboot.model.entity.category.MainCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainCategoryRepository extends JpaRepository<MainCategory, Long> {
}
